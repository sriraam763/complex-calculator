/**
 * Contains functions to convert from infix to postfix as well as parse the postfix equation.
 */

package backend;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import javax.swing.JTextField;

public class Parser {
    /**
     * Text field output.
     */
    public static JTextField textField = new JTextField();

    /**
     * Determines whether to clear the output or not.
     */
    public static boolean clear_output = false;

    /**
     * Strores the last result.
     */
    public static BigDecimal ans = new BigDecimal(0);

    /**
     * Flag indicating radians or degrees mode.
     */
    public static boolean radians = true;

    /**
     * Checks the precedence of the operator
     * 
     * @param operator
     * @return
     */
    private static int prec(String operator) {
        String operators[] = { "-", "+", "x", "%", "/", "√", "^", "!", "sin", "cos", "tan", "asin", "acos", "atan" };
        return Arrays.asList(operators).indexOf(operator);
    }

    /**
     * Determines if a string is a number or not.
     * 
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void clearOutput() {
        if (clear_output) {
            textField.setText("");
            clear_output = false;
        }
    }

    public static BigDecimal calculate() {
        clear_output = true;
        if (textField.getText().isEmpty()) {
            ans.subtract(ans);
            textField.setText("");
            return new BigDecimal(0);
        }

        String infix[];
        try {
            infix = checkSyntaxErrors();
        } catch (NumberFormatException e) {
            textField.setText("Syntax Error!");
            return new BigDecimal(0);
        }

        try {
            ans = postfixParse(infixToPostfix(infix));
        } catch (Exception e) {
            textField.setText("Math Error!");
            return new BigDecimal(0);
        }

        textField.setText(Double.toString(ans.doubleValue()));
        return ans;
    }

    /**
     * Checks for syntax errors in an expression list.
     * 
     * @param expression
     * @return The infix format of the expression.
     */
    public static String[] checkSyntaxErrors() throws NumberFormatException {
        ArrayList<String> infix;
        try {
            infix = createExpression();
        } catch (Exception e) {
            textField.setText("Syntax Error!");
            throw new NumberFormatException("Syntax error");
        }

        if (!(isNumber(infix.get(0)) || Utils.valInArray(infix.get(0), Utils.FUNCTIONS)
                || (Utils.valInArray(infix.get(0), Utils.NON_OPERATORS) && !infix.get(0).equals(")")))) {
            textField.setText("Syntax Error!");
            throw new NumberFormatException("Syntax error");
        }

        for (int i = 0; i < infix.size(); i++) {
            if (isNumber(infix.get(i))
                    || (Utils.valInArray(infix.get(i), Utils.NON_OPERATORS) && !infix.get(i).equals(")"))) {
                if (i > 0 && Utils.valInArray(infix.get(i - 1), Utils.NON_OPERATORS)) {
                    textField.setText("Syntax Error!");
                    throw new NumberFormatException("Syntax error");
                }
            } else if (i > 0 && !isNumber(infix.get(i - 1))
                    && !Utils.valInArray(infix.get(i - 1), Utils.NON_OPERATORS)
                    && !Utils.valInArray(infix.get(i), Utils.FUNCTIONS)
                    && !(infix.get(i).equals("!") && infix.get(i - 1).equals("!"))) {
                textField.setText("Syntax Error!");
                throw new NumberFormatException("Syntax error");
            } else if (infix.get(i).equals(".")) {
                textField.setText("Syntax Error!");
                throw new NumberFormatException("Syntax error");
            }
        }

        return infix.toArray(new String[infix.size()]);
    }

