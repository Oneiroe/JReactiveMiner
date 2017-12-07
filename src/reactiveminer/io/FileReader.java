package reactiveminer.io;

import java.util.List;

/**
 * Interface to read a file and extract Log out of it regardless the source format
 */
public interface FileReader extends Iterable<LogReader> {

    /**
     * @return List of logs present in the file
     */
    List<?> getLogsList();

    /**
     * @param logIndex Index of the desired log
     * @return Object to read the specified log
     */
    LogReader getLogReader(int logIndex);

}
