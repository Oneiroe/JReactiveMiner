package reactiveminer.io;

import org.deckfour.xes.classification.XEventClassifier;
import org.deckfour.xes.in.XesXmlParser;
import org.deckfour.xes.model.XLog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Class to read a XES file, loading it all in memory.
 */
public class XesFileReader implements FileReader {
    private List<XLog> logs;
    private int index;


    /**
     * @param inputXesFile path to the xes file to parse
     */
    public XesFileReader(Path inputXesFile) {

        try {
            File inFile = new File(inputXesFile.toAbsolutePath().toString());
            this.logs = new XesXmlParser().parse(inFile);
            index = 0;
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception");
            e.printStackTrace();
        }
    }


    /**
     * @return if there are more logs in the file
     */
    @Override
    public boolean hasNext() {
        return index < (logs.size() );
    }

    /**
     * @return Next log of the file
     */
    @Override
    public XLog next() {
        return logs.get(index++);
    }

    /**
     * @return the list of logs in the file
     */
    @Override
    public List<XLog> getLogsList() {
        return logs;
    }


    /**
     * @return TraceReader of the specified log
     */
    @Override
    public LogReader getLogReader(int logIndex) {
        XesLogReader res = new XesLogReader(logs.get(logIndex));
        return res;
    }

    /**
     * @return TraceReader of the current log
     */
    @Override
    public LogReader getCurrentLogReader() {
        return (new XesLogReader(logs.get(index)));
    }
}
