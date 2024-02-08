/*
 * Nicholas McCarty
 * CSE 465
 * Z+- Interpreter
 */
public class Zpm {

    /*
     * Our main method. This method makes sure we recieve an input file
     * so we can interpret the Z+-.
     */
    public static void main(String[] args) throws Exception {
        // Requiring a input file
        if (args.length != 1) {
            System.out.println("Usage: java Zpm <filename.zpm>");
            return;
        }
        doNext(args[0]);
    }

    public static void doNext(String fileName) throws Exception {
        // This is from ChatGPT
        
    }
}
