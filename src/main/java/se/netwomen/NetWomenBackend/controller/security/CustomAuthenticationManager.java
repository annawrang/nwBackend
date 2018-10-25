package se.netwomen.NetWomenBackend.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.user.UserRepository;
import se.netwomen.NetWomenBackend.repository.DTO.dto.User.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserRepository userRepository;


    public CustomAuthenticationManager() {
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        Optional<UserDTO> user = userRepository.findByEmail(email);

        if (!user.isPresent()) {
            throw new BadCredentialsException("1000");
        }
        // OM USER ÄR AVSTÄNGD
//        if (user.isDisabled()) {
//            throw new DisabledException("1001");
//        }
        if (!credentialsMatching(user.get(), password)) {
            throw new BadCredentialsException("1000");
        }
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(email, password, grantedAuths);
    }

    private boolean credentialsMatching(UserDTO user, String password) {
        if (user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}