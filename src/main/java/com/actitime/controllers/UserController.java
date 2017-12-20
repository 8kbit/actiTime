package com.actitime.controllers;

import com.actitime.domain.Role;
import com.actitime.domain.User;
import com.actitime.services.UserService;
import com.actitime.utils.BindingHelper;
import com.actitime.utils.ServiceResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Qualifier(value = "mvcValidator")
    @Autowired
    private Validator validator;

    @Qualifier("userValidator")
    @Autowired
    private Validator userValidator;

    @Inject
    private SessionFactory sessionFactory;

    @Autowired
    ApplicationContext applicationContext;

    @Inject
    private UserService userService;

    @Secured({Role.ROLE_MANAGER, Role.ROLE_EMPLOYEE})
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> index(@RequestParam Integer limit, @RequestParam Integer offset) throws JsonProcessingException {
        ServiceResponse serviceResult = userService.index(limit, offset);

        Map result = new HashMap<>();
        result.put("total", serviceResult.getMetadata().getTotalCount());
        result.put("rows", serviceResult.getData());
        return new ResponseEntity<Object>(new ObjectMapper().writeValueAsString(result), HttpStatus.OK);
    }

    @Secured({Role.ROLE_MANAGER})
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> show(@PathVariable Long id) {
        ServiceResponse serviceResult = userService.show(id);
        if (serviceResult.getData() != null) return new ResponseEntity<Object>(serviceResult.getData(), HttpStatus.OK);
        else return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }

    @Secured({Role.ROLE_MANAGER})
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody String userMap_) throws IOException {
        Map userMap = new ObjectMapper().readValue(userMap_, HashMap.class);
        User user = new User();
        BindingResult errors = BindingHelper.bindMapToDomain(user, userMap, validator, userValidator);
        if (errors.hasErrors())
            return new ResponseEntity<Object>(new BindingHelper.CustomErrors(errors.getFieldErrors()), HttpStatus.BAD_REQUEST);
        userService.create(user);
        return new ResponseEntity<Object>(user, HttpStatus.CREATED);
    }

    @Secured({Role.ROLE_MANAGER})
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody String userMap_) throws IOException {
        Map userMap = new ObjectMapper().readValue(userMap_, HashMap.class);
        User user = (User) userService.show(id).getData();
        if (user == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Integer newVersion = user.getVersion();
        Integer oldVersion = Integer.valueOf((String) userMap.get("version"));
        if (newVersion != oldVersion) {
            return new ResponseEntity<Object>(new BindingHelper.CustomErrors("concurentModification", ""), HttpStatus.BAD_REQUEST);
        }

        BindingResult errors = BindingHelper.bindMapToDomain(user, userMap, validator, userValidator);
        if (errors.hasErrors())
            return new ResponseEntity<Object>(new BindingHelper.CustomErrors(errors.getFieldErrors()), HttpStatus.BAD_REQUEST);
        user = (User) userService.update(user).getData();
        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }

    @Secured({Role.ROLE_MANAGER})
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        boolean res = userService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}