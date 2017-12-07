package reactiveminer.io;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Class to read a trace composed by a string of characters, where each character is an event.
 */
public class StringTraceReader implements TraceReader {
    private String trace;

    /**
     * Initialize the reader with a string representing a trace of characters
     *
     * @param trace string where each character is an event
     */
    public StringTraceReader(String trace) {
        this.trace = trace;
    }

    /**
     * @return trace as a list of characters
     */
    @Override
    public List<?> getEventsList() {
        return Arrays.asList(trace.toCharArray());
    }

    /**
     * @param traceIndex Index of the desired trace
     * @return reader object for the required event
     */
    @Override
    public EventReader getEventReader(int traceIndex) {
        return new StringEventReader(trace.charAt(traceIndex));
    }

    @Override
    public Iterator<EventReader> iterator() {
        EventReader[] erIter = new EventReader[trace.length()];
        for (int i = 0; i < trace.length(); i++) {
            erIter[i] = new StringEventReader(trace.charAt(i));
        }
        return Arrays.asList(erIter).iterator();
    }

}
