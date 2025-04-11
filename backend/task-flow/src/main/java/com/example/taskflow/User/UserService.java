package com.example.taskflow.User;

import com.example.taskflow.Exception.UserCreateFailedException;
import com.example.taskflow.Exception.UserLoginFailedException;
import com.example.taskflow.Role.IRoleRepository;
import com.example.taskflow.Role.Role;
import com.example.taskflow.Security.JwtUtilService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtilService jwtUtilService;
    public UserService(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder,UserDetailsService userDetailsService,AuthenticationManager authenticationManager,JwtUtilService jwtUtilService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtilService = jwtUtilService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        if(user == null) {
            throw new UserCreateFailedException("The user is null");
        }else if(user.getUsername() == null || user.getPassword() == null) {
            throw new UserCreateFailedException("The username or password is null");
        }
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();

        optionalRoleUser.ifPresent(role -> roles.add(role));
        if(user.isAdmin()){
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(role -> roles.add(role));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String login(User user) throws JsonProcessingException {
        if(user == null) {
            throw new UserLoginFailedException("The user is null");
        }if(user.getUsername() == null || user.getPassword() == null) {
            throw new UserLoginFailedException("The username or password is null");
        }
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword()
        ));

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getUsername());
        return this.jwtUtilService.genereateToken(userDetails);

    }

}
