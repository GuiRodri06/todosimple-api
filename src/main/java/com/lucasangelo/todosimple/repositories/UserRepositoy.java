package com.lucasangelo.todosimple.repositories;

import com.lucasangelo.todosimple.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepositoy extends JpaRepository<User, Long> {

    @Transactional
    User findByUsername(String username);

}
