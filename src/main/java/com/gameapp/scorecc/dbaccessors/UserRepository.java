package com.gameapp.scorecc.dbaccessors;

import com.gameapp.scorecc.model.User;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

/*
* https://spring.io/guides/gs/accessing-data-mysql
* https://www.baeldung.com/spring-boot-sqlite
 */

public interface UserRepository extends CrudRepository<User, Integer> {

}