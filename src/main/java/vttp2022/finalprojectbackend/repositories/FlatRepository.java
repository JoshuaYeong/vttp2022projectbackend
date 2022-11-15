package vttp2022.finalprojectbackend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.finalprojectbackend.models.Flat;

import static vttp2022.finalprojectbackend.repositories.SQL.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlatRepository {

    @Autowired
    private JdbcTemplate template;

    public Boolean insertFlatByUsername(String username, Flat flat) {

        int added = template.update(SQL_INSERT_INTO_FLATS_BY_USERNAME, flat.get_id(),
                flat.getMonth(), flat.getTown(), flat.getFlatType(), flat.getBlock(),
                flat.getStreetName(), flat.getStoreyRange(), flat.getFloorArea(),
                flat.getFlatModel(), flat.getLeaseCommenceDate(), flat.getResalePrice(), username);

        return 1 == added;
    }

    public List<Flat> getFlatsByUsername(String username) {

        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_ALL_FLATS_BY_USERNAME, username);
        List<Flat> flats = new ArrayList<>();
        while (rs.next()) {
            flats.add(Flat.create(rs));
        }
        return flats;
    }

    public Boolean deleteFlatByIdAndUsername(String username, Integer flatId) {

        int deleted = template.update(SQL_DELETE_FLAT_BY_ID_AND_USERNAME, flatId, username);

        return 1 == deleted;

    }

}
