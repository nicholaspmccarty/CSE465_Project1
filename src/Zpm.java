/**
 * DISCLAIMER: ChatGPT was used extensively for this project. 
 *
 * Author: Nicholas McCarty
 * This class implements a simple script interpreter that processes commands from a file. It supports basic
 * operations like printing variables, assignment, addition, multiplication, and executing for loops with
 * numeric iterations.
 */

 import java.util.HashMap;
 import java.util.ArrayList;
 import java.io.BufferedReader;
 import java.io.FileReader;
 import java.io.IOException;
 
 public class Zpm {
 
     protected static final HashMap<String, Object> variables = new HashMap<>();
     protected static final ArrayList<String> lines = new ArrayList<>();
 
     public static void main(String[] args) throws Exception {
         if (args.length != 1) {
             System.out.println("Usage: java Zpm <filename.zpm>");
             return;
         }
         readFile(args[0]);
         executeLines();
     }
 
     /**
      * Reads lines from the specified file and stores them in an ArrayList.
      * 
      * @param fileName The name of the file to read.
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
      * Executes each line read from the file.
      */
     public static void executeLines() {
         for (String line : lines) {
             interpretLine(line);
         }
     }
 
     /**
      * Interprets and executes a single line of input.
      * 
      * @param line The line to be interpreted.
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
                 try {
                     executeForLoop(Integer.parseInt(parts[1]), parts);
                 } catch (NumberFormatException e) {
                     System.err.println("FOR loop expects a numeric iteration count.");
                 }
                 break;
             default:
                 processAssignment(parts);
         }
     }
 
     /**
      * Prints the value of a specified variable.
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
      * @param parts The split parts of the input line, containing variable name, operator, and value.
      */
     public static void processAssignment(String[] parts) {
         String variable = parts[0];
         String operator = parts[1];
         Object value = parseValue(parts[2]);
 
         if (value instanceof String) {
             if (operator.equals("=")) {
                 variables.put(variable, value);
             } else if (operator.equals("+=")) {
                 String existingValue = variables.containsKey(variable) ? variables.get(variable).toString() : "";
                 variables.put(variable, existingValue + value);
             } else {
                 System.err.println("Unsupported operation for strings.");
             }
         } else if (value instanceof Integer) {
             int intValue = (Integer)value;
             switch(operator) {
                 case "=":
                     variables.put(variable, intValue);
                     break;
                 case "+=":
                     variables.put(variable, getVariableValue(variable) + intValue);
                     break;
                 case "*=":
                     variables.put(variable, getVariableValue(variable) * intValue);
                     break;
                 default:
                     System.err.println("Unsupported operator: " + operator);
                     break;
             }
         }
     }
 
     /**
      * Returns the integer value of a specified variable.
      * 
      * @param variable The name of the variable.
      * @return The integer value of the variable.
      */
     public static int getVariableValue(String variable) {
         Object value = variables.getOrDefault(variable, 0);
         if (value instanceof Integer) {
             return (Integer)value;
         }
         return 0;
     }
 
     /**
      * Parses a value as an integer or retrieves it from variables if it is a variable name.
      * 
      * @param value The value to parse.
      * @return The parsed value.
      */
     public static Object parseValue(String value) {
         if (isNumeric(value)) {
             return Integer.parseInt(value);
         } else if (variables.containsKey(value)) {
             return variables.get(value);
         } else {
             return value; // Treat as a string literal
         }
     }
 
     /**
      * Executes a for loop with a specified number of iterations.
      * 
      * @param iterations The number of iterations to execute.
      * @param parts The split parts of the input line, containing loop control variables.
      */
     public static void executeForLoop(int iterations, String[] parts) {
         for (int i = 0; i < iterations; i++) {
             variables.put("B", getVariableValue("B") + getVariableValue("A"));
             variables.put("A", getVariableValue("A") * 2);
         }
     }
 
     /**
      * Checks if a string is numeric.
      * 
      * @param str The string to check.
      * @return True if the string is numeric, false otherwise.
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
 
