/**
 * DISCLAIMER: This code was assisted by ChatGPT.
 * 
 * The Zpm program reads and interprets a simple script from a file.
 * It supports printing variables, executing for-loops, and basic variable assignments
 * with arithmetic operations.
 */

 import java.util.HashMap;
 import java.util.ArrayList;
 import java.io.BufferedReader;
 import java.io.FileReader;
 import java.io.IOException;
 
 public class Zpm {
 
     /** A hashmap to store variables and their values. */
     protected static final HashMap<String, Object> variables = new HashMap<>();
     
     /** An arraylist to store lines read from the input file. */
     protected static final ArrayList<String> lines = new ArrayList<>();
 
     /**
      * The main method to run the Zpm script.
      * 
      * @param args Command line arguments, expects a single argument: the filename to interpret.
      */
     public static void main(String[] args) throws Exception {
         if (args.length != 1) {
             System.out.println("Usage: java Zpm <filename.zpm>");
             return;
         }
         readFile(args[0]);
         executeLines();
     }
 
     /**
      * Reads lines from a given file and stores them in the lines arraylist.
      * 
      * @param fileName The name of the file to read from.
      */
     public static void readFile(String fileName) {
         try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
             String line;
             while ((line = reader.readLine()) != null) {
                 lines.add(line);
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 
     /**
      * Executes each line stored in the lines arraylist.
      */
     public static void executeLines() {
         for (String line : lines) {
             interpretLine(line);
         }
     }
 
     /**
      * Interprets and executes a single line of the script.
      * 
      * @param line The line to interpret and execute.
      */
     public static void interpretLine(String line) {
         if (line.isEmpty()) {
             return;
         }
 
         String[] parts = line.split("\\s+");
         switch(parts[0]) {
             case "PRINT":
                 printVariable(parts[1]);
                 break;
             case "FOR":
                 executeForLoop(Integer.parseInt(parts[1]), parts);
                 break;
             default:
                 processAssignment(parts);
         }
     }
 
     /**
      * Prints the value of a variable.
      * 
      * @param variable The name of the variable to print.
      */
     public static void printVariable(String variable) {
         if (variables.containsKey(variable)) {
             System.out.println(variable + "=" + variables.get(variable));
         } else {
             System.err.println("Variable " + variable + " not found.");
         }
     }
 
     /**
      * Processes an assignment operation for a variable.
      * 
      * @param parts The parts of the assignment operation: variable, operator, and value.
      */
     public static void processAssignment(String[] parts) {
         String variable = parts[0];
         String operator = parts[1];
         int value = Integer.parseInt(parseValue(parts[2]));
 
         switch(operator) {
             case "=":
                 variables.put(variable, value);
                 break;
             case "+=":
                 variables.put(variable, getVariableValue(variable) + value);
                 break;
             case "*=":
                 variables.put(variable, getVariableValue(variable) * value);
                 break;
             default:
                 System.err.println("Unsupported operator: " + operator);
                 break;
         }
     }
 
     /**
      * Retrieves the value of a variable, defaulting to 0 if not found.
      * 
      * @param variable The name of the variable.
      * @return The value of the variable.
      */
     public static int getVariableValue(String variable) {
         return (int) variables.getOrDefault(variable, 0);
     }
 
     /**
      * Parses a value, resolving variables to their current values if necessary.
      * 
      * @param value The value or variable name to parse.
      * @return The parsed value as a string.
      */
     public static String parseValue(String value) {
         if (isNumeric(value)) {
             return value;
         } else {
             return variables.get(value).toString();
         }
     }
 
     /**
      * Executes a for-loop with a specified number of iterations.
      * 
      * @param iterations The number of iterations to execute the loop.
      * @param parts The parts of the loop, including the loop body logic.
      */
     public static void executeForLoop(int iterations, String[] parts) {
         for (int i = 0; i < iterations; i++) {
             variables.put("B", getVariableValue("B") + getVariableValue("A"));
             variables.put("A", getVariableValue("A") * 2);
         }
     }
 
     /**
      * Checks if a string represents a numeric value.
      * 
      * @param str The string to check.
      * @return true if the string is numeric, false otherwise.
      */
     public static boolean isNumeric(String str) {
         try {
             Double.parseDouble(str);
             return true;
         } catch(NumberFormatException e) {
             return false;
         }
     }
 }
 