package reactiveminer.io;

import java.util.List;

/**
 * Interface to read traces of a Log regardless the source format.
 */
public interface LogReader extends Iterable<TraceReader>{

    /**
     * @return List of traces in the log
     */
    List<?> getTracesList();

    /**
     * @param traceIndex Index of the desired trace
     * @return Object ot read the specified trace
     */
    TraceReader getTraceReader(int traceIndex);

}
