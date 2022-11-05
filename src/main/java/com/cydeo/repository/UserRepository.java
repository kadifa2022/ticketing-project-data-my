package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    
    User findByUserName(String username);
    @Transactional//if transaction is successful will get commit if not rollback
    void deleteByUserName(String username);//derived query

    List<User> findByRoleDescriptionIgnoreCase(String description);
}
