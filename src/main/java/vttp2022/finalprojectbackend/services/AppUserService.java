package vttp2022.finalprojectbackend.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.finalprojectbackend.models.AppUser;
import vttp2022.finalprojectbackend.repositories.AppUserRepository;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepo;

    private static final Logger logger = LoggerFactory.getLogger(AppUserService.class.getName());

    public Optional<AppUser> findUser(String username) {

        try {
            Optional<AppUser> appUser = appUserRepo.findUserByUsername(username);
            logger.info(">>>>>AppUser [{}] in database found ", username);
            return appUser;

        } catch (Exception ex) {
            logger.error(">>>>>AppUser [{}] in database not found ", username);
        }
        return Optional.empty();
    }

    public boolean userExists(String username) {

        try {
            boolean exists = appUserRepo.existsByUsername(username);
            logger.info(">>>>>AppUser [{}] exist in database ", username);
            return exists;

        } catch (Exception ex) {
            logger.error(">>>>>AppUser [{}] does not exist in database ", username);
        }
        return false;

    }

    public boolean saveUser(String username, String password) {

        try {
            boolean saved = appUserRepo.insertNewUser(username, password);
            logger.info(">>>>>AppUser [{}] saved to database ", username);
            return saved;

        } catch (Exception ex) {
            logger.error(">>>>>AppUser [{}] not saved to database ", username);
        }
        return false;
    }

}
