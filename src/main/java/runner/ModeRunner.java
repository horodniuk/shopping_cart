package runner;


/*
 * Interface for working with different modes of data output to the console
 * method start() - starts reading and executing commands depending on mode
 * For example:
 * if it is Interactive mode - we read the commands from console
 * if it is File mode - we read the commands from file
 *
 */
public interface ModeRunner {
    void start();
}