    /**
     * Converts the input from the calculator into an expression list. Ensures that
     * any functions like
     * tan, sin, cos, ln, etc... are correct. If not it throws a
     * NumberFormatException.
     * 
     * @return
     * @throws NumberFormatException
     */
    public static ArrayList<String> createExpression() throws NumberFormatException {
        ArrayList<String> expression = new ArrayList<String>();

        char input[] = textField.getText().toCharArray();

        int i = 0;
        while (i < input.length) {
            char c = input[i];
            if (isNumber(Character.toString(c))) {
                // Check if the last element was a number or not
                if (!expression.isEmpty()) {
                    if (isNumber(expression.getLast())) {
                        expression.add(expression.removeLast() + c);
                    } else if (expression.getLast().equals("-") &&
                            (expression.size() > 1 && !isNumber(expression.get(expression.size() - 2))
                                    && !Utils.valInArray(expression.get(expression.size() - 2), Utils.NON_OPERATORS)
                                    || expression.size() == 1)) { // Check if the last character was -
                        expression.add(expression.removeLast() + c);
                    } else if (expression.getLast().equals(".")) {
                        expression.add(expression.removeLast() + c);
                    } else {
                        expression.add(Character.toString(c));
                    }
                } else {
                    expression.add(Character.toString(c));
                }
            } else {
                switch (c) {
                    // Tangent
                    case 't':
                        if (!expression.isEmpty() && (isNumber(expression.getLast())
                                || Utils.valInArray(expression.getLast(), Utils.NON_OPERATORS))) {
                            expression.add("x");
                        }
                        if (textField.getText().subSequence(i, i + 3).equals("tan")) {
                            expression.add("tan");
                            i += 2;
                        } else {
                            throw new NumberFormatException("Invalid function");
                        }
                        break;

                    // Sine
                    case 's':
                        if (!expression.isEmpty() && (isNumber(expression.getLast())
                                || Utils.valInArray(expression.getLast(), Utils.NON_OPERATORS))) {
                            expression.add("x");
                        }
                        if (textField.getText().subSequence(i, i + 3).equals("sin")) {
                            expression.add("sin");
                            i += 2;
                        } else {
                            throw new NumberFormatException("Invalid function");
                        }
                        break;

                    // Cosine
                    case 'c':
                        if (!expression.isEmpty() && (isNumber(expression.getLast())
                                || Utils.valInArray(expression.getLast(), Utils.NON_OPERATORS))) {
                            expression.add("x");
                        }
                        if (textField.getText().subSequence(i, i + 3).equals("cos")) {
                            expression.add("cos");
                            i += 2;
                        } else {
                            throw new NumberFormatException("Invalid function");
                        }
                        break;

                    // Ln or Log
                    case 'l':
                        if (!expression.isEmpty() && (isNumber(expression.getLast())
                                || Utils.valInArray(expression.getLast(), Utils.NON_OPERATORS))) {
                            expression.add("x");
                        }
                        if (textField.getText().substring(i, i + 2).equals("ln")) {
                            expression.add("ln");
                            i++;
                        } else if (textField.getText().substring(i, i + 3).equals("log")) {
                            expression.add("log");
                            i += 2;
                        } else {
                            throw new NumberFormatException("Invalid function");
                        }
                        break;

                    // Arcsin, arctan, arccos
                    case 'a':
                        if (!expression.isEmpty() && (isNumber(expression.getLast())
                                || Utils.valInArray(expression.getLast(), Utils.NON_OPERATORS))) {
                            expression.add("x");
                        }
                        if (textField.getText().subSequence(i, i + 4).equals("atan")) {
                            expression.add("atan");
                        } else if (textField.getText().subSequence(i, i + 4).equals("asin")) {
                            expression.add("asin");
                        } else if (textField.getText().subSequence(i, i + 4).equals("acos")) {
                            expression.add("acos");
                        } else {
                            throw new NumberFormatException("Invalid function");
                        }
                        i += 3;
                        break;

                    // Add the previous answer
                    case 'A':
                        if (textField.getText().subSequence(i, i + 3).equals("Ans")) {
                            if (!expression.isEmpty() && (isNumber(expression.getLast())
                                    || Utils.valInArray(expression.getLast(), Utils.NON_OPERATORS))) {
                                expression.add("x");
                            }
                            expression.add("Ans");
                        } else {
                            throw new NumberFormatException("Invalid function");
                        }
                        i += 2;
                        break;

                    case '(':
                        if (!expression.isEmpty() && (isNumber(expression.getLast())
                                || Utils.valInArray(expression.getLast(), Utils.NON_OPERATORS))) {
                            expression.add("x");
                        }
                        expression.add("(");
                        break;

                    case 'π':
                        if (!expression.isEmpty()) {
                            if (isNumber(expression.getLast())
                                    || Utils.valInArray(expression.getLast(), Utils.NON_OPERATORS)) {
                                expression.add("x");
                                expression.add("π");
                            } else if (expression.getLast().equals("-") &&
                                    (expression.size() > 1 && !isNumber(expression.get(expression.size() - 2))
                                            && !Utils.valInArray(expression.get(expression.size() - 2),
                                                    Utils.NON_OPERATORS)
                                            || expression.size() == 1)) {
                                expression.add(expression.removeLast() + "π");
                            } else {
                                expression.add("π");
                            }
                        } else {
                            expression.add("π");
                        }
                        break;

                    case 'e':
                        if (!expression.isEmpty()) {
                            if (isNumber(expression.getLast())
                                    || Utils.valInArray(expression.getLast(), Utils.NON_OPERATORS)) {
                                expression.add("x");
                                expression.add("e");
                            } else if (expression.getLast().equals("-") &&
                                    (expression.size() > 1 && !isNumber(expression.get(expression.size() - 2))
                                            && !Utils.valInArray(expression.get(expression.size() - 2),
                                                    Utils.NON_OPERATORS)
                                            || expression.size() == 1)) {
                                expression.add(expression.removeLast() + "e");
                            } else {
                                expression.add("e");
                            }
                        } else {
                            expression.add("e");
                        }
                        break;

                    case '.':
                        if (!expression.isEmpty() && isNumber(expression.getLast())) {
                            expression.add(expression.removeLast() + ".");
                        } else {
                            expression.add(".");
                        }
                        break;

                    default:
                        expression.add(Character.toString(c));
                        break;
                }
            }

            i++;
        }

        return expression;
    }

