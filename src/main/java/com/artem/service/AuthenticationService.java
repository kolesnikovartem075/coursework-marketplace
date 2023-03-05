package com.artem.service;

import com.artem.database.repository.CustomerRepository;
import com.artem.database.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final ManagerRepository managerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> customer = getCustomer(username);
        Optional<User> manager = getManager(username);

        if (customer.isPresent()) {
            return customer.get();
        }

        if (manager.isPresent()) {
            return manager.get();
        }

        throw new UsernameNotFoundException("Failed to retrieve user: " + username);
    }

    private Optional<User> getCustomer(String email) {
        return customerRepository.findByEmail(email)
                .map(entity -> new User(entity.getEmail(),
                        entity.getPassword(),
                        Collections.singleton(entity.getRole())
                ));
    }

    private Optional<User> getManager(String username) {
        return managerRepository.findByUsername(username)
                .map(entity -> new User(entity.getUsername(),
                        entity.getPassword(),
                        Collections.singleton(entity.getRole())
                ));
    }
}
