package main.java.edu.csu2017sp314.dtr17.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by maste on 4/7/2017.
 */
public class DatabaseFetcher {
    private static final String DB_URL = "jdbc:mysql://faure.cs.colostate.edu/cs314?serverTimezone=MST";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    //Definitely the most secure way to store user names and passwords, trust me.
    private static final String USER_NAME = "mjdunn";
    private static final String PASSWORD = "830602242";

    protected Connection connection;
    protected Statement statement;

    public static void main(String[] args) throws SQLException {
        DatabaseFetcher test = new DatabaseFetcher();
        test.getAllCountriesInContinent("North America");
    }

    public DatabaseFetcher(){
        //connect();
    }

    protected void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.err.printf("Exception in connect: ");
            System.err.println(e.getMessage());
        }
    }

    protected void disconnect() throws SQLException {
        connection.close();
        statement.close();
    }

    protected void selectFrom(String column, String table, ResultSet resultSet) throws SQLException {
        connect();

        String query = "select " + column + " from " + table;
        resultSet = statement.executeQuery(query);

        disconnect();
    }

    public ArrayList<String> getAllCountriesInContinent(String continent) {
        ArrayList<String> countries = new ArrayList<String>();

        ResultSet rs = null;

        try {
            String query = "select name from countries where continent = '" + continent + "'" ;

            connect();
            rs = statement.executeQuery(query);


            while (rs.next()) {
                countries.add(rs.getString("name"));
            }

            Collections.sort(countries.subList(0, countries.size()));

            rs.close();
            disconnect();

        } catch (SQLException e) {
            System.err.printf("Error in getAllCountriesInContinent: ");
            System.err.println(e.getMessage());
        }

        return countries;
    }

    public ArrayList<String> getAllRegionsInISOCountry(String isoCountry){
        ArrayList<String> regions = new ArrayList<String>();

        ResultSet rs = null;

        String query = "select name from regions where iso_country = '" + isoCountry + "'" ;
        try {
            connect();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                regions.add(rs.getString("name"));
            }

            Collections.sort(regions.subList(0, regions.size()));

            disconnect();
            rs.close();

        } catch (SQLException e) {
            System.err.printf("Error in getAllRegionsInISOCountry: ");
            System.err.println(e.getMessage());
        }

        return regions;
    }

    public ArrayList<String> getAllMunicipalitiesInISORegion(String iso_region){
        ArrayList<String> municipalities = new ArrayList<String>();

        ResultSet rs = null;

        String query = "select distinct municipality from airports where iso_region = '" + iso_region + "'";
        try {
            connect();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                municipalities.add(rs.getString("municipality"));
            }

            Collections.sort(municipalities.subList(0, municipalities.size()));

            disconnect();
            rs.close();
        } catch (SQLException e) {
            System.err.printf("Error in getAllMunicipalitiesInRegion: ");
            System.err.println(e.getMessage());
        }

        return municipalities;
    }

    public String getContinentCodeFromName(String continentName){
        ResultSet rs = null;
        String id = "";

        String query = "select id from continents where name = '" + continentName + "'";
        try {
            connect();
            rs = statement.executeQuery(query);

            rs.next();
            id = rs.getString("id");

            disconnect();
            rs.close();
        } catch (SQLException e) {
            System.err.printf("Error in getContinentCodeFromName: ");
            System.err.println(e.getMessage());
        }

        return id;
    }

    public String getCountryCodeFromName(String countryName){
        ResultSet rs = null;
        String code = "";

        String query = "select code from countries where name = '" + countryName + "'";
        try {
            connect();
            rs = statement.executeQuery(query);

            rs.next();
            code = rs.getString("code");

            disconnect();
            rs.close();
        } catch (SQLException e) {
            System.err.printf("error in getCountryCodeFromName:");
            System.err.println(e.getMessage());
        }

        return code;
    }

    public String getISORegionFromName(String regionName){
        ResultSet rs = null;
        String code = "";

        String query = "select code from regions where name = '" + regionName + "'";
        try {
            connect();
            rs = statement.executeQuery(query);

            rs.next();
            code = rs.getString("code");

            disconnect();
            rs.close();
        } catch (SQLException e) {
            System.err.printf("error in getISORegionFromName: ");
            System.err.println(e.getMessage());
        }

        return code;
    }

    public String getLatitudeFromName(String airPortName){
        ResultSet rs = null;
        String code = "";

        String query = "select latitude from airports where name = '" + airPortName + "'";
        try {
            connect();
            rs = statement.executeQuery(query);

            rs.next();
            code = rs.getString("latitude");

            disconnect();
            rs.close();
        } catch (SQLException e) {
            System.err.printf("error in getLatitudeFromName: ");
            System.err.println(e.getMessage());
        }

        return code;
    }

    public String getLatitudeFromID(String airPortID){
        ResultSet rs = null;
        String code = "";

        String query = "select latitude from airports where id = '" + airPortID + "'";
        try {
            connect();
            rs = statement.executeQuery(query);

            rs.next();
            code = rs.getString("latitude");

            disconnect();
            rs.close();
        } catch (SQLException e) {
            System.err.printf("error in getLatitudeFromID: ");
            System.err.println(e.getMessage());
        }

        return code;
    }

    public String getLongitudeFromName(String airPortName){
        ResultSet rs = null;
        String code = "";

        String query = "select longitude from airports where name = '" + airPortName + "'";
        try {
            connect();
            rs = statement.executeQuery(query);

            rs.next();
            code = rs.getString("longitude");

            disconnect();
            rs.close();
        } catch (SQLException e) {
            System.err.printf("error in getLongitudeFromName: ");
            System.err.println(e.getMessage());
        }

        return code;
    }

    public String getLongitudeFromID(String airPortID){
        ResultSet rs = null;
        String code = "";

        String query = "select longitude from airports where id = '" + airPortID + "'";
        try {
            connect();
            rs = statement.executeQuery(query);

            rs.next();
            code = rs.getString("longitude");

            disconnect();
            rs.close();
        } catch (SQLException e) {
            System.err.printf("error in getLongitudeFromID: ");
            System.err.println(e.getMessage());
        }

        return code;
    }

    public ArrayList<Airport> getAirportsFromAirportIDs(List<String> IDs){
        ArrayList<Airport> airports = new ArrayList<Airport>();
        ResultSet rs = null;
        String query = "select * from airports where id in(";

        for(int i = 0; i < IDs.size() - 1; ++i){
            query += "'" + IDs.get(i) + "' , ";
        }
        query += "'" + IDs.get(IDs.size() - 1) + "')";

        try {
            connect();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                airports.add(initializeAirport(rs));
            }

            for(int i = 0; i < airports.size(); ++i){
                String countryQuery = "select name from countries where code = '" + airports.get(i).getIsoCountry() + "'";
                rs = statement.executeQuery(countryQuery);

                rs.next();
                airports.get(i).setCountryName(rs.getString("name"));
            }

            disconnect();
            rs.close();
        } catch (SQLException e) {
            System.err.printf("error in getAirportsFromAirportIDs: ");
            System.err.println(e.getMessage());
        }

        return airports;
    }

    //Initializes an airport from a result set containing all airport columns.
    protected Airport initializeAirport(ResultSet rs) throws SQLException {
        Airport airport = new Airport();

        airport.setName(rs.getString("name"));
        airport.setLongitude(rs.getString("longitude"));
        airport.setLatitude(rs.getString("latitude"));
        airport.setID(rs.getString("id"));
        airport.setContinent(rs.getString("continent"));
        airport.setIsoCountry(rs.getString("iso_country"));
        airport.setIsoRegion(rs.getString("iso_region"));
        airport.setMunicipality(rs.getString("municipality"));
        airport.setWikipediaLink(rs.getString("wikipedia_link"));
        airport.setGps_code(rs.getString("gps_code"));
        airport.setHomeLink(rs.getString("home_link"));
        airport.setScheduledService(rs.getString("scheduled_service"));
        airport.setIataCode(rs.getString("iata_code"));
        airport.setLocalCode(rs.getString("local_code"));
        airport.setType(rs.getString("type"));

        return airport;
    }

    public String getAirportIDFromName(String airPortName){
        ResultSet rs = null;
        String code = "";

        String query = "select id from airports where name = '" + airPortName + "'";
        try {
            connect();
            rs = statement.executeQuery(query);

            rs.next();
            code = rs.getString("id");

            disconnect();
            rs.close();
        } catch (SQLException e) {
            System.err.printf("error in getAirportIDFromName: ");
            System.err.println(e.getMessage());
        }

        return code;
    }

    public String getAirportNameFromID(String airportID){
        ResultSet rs = null;
        String code = "";

        String query = "select name from airports where id = '" + airportID + "'";
        try {
            connect();
            rs = statement.executeQuery(query);

            rs.next();
            code = rs.getString("name");

            disconnect();
            rs.close();
        } catch (SQLException e) {
            System.err.printf("error in getAirportNameFromID: ");
            System.err.println(e.getMessage());
        }

        return code;
    }
    public ArrayList<Airport> searchForAirports(String columnSpecifier){
        ArrayList<Airport> airports = new ArrayList<Airport>();

        ResultSet rs = null;

        String query = "select * from airports where " + columnSpecifier;

        try {
            connect();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                airports.add(initializeAirport(rs));
            }

            for(int i = 0; i < airports.size(); ++i){
                String countryQuery = "select name from countries where code = '" + airports.get(i).getIsoCountry() + "'";
                rs = statement.executeQuery(countryQuery);

                rs.next();
                //System.out.println(rs.getString("name"));
                airports.get(i).setCountryName(rs.getString("name"));
            }

            disconnect();
            rs.close();

        } catch (SQLException e) {
            System.err.printf("Error in getAllRegionsInISOCountry: ");
            System.err.println(e.getMessage());
        }

        return airports;
    }
}
