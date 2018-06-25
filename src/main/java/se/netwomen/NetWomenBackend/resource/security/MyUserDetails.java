package se.netwomen.NetWomenBackend.resource.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.repository.DTO.UserRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;
import se.netwomen.NetWomenBackend.service.exceptions.EmailNotFoundException;

import java.util.Optional;

@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserDTO> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new EmailNotFoundException("User '" + email + "' not found");
        }

        System.out.println("MyUserDetails: User är present");
        return org.springframework.security.core.userdetails.User//
                .withUsername(email)//
                .password(user.get().getPassword())//
                .authorities(user.get().getRoles())//
                .accountExpired(false)//
                .accountLocked(false)//
                .credentialsExpired(false)//
                .disabled(false)//
                .build();
    }
}
