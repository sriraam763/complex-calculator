/**
 * Contains functions to convert from infix to postfix as well as parse the postfix equation.
 */

package backend;

import java.util.ArrayList;
import java.util.Stack;

public class Parser {
    private static int prec(String operator) {
        if (operator == "^")
            return 6;
        else if (operator == "/")
            return 5;
        else if (operator == "%")
            return 4;    
        else if (operator == "*")
            return 3;
        else if (operator == "+")
            return 2;
        else if (operator == "-")
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

    public static String[] infixToPostfix(String infix[]) {
        Stack<String> stack = new Stack<String>();
        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < infix.length; i++) {
            if (isNumber(infix[i])) {
                result.add(infix[i]);
            } else if (infix[i] == "(") {
                stack.push(infix[i]);
            } else if (infix[i] == ")") {
                while (!stack.isEmpty() && stack.peek() != "(") {
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

    public static double postfixParse(String postfix[]) {
        Stack<Double> answerStack = new Stack<Double>();

        for (int i = 0; i < postfix.length; i++) {
            if (isNumber(postfix[i])) {
                answerStack.push(Double.parseDouble(postfix[i]));
            }

            else {
                double num2 = answerStack.pop();
                double num1 = answerStack.pop();

                switch (postfix[i]) {
                    case "^":
                        answerStack.push(Math.pow(num1, num2));
                        break;
                    case "/":
                        answerStack.push(num1 / num2);
                        break;
                    case "%":
                        answerStack.push(num1 % num2);
                        break;
                    case "*":
                        answerStack.push(num1 * num2);
                        break;
                    case "+":
                        answerStack.push(num1 + num2);
                        break;
                    case "-":
                        answerStack.push(num1 - num2);
                        break;
                    default:
                        break;
                }
            }
        }
        return answerStack.pop();

    }
}
