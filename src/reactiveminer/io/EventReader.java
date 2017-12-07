package reactiveminer.io;

/**
 * Interface to read Event unique identifier regardless the source format.
 */
public interface EventReader {
    /**
     * @return unique id of the event
     */
    Object getId();
}
