package com.actitime.controllers;

import com.actitime.domain.Role;
import com.actitime.utils.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Secured({Role.ROLE_MANAGER, Role.ROLE_EMPLOYEE})
    @RequestMapping(path = "/validate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> validate() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof CustomUserDetails)
            return new ResponseEntity<Object>(((CustomUserDetails) userDetails).toJSON(), HttpStatus.OK);
        else return new ResponseEntity(HttpStatus.OK);
    }
}
