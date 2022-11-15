package vttp2022.finalprojectbackend.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp2022.finalprojectbackend.models.Flat;
import vttp2022.finalprojectbackend.repositories.FlatRepository;
import vttp2022.finalprojectbackend.repositories.AppUserRepository;

@Service
public class FlatService {

    @Autowired
    private AppUserRepository appUserRepo;

    @Autowired
    private FlatRepository flatRepo;

    private static final Logger logger = LoggerFactory.getLogger(FlatService.class.getName());

    @Transactional
    public Boolean saveFlat(String username, Flat flat) {

        try {
            appUserRepo.existsByUsername(username);
            logger.info(">>>>>AppUser [{}] in database found", username);

        } catch (Exception ex) {
            logger.error(">>>>>AppUser [{}] in database not found", username);
            return false;
        }

        boolean success = flatRepo.insertFlatByUsername(username, flat);
        if (success == false) {
            throw new IllegalArgumentException(">>>>>Failed to insert flat for: " + username);
        }
        logger.info(">>>>>Successfully saved favourites to DB for AppUser [{}] ", username);
        return true;
    }

    public List<Flat> getFlatsByUsername(String username) {

        return flatRepo.getFlatsByUsername(username);
    }

    public Boolean deleteFavourite(String username, Integer flatId) {

        try {
            boolean flatDeleted = flatRepo.deleteFlatByIdAndUsername(username, flatId);
            logger.info(">>>>>Successfully deleted Flat Id: [{}] from DB for for AppUser [{}] ", flatId, username);
            return flatDeleted;

        } catch (Exception ex) {
            logger.error(">>>>>Flat in DB was not deleted for AppUser [{}] ", username);
        }
        return false;
    }
}
