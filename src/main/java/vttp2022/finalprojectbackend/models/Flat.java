package vttp2022.finalprojectbackend.models;

import java.io.StringReader;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Flat {

    // "records": [{"town": "SENGKANG", "flat_type": "5 ROOM", "_full_count":
    // "15781", "flat_model": "Improved", "floor_area_sqm": "110", "street_name":
    // "SENGKANG EAST WAY", "resale_price": "400000", "rank": 0.158137, "month":
    // "2017-02", "remaining_lease": "82 years 07 months", "lease_commence_date":
    // "2000", "storey_range": "16 TO 18", "_id": 1941, "block": "122A"},

    private Integer _id;
    private String month;
    private String town;
    private String flatType;
    private String block;
    private String streetName;
    private String storeyRange;
    private Integer floorArea;
    private String flatModel;
    private Integer leaseCommenceDate;
    private Integer resalePrice;

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getFlatType() {
        return flatType;
    }

    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStoreyRange() {
        return storeyRange;
    }

    public void setStoreyRange(String storeyRange) {
        this.storeyRange = storeyRange;
    }

    public Integer getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(Integer floorArea) {
        this.floorArea = floorArea;
    }

    public String getFlatModel() {
        return flatModel;
    }

    public void setFlatModel(String flatModel) {
        this.flatModel = flatModel;
    }

    public Integer getLeaseCommenceDate() {
        return leaseCommenceDate;
    }

    public void setLeaseCommenceDate(Integer leaseCommenceDate) {
        this.leaseCommenceDate = leaseCommenceDate;
    }

    public Integer getResalePrice() {
        return resalePrice;
    }

    public void setResalePrice(Integer resalePrice) {
        this.resalePrice = resalePrice;
    }

    public static Flat create(SqlRowSet rs) {

        Flat f = new Flat();
        f.set_id(rs.getInt("_id"));
        f.setMonth(rs.getString("_month"));
        f.setTown(rs.getString("town"));
        f.setFlatType(rs.getString("flat_type"));
        f.setBlock(rs.getString("_block"));
        f.setStreetName(rs.getString("street_name"));
        f.setStoreyRange(rs.getString("storey_range"));
        f.setFloorArea(rs.getInt("floor_area_sqm"));
        f.setFlatModel(rs.getString("flat_model"));
        f.setLeaseCommenceDate(rs.getInt("lease_commence_date"));
        f.setResalePrice(rs.getInt("resale_price"));
        return f;
    }

    public static Flat create(String json) {

        Flat f = new Flat();
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject obj = reader.readObject();

        f.set_id(obj.getInt("_id"));
        f.setMonth(obj.getString("month"));
        f.setTown(obj.getString("town"));
        f.setFlatType(obj.getString("flatType"));
        f.setBlock(obj.getString("block"));
        f.setStreetName(obj.getString("streetName"));
        f.setStoreyRange(obj.getString("storeyRange"));
        f.setFloorArea(obj.getInt("floorArea"));
        f.setFlatModel(obj.getString("flatModel"));
        f.setLeaseCommenceDate(obj.getInt("leaseCommenceDate"));
        f.setResalePrice(obj.getInt("resalePrice"));
        return f;
    }

    public static Flat create(JsonObject jsonObj) {

        Flat f = new Flat();
        f.set_id(jsonObj.getInt("_id"));
        f.setMonth(jsonObj.getString("month"));
        f.setTown(jsonObj.getString("town"));
        f.setFlatType(jsonObj.getString("flatType"));
        f.setBlock(jsonObj.getString("block"));
        f.setStreetName(jsonObj.getString("streetName"));
        f.setStoreyRange(jsonObj.getString("storeyRange"));
        f.setFloorArea(jsonObj.getInt("floorArea"));
        f.setFlatModel(jsonObj.getString("flatModel"));
        f.setLeaseCommenceDate(jsonObj.getInt("leaseCommenceDate"));
        f.setResalePrice(jsonObj.getInt("resalePrice"));
        return f;
    }

}
