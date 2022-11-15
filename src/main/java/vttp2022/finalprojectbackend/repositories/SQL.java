package vttp2022.finalprojectbackend.repositories;

public interface SQL {

    public static final String SQL_INSERT_INTO_USERS = "INSERT INTO users (username, password) VALUES (?,?)";
    public static final String SQL_SELECT_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    public static final String SQL_INSERT_INTO_FLATS_BY_USERNAME = "INSERT INTO flats (_id, _month, town, flat_type, _block, street_name, storey_range, floor_area_sqm, flat_model, lease_commence_date, resale_price, username) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String SQL_SELECT_ALL_FLATS_BY_USERNAME = "SELECT * FROM flats WHERE username = ?";
    public static final String SQL_DELETE_FLAT_BY_ID_AND_USERNAME = "DELETE FROM flats WHERE _id = ? AND username = ?";

}
