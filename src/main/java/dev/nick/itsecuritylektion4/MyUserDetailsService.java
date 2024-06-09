package dev.nick.itsecuritylektion4;

import dev.nick.itsecuritylektion4.persistence.MyUser;
import dev.nick.itsecuritylektion4.persistence.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }


    @Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {

        MyUser user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        Collection<? extends GrantedAuthority> authorities = getAuthorities(user.getRole());

        return new User(user.getEmail(), user.getPassword(),true, true, true, true, authorities);
    }


}
