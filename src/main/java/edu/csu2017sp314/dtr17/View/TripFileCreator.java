package main.java.edu.csu2017sp314.dtr17.View;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by mjdun on 3/26/2017.
 */
public class TripFileCreator {
    private boolean showMileage;
    private boolean showID;
    private boolean showName;
    protected boolean useBGMap = false;
    private String filename;

    private int totalMileage;

    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<String> IDs = new ArrayList<String>();
    private ArrayList<Integer> xList = new ArrayList<Integer>();
    private ArrayList<Integer> yList = new ArrayList<Integer>();
    private ArrayList<Integer> mileages = new ArrayList<Integer>();

    public static final double FILE_SCALE = 5.0;
    public static final double RAW_WIDTH = 800;
    public static final double RAW_HEIGHT = 400;
    public static final double FILE_WIDTH = RAW_WIDTH*(FILE_SCALE/(RAW_WIDTH/360));
    public static final double FILE_HEIGHT = RAW_HEIGHT*(FILE_SCALE/(RAW_HEIGHT/180));


    public static final String BACKGROUND_FILE_NAME = "World4.svg";

    private String[] itineraryStrings;
    //private GUI gui;

    public TripFileCreator(){}

    public void clear(){
        names.clear();
        IDs.clear();
        xList.clear();
        yList.clear();
        mileages.clear();
        itineraryStrings = null;
        totalMileage = 0;
    }

    public void addLocation(String name, String ID, double longitude, double latitude, int mileageToNextLoc) {
        totalMileage += mileageToNextLoc;
        names.add(name);
        IDs.add(ID);

        int pixLong = longToPix(longitude);
        xList.add(pixLong);

        int pixLat = latToPix(latitude);
        yList.add(pixLat);

        mileages.add(mileageToNextLoc);
    }

    protected int longToPix(double longitude) {
        longitude =  (180 + longitude)*FILE_SCALE;
        int longPix = (int)(Math.rint(longitude));
        return longPix;
    }

    protected int latToPix(double latitude) {
        latitude = (90 - latitude)*FILE_SCALE;
        int latPix = (int)(Math.rint(latitude));
        return latPix;
    }


    protected void writeLegsToSVG(PrintWriter writer){
        //write the legs to the file
        writer.println("<g>");
        writer.println("<title>Legs</title>");

        for (int i = 0; i < mileages.size(); i++) {
            int secondY;
            if (i + 1 >= yList.size()) {
                secondY = yList.get(0);
            } else {
                secondY = yList.get(i + 1);
            }

            int secondX;
            if (i + 1 >= xList.size()) {
                secondX = xList.get(0);
            } else {
                secondX = xList.get(i + 1);
            }

            writer.println("<line id=\"leg1\" y2=\"" + secondY + "\" x2=\""
                    + secondX + "\" y1=\"" + yList.get(i) + "\" x1=\""
                    + xList.get(i) + "\" stroke-width=\"3\" stroke=\"#999999\"/>");
        }

        writer.println("</g>");
    }

    protected void writeTitlesToSVG(PrintWriter writer){
        double milePosition = (FILE_HEIGHT) - 20;
        double stateMid = ((FILE_WIDTH) / 2.0);

        //write the titles to the file
        writer.println("<g>");
        writer.println("<text text-anchor=\"middle\" font-family=\"Sans-serif\" font-size=\"24\" id=\"distance\" y=\"" + milePosition + "\" x=\"" + stateMid + "\">" + totalMileage + " miles</text>");
        writer.println("</g>");
    }

    protected void writeLocationsToSVG(PrintWriter writer){
        //write the locations to the file
        writer.println("<g>");
        writer.println("<title>Locations</title>");
        if (showID) {
            for (int i = 0; i < IDs.size(); i++) {
                writer.println("<text font-family=\"Sans-serif\" font-size=\"16\" id=\"id1\" y=\"" + yList.get(i) + "\" x=\"" + xList.get(i) + "\">" + IDs.get(i) + "</text>");
            }
        } else if (showName) {
            for (int i = 0; i < names.size(); i++) {
                writer.println("<text font-family=\"Sans-serif\" font-size=\"16\" id=\"id1\" y=\"" + yList.get(i) + "\" x=\"" + xList.get(i) + "\">" + names.get(i) + "</text>");
            }
        }
        writer.println("</g>");
    }

