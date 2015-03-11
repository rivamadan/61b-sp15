package ngordnet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class TimeSeries<T extends Number> extends TreeMap<Integer, T> {

    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /**
     * Returns the years in which this time series is valid. Doesn't really need
     * to be a NavigableSet. This is a private method and you don't have to
     * implement it if you don't want to.
     */
    private NavigableSet<Integer> validYears(TimeSeries<? extends Number> ts) {
        NavigableSet<Integer> valid = new TreeSet<Integer>();
        Collection<Integer> thisYear = keySet();
        for (Integer year : thisYear) {
            if (ts.containsKey(year)) {
                valid.add(year);
            }
        }
        return valid;
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR. inclusive
     * of both end points.
     */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {
        super(ts.subMap(startYear, true, endYear, true));
    }

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
        super(ts);
    }

    /**
     * Returns the quotient of this time series divided by the relevant value in
     * ts. If ts is missing a key in this time series, return an
     * IllegalArgumentException.
     */
    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> divided = new TimeSeries<Double>();
        NavigableSet<Integer> sharedYears = validYears(ts);
        if (ts.size() < this.size() || sharedYears.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (ts.size() > this.size()) {
            Collection<Integer> tsYear = ts.keySet();
            for (Integer year : tsYear) {
                sharedYears.add(year);
            }
        }

        for (Integer year : sharedYears) {
            if (this.containsKey(year)) {
                double dividedVal = (this.get(year).doubleValue() / ts.get(year).doubleValue());
                divided.put(year, dividedVal);
            } else {
                divided.put(year, 0.0);
            }
        }
        return divided;
    }

    /**
     * Returns the sum of this time series with the given ts. The result is a a
     * Double time series (for simplicity).
     */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> sum = new TimeSeries<Double>();
        Collection<Integer> yearInt = keySet();
        Collection<Integer> tsYear = ts.keySet();
        for (Integer year : tsYear) {
            sum.put(year, ts.get(year).doubleValue());
        }
        for (Integer year : yearInt) {
            if (ts.containsKey(year)) {
                double addVal = (this.get(year).doubleValue() + ts.get(year).doubleValue());
                sum.put(year, addVal);
            } else {
                sum.put(year, this.get(year).doubleValue());
            }
        }
        return sum;
    }

    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
        Collection<Integer> yearInt = keySet();
        Collection<Number> yearNum = new ArrayList<Number>();
        for (Integer eachYear : yearInt) {
            yearNum.add(eachYear);
        }
        return yearNum;

    }

    /**
     * Returns all data for this time series. Must be in the same order as
     * years().
     */
    public Collection<Number> data() {
        Collection<T> dataT = values();
        Collection<Number> dataNum = new ArrayList<Number>();
        for (T eachData : dataT) {
            dataNum.add(eachData);
        }
        return dataNum;

    }
}
