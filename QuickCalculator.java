/**
 * Author: Audrey Trinh
 * Purpose: A class that takes multiple expressions from the command line,
 * compute them and print out the results.
 */
public class QuickCalculator {
    public static void main(String[] args) {
        BFCalculator calculator = new BFCalculator();
        // read the command line arguments
        for (int i = 0; i < args.length; i++) {
            // if user enter 'quit', then quit
            if (args[i].equalsIgnoreCase("QUIT")) {
                break;
            } else if (args[i].startsWith("STORE ")) {
                // store to the register
                char register = args[i].charAt(6);
                calculator.store(register);
            } else {
                // evaluate the expression
                calculator.evaluate(args[i]);
            }
        }
    }
}
