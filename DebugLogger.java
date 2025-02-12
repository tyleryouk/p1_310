import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * DebugLogger provides file-based logging for debug messages.
 */
public class DebugLogger {
    /**
     * The PrintStream used for writing debug messages to a file.
     */
    private static PrintStream debugOut;
    
    static {
        try {
            // Open the file "debug_output.txt" in append mode.
            // Since the working directory is already "yourCodeHere",
            // this will create or open p1/yourCodeHere/debug_output.txt.
            debugOut = new PrintStream(new FileOutputStream("debug_output.txt", true));
        } catch (IOException e) {
            // Throw an unchecked exception instead of falling back to System.out.
            throw new RuntimeException("Unable to create debug log file", e);
        }
    }
    
    /**
     * Writes a message to the debug output.
     *
     * @param message the message to be logged.
     */
    public static void println(String message) {
        debugOut.println(message);
    }
} 