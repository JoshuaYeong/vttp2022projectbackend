package vttp2022.finalprojectbackend.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vttp2022.finalprojectbackend.models.AppUser;
import vttp2022.finalprojectbackend.repositories.AppUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<AppUser> optAppUser = appUserRepo.findUserByUsername(username);

        if (optAppUser.isEmpty()) {
            throw new UsernameNotFoundException(">>>>>User " + username + " Not Found!");
        }

        AppUser appUser = optAppUser.get();

        return new User(appUser.getUsername(), appUser.getPassword(), new ArrayList<>());
    }

}
