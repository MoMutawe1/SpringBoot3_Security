package springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import springboot.entity.UserInfo;
import springboot.repository.UserInfoRepository;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    // check if the user is registered in our system and has a profile in our DB.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // get the user info from the DB.
        Optional<UserInfo> userInfo = repository.findByName(username);
        // here just converting userInfo which we have in DB into to UserInfoUserDetails object by giving the username,password,roles and authority.
        return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }
}