package com.actitime.repository;

import com.actitime.domain.User;
import com.actitime.domain.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    UserRole findByUserAndRoleName(User user, String name);
}
