// Nicholas Mccarty
// CSE 465
// Z+- Interpreter
import java.util.HashMap;
import java.util.ArrayList; // Import ArrayList
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Zpm {

    protected static final HashMap<Integer, String> linesMap = new HashMap<>();
    protected static final ArrayList<String> lines = new ArrayList<String>();
    

    /*
     * Our main method. This method makes sure we receive an input file
     * so we can interpret the Z+-.
     */
    public static void main(String[] args) throws Exception {
        // Requiring an input file
        if (args.length != 1) {
            System.out.println("Usage: java Zpm <filename.zpm>");
            return;
        }
        doNext(args[0]);
        testRead();
    }

    /*
     * This is where we will start doing the magic. 
     */
    public static void doNext(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lines.add(line); // Add the line number to the ArrayList
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    /*
     * A simple method for debugging purposes.
     */
    public static void testRead() {
        for (String line : lines) {
            System.out.println(line);
            interpretLine(line);
        }
    }
    
    public static void interpretLine(String line) {
        if (line.isEmpty()) {
            return;
        }
    
        // Corrected line: removed the parameter name before the argument
        String[] splitter = line.split("\\s+");

        if (splitter.length < 3 || (!splitter[splitter.length - 1].equals(";") && !splitter[splitter.length - 1].equals("ENDFOR"))) {
            System.out.println("Sytnax Error " + line);
        }

        switch(splitter[0]) {
            case "PRINT":
                printOut(splitter[1]);
                break;
            default:
                doAssign(splitter);
        }

    }

    public static void printOut(String line) {
        System.out.println("hello");

        Object currVal = linesMap.get(line);

        if (currVal != null) {
            System.out.println(line + "=" + currVal);
        } else {
            throw new RuntimeException();
        }
    }
    
    public static void doAssign() {
        
    }

}