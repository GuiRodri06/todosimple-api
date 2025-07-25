package com.lucasangelo.todosimple.repositories;

import com.lucasangelo.todosimple.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoy extends JpaRepository<User, Long> {
    
}
