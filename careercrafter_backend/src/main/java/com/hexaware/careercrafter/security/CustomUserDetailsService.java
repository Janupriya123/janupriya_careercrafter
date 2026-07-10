package com.hexaware.careercrafter.security;
import com.hexaware.careercrafter.entity.User;
import com.hexaware.careercrafter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    @Autowired
    private UserRepository repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        logger.info("Loading user details for email: {}", email);
        User user=repo.findByEmail(email);
        if(user==null)
        {
            throw new UsernameNotFoundException("User not found");
        }

        logger.info("User details loaded successfully for email: {}", email);
        return  org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
