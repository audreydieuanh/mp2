import java.io.PrintWriter;

/**
 * Author: Audrey Trinh
 * Purpose: The workhouse for BigFraction. Include methods for evaluating an expression
 * of integers and BigFractions and store the result to the register.
 */
public class BFCalculator {

    // +--------+-------------------------------------------------------
    // | Fields |
    // +--------+

    /**
     * The current result of the calculator. Can be positive, zero or negative.
     */
    BigFraction currentResult;

    /**
     * An array of 26 big fractions used to store results corresponding to 26 lowercase letters.
     */
    BigFraction[] registerList = new BigFraction[26];

    /**
     * Evaluate the expression passed in as a parameter, ignoring priority.
     */
    public void evaluate(String exp) {
        // PrintWriter object used for printing
        PrintWriter pen = new PrintWriter(System.out, true);
        // Split exp into a String array, separated by space
        String[] elements = exp.split("\\s+");
        // Create a String array for the number elements
        String[] numbers = new String[elements.length / 2 + 1];
        // Create a String array for the operator elements
        String[] operators = new String[elements.length / 2];

        // if expression has even elements
        if (elements.length % 2 == 0) {
            // print error that expression is invalid and return
            System.err.println("Invalid expression. There are at least two numbers or two operators in a row.");
            return;
        }

        // for loop
        for (int i = 0; i < numbers.length; i++) {
            // Copy numbers from the expression into String array
            numbers[i] = elements[i * 2];
            if (i < numbers.length - 1) {
                // Copy operators from the expression into String array
                operators[i] = elements[i * 2 + 1];
            }
        }

        // if the number array or operator array is invalid
        if (isNumberInvalid(numbers) || isOperatorInvalid(operators)) {
            return;
        }
        // if the first number element is not a letter
        if (!Character.isLetter(numbers[0].charAt(0))) {
            // save the number into currentResult
            this.currentResult = new BigFraction(numbers[0]);
        } else {
            // if the register is stored
            if (canTranslate(numbers[0].charAt(0))) {
                this.currentResult = registerList[(int) numbers[0].charAt(0) - 97];
            } else {
                return;
            }
        }

        // if there are more than 1 number/register in the expression
        if (numbers.length > 1) {
            for (int j = 0; j < numbers.length - 1; j++) {
                // save the next number/register into a variable
                char register = numbers[j + 1].charAt(0);
                BigFraction nextFraction;
                // if the number/register is not a letter
                if (!Character.isLetter(register)) {
                    // save the nextFraction
                    nextFraction = new BigFraction(numbers[j + 1]);
                } else {
                    // if the register is stored
                    if (canTranslate(register)) {
                        nextFraction = registerList[(int) register - 97];
                    } else {
                        return;
                    }

                }
                // evaluate the expression and save it into current result
                this.currentResult = operator(this.currentResult, nextFraction, operators[j]);
            }
        }
        // simplify the result
        this.currentResult.simplify();
        // print the result
        pen.println(exp + " = " + this.currentResult);
    }

    /**
     * Evaluate two BigFraction frac1, frac2 based on the operator that are passed in as String
     */
    public BigFraction operator(BigFraction frac1, BigFraction frac2, String operator) {
        BigFraction result = null;
        switch (operator) {
            // if operator is '+'
            case "+":
                result = frac1.add(frac2);
                break;
            // if operator is '-'
            case "-":
                result = frac1.subtract(frac2);
                break;
            // if operator is '/'
            case "/":
                result = frac1.divide(frac2);
                break;
            // if operator is '*'
            case "*":
                result = frac1.multiply(frac2);
                break;
        }
        return result;
    }

    /**
     * Store the current result in the register
     */
    public void store(char register) {
        registerList[(int) register - 97] = this.currentResult;
    }

    /**
     * Return true and print error if the array of numbers in the expression is invalid, false if not
     */
    public boolean isNumberInvalid(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // if the register is not a one-letter character, print error
            if (Character.isLetter(arr[i].charAt(0)))
                if (arr[i].length() != 1) {
                    System.err.println("Invalid form of register. A register can only be a lowercase letter");
                    return true;
                } else {
                    return false;
                } // if there is a non-fraction in the number
            if (!isNumericString(arr[i]) && !isBigFraction(arr[i])) {
                System.err.println("Invalid expression. Can only take number or fraction, separated by space");
                return true;
            }
        }
        return false;
    }

    /**
     * Return true and print error if the array of operators in the expression is invalid, false if not
     */
    public boolean isOperatorInvalid(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // if the operator is not one of the four
            if (!arr[i].equals("+") && !arr[i].equals("-") && !arr[i].equals("*") && !arr[i].equals("/")) {
                System.err.println("Invalid expression. Only '+', '-', '*', '/' are qualified as operators.");
                return true;
            }
        }
        return false;
    }

    public boolean isBigFraction(String frac) {
        // if frac is not a fraction
        if (!frac.contains("/")) {
            return false;
        }
        int numIndex = 0;
        if (frac.charAt(0) == '-') {
            numIndex = 1;
        }
        // if the denominator or numerator is not a number
        return Character.isDigit(frac.charAt(numIndex)) || (Character.isDigit(frac.charAt(numIndex + 2)));
    }

    /**
     * return true if num is a numeric string, false if not
     *
     * @param num: a string
     */
    public boolean isNumericString(String num) {
        boolean result = Character.isDigit(num.charAt(0)) || num.charAt(0) == '-';
        for (int i = 1; i < num.length(); i++) {
            if (!Character.isDigit(num.charAt(i))) {
                result = false;
            }
        }
        return result;
    }

    /**
     * return true if register is stored, return false and print error if not
     */
    public boolean canTranslate(char register) {
        // take the number from registerList
        BigFraction extractedNum = registerList[(int) register - 97];
        // if the number is already stored
        if (extractedNum != null) {
            // result is the extractedNum
            return true;
        } else {
            // if not stored, print error and return
            System.err.println(register + " was not stored in the system. Please try again");
            return false;
        }
    }

}
