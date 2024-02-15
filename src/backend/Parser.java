/**
 * Contains functions to convert from infix to postfix as well as parse the postfix equation.
 */

package backend;

import java.util.ArrayList;
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
     * Checks the precedence of the operator
     * 
     * @param operator
     * @return
     */
    private static int prec(String operator) {
        if (operator.equals("^"))
            return 6;
        else if (operator.equals("/"))
            return 5;
        else if (operator.equals("%"))
            return 4;
        else if (operator.equals("x"))
            return 3;
        else if (operator.equals("+"))
            return 2;
        else if (operator.equals("-"))
            return 1;
        else
            return -1;
    }

    /**
     * Determines if a string is a number or not.
     * 
     * @param str
     * @return
     */
    private static boolean isNumber(String str) {
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

    public static void calculate() {
        // clear_output = true;
        // if (textField.getText().isEmpty()) {
        // return;
        // }

        // String infix[] = checkSyntaxErrors(expression);
        // if (infix.length == 0) {
        // textField.setText("Syntax Error!");
        // // return;
        // }

        // for (String s : expression) {
        // System.out.printf("%s ", s);
        // }
        System.out.println(textField.getText());
    }

    /**
     * Checks for syntax errors in an expression list.
     * 
     * @param expression
     * @return The infix format of the expression.
     */
    public static String[] checkSyntaxErrors(ArrayList<String> expression) {
        ArrayList<String> infix = new ArrayList<String>();

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

                    case '(':
                        if (!expression.isEmpty() && (isNumber(expression.getLast())
                                || Utils.valInArray(expression.getLast(), Utils.NON_OPERATORS))) {
                            expression.add("x");
                        }
                        expression.add("(");
                        break;

                    case 'π':
                        if (!expression.isEmpty() && (isNumber(expression.getLast())
                                || Utils.valInArray(expression.getLast(), Utils.NON_OPERATORS))) {
                            expression.add("x");
                        }
                        expression.add("π");
                        break;

                    case 'e':
                        if (!expression.isEmpty() && (isNumber(expression.getLast())
                                || Utils.valInArray(expression.getLast(), Utils.NON_OPERATORS))) {
                            expression.add("x");
                        }
                        expression.add("e");
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
            if (isNumber(infix[i])) {
                result.add(infix[i]);
            } else if (infix[i].equals("(")) {
                stack.push(infix[i]);
            } else if (infix[i].equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    result.add(stack.pop());
                }
                stack.pop();
            } else {
                while (!stack.isEmpty() && prec(infix[i]) < prec(stack.peek())) {
                    result.add(stack.pop());
                }
                stack.push(infix[i]);
            }
        }

        while (!stack.isEmpty()) {
            result.add(stack.pop());
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
    public static double postfixParse(String postfix[]) {
        Stack<Double> stack = new Stack<Double>();

        for (int i = 0; i < postfix.length; i++) {
            if (isNumber(postfix[i])) {
                stack.push(Double.parseDouble(postfix[i]));
            }

            else {
                double num2 = stack.pop();
                double num1 = stack.pop();

                switch (postfix[i]) {
                    case "^":
                        stack.push(Math.pow(num1, num2));
                        break;
                    case "/":
                        stack.push(num1 / num2);
                        break;
                    case "%":
                        stack.push(num1 % num2);
                        break;
                    case "x":
                        stack.push(num1 * num2);
                        break;
                    case "+":
                        stack.push(num1 + num2);
                        break;
                    case "-":
                        stack.push(num1 - num2);
                        break;
                    default:
                        break;
                }
            }
        }
        return stack.pop();

    }
}
