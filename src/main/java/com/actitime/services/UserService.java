package com.actitime.services;

import com.actitime.domain.Role;
import com.actitime.domain.User;
import com.actitime.domain.UserRole;
import com.actitime.repository.RoleRepository;
import com.actitime.repository.UserRepository;
import com.actitime.repository.UserRoleRepository;
import com.actitime.utils.ServiceResponse;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Transactional
@Service
public class UserService {

    @Inject
    private SessionService sessionService;

    @Inject
    private SessionFactory sessionFactory;

    @Inject
    private EntityManager entityManager;

    @Inject
    private UserRepository userRepository;

    @Inject
    private RoleRepository roleRepository;

    @Inject
    private UserRoleRepository userRoleRepository;

    @Secured({Role.ROLE_MANAGER, Role.ROLE_EMPLOYEE})
    @Transactional(readOnly = true)
    public ServiceResponse index(int limit, int offset) {
        int total = ((Long) sessionFactory.getCurrentSession().createCriteria(User.class)
                .setProjection(Projections.rowCount()).uniqueResult()).intValue();

        List<User> users = sessionFactory.getCurrentSession().createCriteria(User.class)
                .setMaxResults(limit).setFirstResult(offset).list();

        return new ServiceResponse(users, new ServiceResponse.Metadata(total, limit, offset), null);
    }

    @Secured({Role.ROLE_MANAGER})
    @Transactional(readOnly = true)
    public ServiceResponse show(Long id) {
        User user = entityManager.find(User.class, id);
        return new ServiceResponse(user, null, null);
    }

    @Secured({Role.ROLE_MANAGER})
    public ServiceResponse create(User user) {
        entityManager.persist(user);
        if (user.getDirtyFieldNames().contains("manager")) {
            if (user.isManager()) {
                addRole(user, Role.ROLE_MANAGER);
            } else {
                addRole(user, Role.ROLE_EMPLOYEE);
            }
        }
        return new ServiceResponse(user, null, null);
    }

    @PreAuthorize("not (#id == principal.id and not #user.isManager)")
    @Secured({Role.ROLE_MANAGER})
    public ServiceResponse update(User user) {
        if (user.getDirtyFieldNames().contains("manager")) {
            if (user.isManager()) {
                revokeRole(user, Role.ROLE_EMPLOYEE);
                addRole(user, Role.ROLE_MANAGER);
            } else {
                revokeRole(user, Role.ROLE_MANAGER);
                addRole(user, Role.ROLE_EMPLOYEE);
            }
            sessionService.expireSession(user.getId());
        }
        entityManager.merge(user);
        return new ServiceResponse(user, null, null);
    }

    @PreAuthorize("#id != principal.id")
    @Secured({Role.ROLE_MANAGER})
    public boolean delete(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
            sessionService.expireSession(user.getId());
            return true;
        } else return false;
    }

    @Secured({Role.ROLE_MANAGER})
    public void addRole(User user, String roleName) {
        Role role = roleRepository.findByName(roleName);
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        user.getUserRoles().add(userRole);
    }

    @Secured({Role.ROLE_MANAGER})
    private void revokeRole(User user, String roleName) {
        UserRole userRole = userRoleRepository.findByUserAndRoleName(user, roleName);
        if (userRole != null) {
            user.getUserRoles().remove(userRole);
            entityManager.remove(userRole);
        }
    }
}
