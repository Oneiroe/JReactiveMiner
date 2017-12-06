package reactiveminer.io;

import java.util.Arrays;
import java.util.List;

/**
 *  Class to read a trace composed by a string of characters, where each character is an event.
 */
public class StringTraceReader implements TraceReader {
    private String trace;
    private int index;

    /**
     * Initialize the reader with a string representing a trace of characters
     * @param trace string where each character is an event
     */
    public StringTraceReader(String trace) {
        this.trace = trace;
        index = 0;
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
     * @return  reader object for the required event
     */
    @Override
    public EventReader getEventReader(int traceIndex) {
        return new StringEventReader(trace.charAt(traceIndex));
    }

    /**
     * @return reader for the current event
     */
    @Override
    public EventReader getCurrentEventReader() {
        return new StringEventReader(trace.charAt(index));
    }

    /**
     * @return true if there are more characters in the trace
     */
    @Override
    public boolean hasNext() {
        return index < trace.length();
    }

    /**
     * @return next event in the trace
     */
    @Override
    public Character next() {
        char res = trace.charAt(index);
        index++;
        return res;
    }
}