    /**
     * Coverts infix expression to postfix.
     * 
     * @param infix
     * @return Postfix expression.
     */
    public static String[] infixToPostfix(String infix[]) {
        Stack<String> stack = new Stack<String>();
        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < infix.length; i++) {
            if (isNumber(infix[i])) { // Check if the element is a number
                result.add(infix[i]);
            } else if (infix[i].equals("(")) {
                stack.push(infix[i]);
            } else if (infix[i].equals(")")) { // If there is a closing brakcet, pop the stack until an opening bracket
                                               // is found
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    result.add(stack.pop());
                }
                stack.pop();
            } else if (Utils.valInArray(infix[i], Utils.NON_OPERATORS)) { // Check if the value is Pi or e
                if (infix[i].equals("π")) {
                    result.add(Double.toString(Math.PI));
                } else if (infix[i].equals("-π")) {
                    result.add(Double.toString(-Math.PI));
                } else if (infix[i].equals("e")) {
                    result.add(Double.toString(Math.E));
                } else if (infix[i].equals("-e")) {
                    result.add(Double.toString(-Math.E));
                } else if (infix[i].equals("Ans")) {
                    result.add(Double.toString(ans.doubleValue()));
                }
            } else {
                while (!stack.isEmpty() && prec(infix[i]) < prec(stack.peek())) {
                    result.add(stack.pop());
                }
                stack.push(infix[i]);
            }
        }

        while (!stack.isEmpty()) {
            if (!stack.peek().equals("(")) { // Ignore opening brackets when popping the stack
                result.add(stack.pop());
            } else {
                stack.pop();
            }
        }

        String result_2[] = new String[result.size()];
        return result.toArray(result_2);
    }

    /**
     * Evaluates the value of the postfix expression.
     * 
     * @param postfix
     * @return
     */
    public static BigDecimal postfixParse(String postfix[]) throws Exception {
        Stack<BigDecimal> stack = new Stack<BigDecimal>();

        for (int i = 0; i < postfix.length; i++) {
            if (isNumber(postfix[i])) {
                stack.push(new BigDecimal(postfix[i]));
            } else if (Utils.valInArray(postfix[i], Utils.FUNCTIONS) || postfix[i].equals("!")) {
                BigDecimal num = stack.pop();
                double value;

                switch (postfix[i]) {
                    case "sin":
                        if (!radians) {
                            num = new BigDecimal(Math.toRadians(num.doubleValue()));
                        }
                        value = Math.sin(num.doubleValue());
                        if (Double.isNaN(value)) {
                            throw new Exception("Math error");
                        }
                        stack.push(new BigDecimal(value));
                        break;

                    case "cos":
                        if (!radians) {
                            num = new BigDecimal(Math.toRadians(num.doubleValue()));
                        }
                        value = Math.cos(num.doubleValue());
                        if (Double.isNaN(value)) {
                            throw new Exception("Math error");
                        }
                        stack.push(new BigDecimal(value));
                        break;

                    case "tan":
                        if (!radians) {
                            num = new BigDecimal(Math.toRadians(num.doubleValue()));
                        }
                        value = Math.tan(num.doubleValue());
                        if (Double.isNaN(value)) {
                            throw new Exception("Math error");
                        }
                        stack.push(new BigDecimal(value));
                        break;

                    case "asin":
                        value = Math.asin(num.doubleValue());
                        if (!radians) {
                            value = Math.toDegrees(value);
                            if (Double.isNaN(value)) {
                                throw new Exception("Math error");
                            }
                        }
                        stack.push(new BigDecimal(value));
                        break;

                    case "acos":
                        value = Math.acos(num.doubleValue());
                        if (!radians) {
                            value = Math.toDegrees(value);
                            if (Double.isNaN(value)) {
                                throw new Exception("Math error");
                            }
                        }
                        stack.push(new BigDecimal(value));
                        break;

                    case "atan":
                        value = Math.atan(num.doubleValue());
                        if (!radians) {
                            value = Math.toDegrees(value);
                            if (Double.isNaN(value)) {
                                throw new Exception("Math error");
                            }
                        }
                        stack.push(new BigDecimal(value));
                        break;

                    case "ln":
                        if (num.doubleValue() <= 0) {
                            throw new Exception("Math error");
                        }
                        stack.push(new BigDecimal(Math.log(num.doubleValue())));
                        break;

                    case "log":
                        if (num.doubleValue() <= 0) {
                            throw new Exception("Math error");
                        }
                        stack.push(new BigDecimal(Math.log10(num.doubleValue())));
                        break;

                    case "!":
                        if (!isInt(num.doubleValue()) || num.doubleValue() < 0) {
                            throw new Exception("Math error");
                        }
                        stack.push(new BigDecimal(factorial(BigInteger.valueOf(Math.round(num.doubleValue())))));
                        break;

                    default:
                        break;
                }
            } else {
                BigDecimal num2 = stack.pop();
                BigDecimal num1 = stack.pop();

                switch (postfix[i]) {
                    case "^":
                        if (num2.equals(0) && num1.equals(0)) {
                            throw new Exception("Math error");
                        }
                        stack.push(new BigDecimal(Math.pow(num1.doubleValue(), num2.doubleValue())));
                        break;
                    case "/":
                        if (num2.equals(0)) {
                            throw new Exception("Math error");
                        }
                        stack.push(num1.divide(num2));
                        break;
                    case "%":
                        if (num2.equals(0)) {
                            throw new Exception("Math error");
                        }
                        stack.push(new BigDecimal(num1.doubleValue() % num2.doubleValue()));
                        break;
                    case "x":
                        stack.push(num1.multiply(num2));
                        break;
                    case "+":
                        stack.push(num1.add(num2));
                        break;
                    case "-":
                        stack.push(num1.subtract(num2));
                        break;
                    case "√":
                        if (num1.equals(0)) {
                            throw new Exception("Math error");
                        }
                        stack.push(new BigDecimal(Math.pow(num2.doubleValue(), 1 / num1.doubleValue())));
                        break;
                    default:
                        break;
                }
            }
        }
        return stack.pop();
    }

    private static boolean isInt(double num) {
        return num == Math.round(num);
    }

    public static BigInteger factorial(BigInteger num) {
        if (num.equals(BigInteger.valueOf(0))) {
            return BigInteger.valueOf(1);
        }
        return num.multiply(factorial(num.subtract(BigInteger.valueOf(1))));
    }
}
