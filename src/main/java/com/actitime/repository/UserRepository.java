package com.actitime.repository;

import com.actitime.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by PENTAGON on 09.02.2016.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserName(String username);

    List<User> findAllByUserName(String username);
}
