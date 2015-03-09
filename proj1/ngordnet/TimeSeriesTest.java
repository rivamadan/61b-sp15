package ngordnet;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.junit.Before;

public class TimeSeriesTest {
    private TimeSeries<Double> ts;

    @Before
    public void Setup() {
        ts = new TimeSeries<Double>();
        ts.put(1992, 3.6);
        ts.put(1993, 9.2);
        ts.put(1994, 15.2);
        ts.put(1995, 16.1);
        ts.put(1996, -15.7);
    }
    
    @Test
    public void testYear() {
        Collection<Number> years = ts.years();

        for (Number yearNumber : years) {  
            int year = yearNumber.intValue();
            double value = ts.get(year);
            System.out.println("In the year " + year + " the value was " + value);
        }
    }
        
    @Test
    public void testData() {
        Collection<Number> data = ts.data();
        for (Number dataNumber : data) {  
            double datum = dataNumber.doubleValue();
            System.out.println("In some year, the value was " + datum);
        }
    }
    
    @Test
    public void testCopyConstructor() {
        TimeSeries<Double> tsCopy = new TimeSeries<Double>(ts, 1993, 1995);
        Collection<Number> yearsCopy = tsCopy.years();
        Collection<Number> dataCopy = tsCopy.data();
        
        for (Number yearNumber : yearsCopy) { 
            int year = yearNumber.intValue();
            double value = tsCopy.get(yearsCopy);
            System.out.println("Copy: In the year " + year + " the value was " + value);
        }

        for (Number dataNumber : dataCopy) {
            double datum = dataNumber.doubleValue();
            System.out.println("Copy: In some year, the value was " + datum);
        }  
    }
    
    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(TimeSeriesTest.class);
    }
}
