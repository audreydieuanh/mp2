import java.util.Scanner;

/**
 * Author: Audrey Trinh
 * Purpose: A class that repeatedly read a line the user types, compute the result and
 * print the result for the user.
 */
public class InteractiveCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BFCalculator calculator = new BFCalculator();

        while (true) {
            // read a line user types
            String input = scanner.nextLine();
            // if user enter 'quit', then quit
            if (input.equalsIgnoreCase("QUIT")) {
                break;
            } else if (input.startsWith("STORE ")) {
                // store to the register
                char register = input.charAt(6);
                calculator.store(register);
            } else {
                // evaluate the expression
                calculator.evaluate(input);
            }
        }
    }
}
