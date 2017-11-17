package reactiveminer.io;

import java.util.Iterator;
import java.util.List;

/**
 * Interface to read events of a Trace regardless the source format.
 */
public interface TraceReader extends Iterator {
    /**
     * @return List of event present in the trace
     */
    List<?> getEventsList();

    /**
     * @param traceIndex Index of the desired trace
     * @return Object ot read the specified trace
     */
    EventReader getEventReader(int traceIndex);

    /**
     * @return Object ot read the current trace
     */
    EventReader getCurrentEventReader();
}
