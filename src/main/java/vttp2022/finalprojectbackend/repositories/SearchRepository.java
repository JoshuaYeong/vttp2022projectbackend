package vttp2022.finalprojectbackend.repositories;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.finalprojectbackend.models.Flat;

@Repository
public class SearchRepository {

    // Identifier:'f1765 b54-a209-4718-8d 38-a39237f502b3'
    // Title:'Resale flat prices based on registration date from Jan-2017 onwards'

    // "records": [{"town": "SENGKANG", "flat_type": "5 ROOM", "_full_count":
    // "15781", "flat_model": "Improved", "floor_area_sqm": "110", "street_name":
    // "SENGKANG EAST WAY", "resale_price": "400000", "rank": 0.158137, "month":
    // "2017-02", "remaining_lease": "82 years 07 months", "lease_commence_date":
    // "2000", "storey_range": "16 TO 18", "_id": 1941, "block": "122A"},

    // https://data.gov.sg/api/action/datastore_search?resource_id=f1765b54-a209-4718-8d38-a39237f502b3&q=sengkang+east+20way&limit=3

    private static final String URL = "https://data.gov.sg/api/action/datastore_search?resource_id=f1765b54-a209-4718-8d38-a39237f502b3";

    public List<Flat> getResultsFromAPI(String query, Integer limit) {

        String searchUrl = UriComponentsBuilder
                .fromUriString((URL))
                .queryParam("q", query.replaceAll(" ", "+"))
                .queryParam("limit", limit)
                .toUriString();

        // System.out.println(">>>>>Search URL: " + searchUrl);

        RequestEntity<Void> req = RequestEntity
                .get(searchUrl)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = template.exchange(req, String.class);
        String payload = resp.getBody();
        JsonReader reader = Json.createReader(new StringReader(payload));

        JsonArray resultArr = null;

        List<Flat> listOfFlats = new ArrayList<Flat>();

        try {
            resultArr = reader.readObject().getJsonObject("result").getJsonArray("records");
            // System.out.println(">>>>>RESULTS: " + resultArr);

        } catch (Exception ex) {
            ex.printStackTrace();
            return listOfFlats;
        }

        for (int i = 0; i < resultArr.size(); i++) {

            Flat flat = new Flat();
            JsonObject arrObject = resultArr.getJsonObject(i);
            flat.set_id(arrObject.getInt("_id"));
            flat.setMonth(arrObject.getString("month"));
            flat.setTown(arrObject.getString("town"));
            flat.setFlatType(arrObject.getString("flat_type"));
            flat.setBlock(arrObject.getString("block"));
            flat.setStreetName(arrObject.getString("street_name"));
            flat.setStoreyRange(arrObject.getString("storey_range"));
            flat.setFloorArea(Integer.parseInt(arrObject.getString("floor_area_sqm")));
            flat.setFlatModel(arrObject.getString("flat_model"));
            flat.setLeaseCommenceDate(Integer.parseInt(arrObject.getString("lease_commence_date")));
            flat.setResalePrice(Integer.parseInt(arrObject.getString("resale_price")));
            listOfFlats.add(flat);
        }

        return listOfFlats;
    }

}
