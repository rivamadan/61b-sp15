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
    public void testCopyIntervalConstructor() {
        TimeSeries<Double> tsCopy = new TimeSeries<Double>(ts, 1993, 1995);
        TimeSeries<Double> expected = new TimeSeries<Double>();
        expected.put(1993, 9.2);
        expected.put(1994, 15.2);
        expected.put(1995, 16.1);
        assertEquals(expected, tsCopy);
        assertNotEquals(tsCopy, ts);
    }
    
    @Test
    public void testCopyConstructor() {
        TimeSeries<Double> tsCopy = new TimeSeries<Double>(ts);
        assertEquals(tsCopy, ts);
    }
    
    @Test
    public void testDivideBy() {
        TimeSeries<Integer> ts2 = new TimeSeries<Integer>();
        ts2.put(1991, 10);
        ts2.put(1992, 5);   

        TimeSeries<Double> ts3 = new TimeSeries<Double>();
        ts3.put(1991, 5.0);
        ts3.put(1992, 2.0);
        ts3.put(1993, 100.0);

        TimeSeries<Double> tQuotient = ts2.dividedBy(ts3);
        
        assertEquals((Double) 2.0, tQuotient.get(1991));
        assertEquals((Double) 2.5, tQuotient.get(1992));
        assertEquals((Double) 0.0, tQuotient.get(1993));
    }
    
    @Test
    public void testPlus() {
        TimeSeries<Integer> ts2 = new TimeSeries<Integer>();
        ts2.put(1991, 10);
        ts2.put(1992, 5);
        ts2.put(1993, 1);

        TimeSeries<Double> tSum = ts.plus(ts2);
        assertEquals((Double) 10.0, tSum.get(1991));
        assertEquals((Double) 8.6, tSum.get(1992));
    }
    
    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(TimeSeriesTest.class);
    }
}