    protected void writeMileagesToSVG(PrintWriter writer){
        //write the distances to the file
        writer.println("<g>");
        writer.println("<title>Distances</title>");
        for (int i = 0; i < mileages.size(); i++) {
            int secondX;
            if (i + 1 >= xList.size()) {
                secondX = xList.get(0);
            } else {
                secondX = xList.get(i + 1);
            }
            int secondY;
            if (i + 1 >= yList.size()) {
                secondY = yList.get(0);
            } else {
                secondY = yList.get(i + 1);
            }

            //set the mileage label's X value
            int milx = (xList.get(i) + secondX)/2;
            //set the mileage label's Y value
            int mily = (yList.get(i) + secondY)/2;

            writer.println("<text font-family=\"Sans-serif\" font-size=\"16\" id=\"leg1\" y=\"" + mily + "\" x=\"" + milx + "\">" + mileages.get(i) + "</text>");
        }
        writer.println("</g>");
    }

    protected void writeHeader(PrintWriter writer){
        writer.println("<?xml version=\"1.0\"?>");
        writer.println("<svg width=\""  + FILE_WIDTH + "\" height=\"" + FILE_HEIGHT + "\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\">");
    }

    protected void writeBackground(PrintWriter writer)
    {
        double matrixScale = FILE_SCALE/(RAW_WIDTH/360);
        File file = new File(BACKGROUND_FILE_NAME);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            writer.println(line);
            writer.println("<svg \n" +
                    "\txmlns:svg=\"http://www.w3.org/2000/svg\" \n" +
                    "\txmlns=\"http://www.w3.org/2000/svg\" \n" +
                    "\twidth=\"" + FILE_WIDTH + "\" height=\"" + FILE_HEIGHT + "\">\n" +
                    "<g transform=\"matrix(" + matrixScale + " 0 0 " + matrixScale + " 0 0)\">");
            while ((line = br.readLine()) != null) {
                writer.println("    " + line);
            }
            writer.println("</g>\n");
            br.close();

        } catch(IOException exception){
            System.out.println("Could not open file: " + BACKGROUND_FILE_NAME);
        }
    }
    //creates the svg file
    public void printSVGFile(String filename, boolean showID, boolean showName, boolean showMileage, boolean useBGMap) {

        this.showID = showID;
        this.showName = showName;
        this.showMileage = showMileage;
        this.useBGMap = useBGMap;

        String svgName = filename;

        //writes to the svg file
        try {
            PrintWriter writer = new PrintWriter(svgName, "UTF-8");

            if(useBGMap){
                writeBackground(writer);
            }else{
                writeHeader(writer);
            }

            //write the legs to the file
            writeLegsToSVG(writer);


            writeTitlesToSVG(writer);

            //check for the ID or Name flag
            if (showID || showName) {
                writeLocationsToSVG(writer);
            }

            //check for the Mileage flag
            if (showMileage) {
                writeMileagesToSVG(writer);
            }

            writer.println("</svg>");
            writer.close();

        } catch (Exception e) {
            System.out.println("An error has occurred while writing to the svg file.");
            e.printStackTrace();
        }

        //gui.startGUI();
        //gui.setSVGFilePath(svgName);
    }

    protected void printXMLFile(String fileName){
        //creates the name of the xml file
        itineraryStrings = new String[mileages.size()];
        //writes to the xml file
        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<trip>");

            for (int i = 0; i < mileages.size(); i++) {
                String secondName;
                String itineraryString = "";
                if (i + 1 >= names.size()) {
                    secondName = names.get(0);
                } else {
                    secondName = names.get(i + 1);
                }

                writer.println("<leg>");
                writer.println("<sequence>" + (i + 1) + "</sequence>");
                writer.println("<start>" + names.get(i) + "</start>");
                writer.println("<finish>" + secondName + "</finish>");
                writer.println("<mileage>" + mileages.get(i) + "</mileage>");
                writer.println("</leg>");

                itineraryString = itineraryString
                        + (i + 1) + " "
                        + names.get(i) + " to "
                        + secondName + ", "
                        + mileages.get(i) + " miles";
                itineraryStrings[i] = itineraryString;
            }
            writer.println("</trip>");
            writer.close();

        } catch (Exception e) {
            System.out.println("An error has occurred while writing to the xml files.");
            e.printStackTrace();
        }
    }

    public String[] getItineraryStrings(){
        return itineraryStrings;
    }

}
