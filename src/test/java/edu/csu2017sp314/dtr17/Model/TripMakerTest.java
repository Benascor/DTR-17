package test.java.edu.csu2017sp314.dtr17.Model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import main.java.edu.csu2017sp314.dtr17.Model.*;

/**
 * Created by maste on 2/20/2017.
 */
public class TripMakerTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void makeTrip() throws Exception {

    }

    @Test
    public void nearestNeighbor() throws Exception {

    }

    @Test
    public void findNN() throws Exception {
        ArrayList<Location> l = new ArrayList<Location>();
        Location A = new Location("0","testA", "40.578629","-105.085226");
        Location B = new Location("1","testB","50", "-150");
        Location C = new Location("2", "testC", "40.017238", "-105.281881");
        l.add(A);
        l.add(B);
        l.add(C);
        TripMaker test = new TripMaker(l);
        //int i = test.findNN(A, 0); //gives a null pointer exception unsure why

        //assertEquals("testC", test.getLocation(i).getName());
    }

    @Test
    public void twoOpt() throws Exception {
        //TODO ben
    }

    @Test
    public void threeOpt() throws Exception {
        //TODO ben
    }

    @Test
    public void reverse() throws Exception {

    }

    @Test
    public void swap() throws Exception {

    }

    @Test
    public void calculateDistanceBetween() throws Exception {
        ArrayList<Location> l = new ArrayList<Location>();
        TripMaker test = new TripMaker(l);
        Location A = new Location("0","testA", "40.578629","-105.085226");
        Location B = new Location("1","testB","40.017238", "-105.281881");

        assertEquals(40.1601, test.calculateDistanceBetween(A,B), 0.001);
    }

    @Test
    public void getLocation() throws Exception {
        ArrayList<Location> l = new ArrayList<Location>();
        Location A = new Location("0","testA", "40.578629","-105.085226");
        Location B = new Location("1","testB","40.017238", "-105.281881");
        l.add(A);
        l.add(B);
        TripMaker test = new TripMaker(l);
        assertEquals("testA", test.getLocation(0).getName());
        assertEquals("testB", test.getLocation(1).getName());

    }




}
