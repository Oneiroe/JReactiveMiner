package reactiveminer.io;

import org.deckfour.xes.in.XesXmlParser;
import org.deckfour.xes.model.XLog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class to read a XES file, loading it all in memory.
 */
public class XesFileReader implements FileReader {
    private List<XLog> logs;


    /**
     * @param inputXesFile path to the xes file to parse
     */
    public XesFileReader(Path inputXesFile) {

        try {
            File inFile = new File(inputXesFile.toAbsolutePath().toString());
            this.logs = new XesXmlParser().parse(inFile);
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
        }
    }

    /**
     * @return the list of logs in the file
     */
    @Override
    public List<XLog> getLogsList() {
        return logs;
    }


    /**
     * Get a reader for a specific log present in the file
     *
     * @param logIndex index of the desired log
     * @return TraceReader of the specified log
     */
    @Override
    public LogReader getLogReader(int logIndex) {
        return new XesLogReader(logs.get(logIndex));
    }

    /**
     * @return Iterator of XesLogReaders for the logs present in the file
     */
    @Override
    public Iterator<LogReader> iterator() {
        ArrayList<LogReader> res = new ArrayList<>();
        for (XLog log : logs) {
            res.add(new XesLogReader(log));
        }
        return res.iterator();
    }
}
