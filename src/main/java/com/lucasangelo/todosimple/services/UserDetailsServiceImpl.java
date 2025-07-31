package com.lucasangelo.todosimple.services;

import com.lucasangelo.todosimple.models.User;
import com.lucasangelo.todosimple.repositories.UserRepositoy;
import com.lucasangelo.todosimple.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepositoy userRepositoy;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepositoy.findByUsername(username);
        if (Objects.isNull(user))
            throw new UsernameNotFoundException("Usuário nao encontrado" + username);
        return new UserSpringSecurity(user.getId(), user.getUsername(), user.getPassword(), user.getProfiles());
    }
}
