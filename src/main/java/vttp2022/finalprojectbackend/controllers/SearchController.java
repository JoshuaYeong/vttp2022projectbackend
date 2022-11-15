package vttp2022.finalprojectbackend.controllers;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.finalprojectbackend.models.Flat;
import vttp2022.finalprojectbackend.models.SearchRequest;
import vttp2022.finalprojectbackend.services.FlatService;
import vttp2022.finalprojectbackend.services.SearchService;

@RestController
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class.getName());

    @Autowired
    private SearchService searchSvc;

    @Autowired
    private FlatService flatSvc;

    @GetMapping("/results/{username}")
    public ResponseEntity<?> getResults(@PathVariable String username, @RequestParam String query,
            @RequestParam Integer limit) {

        SearchRequest sRequest = new SearchRequest();
        sRequest.setQuery(query);
        sRequest.setLimit(limit);
        logger.info(">>>>>Query: {}, Limit: {}", query, limit);

        Optional<List<Flat>> listOfFlats = searchSvc.getResultsFromAPI(sRequest);

        if (listOfFlats.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No flats found");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(listOfFlats);
        }
    }

    @PostMapping("/favourites/{username}")
    public ResponseEntity<?> postSave(@PathVariable String username, @RequestBody String payload) {

        // System.out.println(">>>>>Payload: " + payload);

        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonArray jArray = reader.readArray();

        try {
            for (int i = 0; i < jArray.size(); i++) {

                JsonObject obj = jArray.getJsonObject(i);
                Flat flat = Flat.create(obj);
                flatSvc.saveFlat(username, flat);
            }
            return ResponseEntity.status(HttpStatus.OK).body(">>>>>Pass: Save to favourites successfully");

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(">>>>>Error: Save to favourites failed");
        }

    }

    @GetMapping("/favourites/{username}")
    public ResponseEntity<?> getFlatsFromDBByUsername(@PathVariable String username) {

        try {
            List<Flat> listOfFlatsFromDB = flatSvc.getFlatsByUsername(username);
            return ResponseEntity.status(HttpStatus.OK).body(listOfFlatsFromDB);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(">>>>>No favourites found for: " + username);
        }
    }

    @DeleteMapping("/favourites/{username}/delete/{flatId}")
    public ResponseEntity<?> deleteFavourite(@PathVariable String username, @PathVariable Integer flatId) {

        System.out.println(">>>>>Payload: " + username + flatId);

        boolean flatDeleted = flatSvc.deleteFavourite(username, flatId);

        if (flatDeleted == false) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(">>>>>Failed to delete Flat from DB");
        }

        return ResponseEntity.status(HttpStatus.OK).body(">>>>>Successfully deleted Flat from DB");
    }

}
