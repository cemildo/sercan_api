package dev.proqa.studentmanagementsystem.security.service;

import dev.proqa.studentmanagementsystem.model.User;
import dev.proqa.studentmanagementsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).
                orElseThrow(()-> new UsernameNotFoundException("User not found with username: "+ username));

        return UserDetailsImpl.build(user);
    }



}
