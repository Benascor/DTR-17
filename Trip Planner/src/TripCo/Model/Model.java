package TripCo.Model;

import TripCo.Trip;

import java.util.ArrayList;

/**
 * Created by mjdun on 2/16/2017.
 */
public class Model {
    private String message;
    protected ArrayList<Location> locations;

    public Model() {
        message = "Hello World!";
        locations = new ArrayList<Location>();
    }

    public String getMessage() {
        return message;
    }

    public void parseCSV(String fileName){
        CSVParser parser = new CSVParser(fileName);
        parser.parse();
        parser.printData();

        CSVData csvData = parser.getCSVData();
        for(int i = 0; i < csvData.getHeight(); ++i){
            String id = csvData.getValue("id", i);
            String name = csvData.getValue("name", i);
            String latitude = csvData.getValue("latitude", i);
            String longitude = csvData.getValue("longitude", i);

            locations.add(new Location(id, name, latitude, longitude));
        }

    }

    public Trip getTrip(){
        return new Trip(false, false, false);
    }

    public static void main(String[] args){

    }




}
