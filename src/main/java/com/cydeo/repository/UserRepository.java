package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByIsDeletedOrderByFirstNameDesc(Boolean deleted);
    
    User findByUserNameAndIsDeleted(String username, Boolean deleted);//for update we use by username
    @Transactional//if transaction is successful will get commit if not rollback
    void deleteByUserName(String username);//all queries inside repository Where included @Where(clause="is_deleted=false")all combined

    List<User> findByRoleDescriptionIgnoreCaseAndIsDeleted(String description, Boolean deleted);
}
