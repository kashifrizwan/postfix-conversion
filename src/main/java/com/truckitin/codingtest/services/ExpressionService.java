package com.truckitin.codingtest.services;

import com.truckitin.codingtest.enums.Bracket;

import java.util.Deque;

/**
 * Author: kashif Rizwan
 * Date: 14th Sept 2021
 */
public interface ExpressionService {

    /**
     * Resolve incoming arithmetic infix equation and calculate result
     *
     * @param infixEquation incoming arithmetic equation
     * @return resolved result as Integer || error message as String
     */
    Object solveEquation(String infixEquation);

    /**
     * Converts the incoming infix equation to postfix equation
     * DeQue stack is used as it's Asynchronous
     *
     * @param infixEquation incoming arithmetic equation
     * @return converted postfix equation as String
     */
    String convertToPostFix(String infixEquation);

    /**
     * Check incoming operator precedence with stack peek and define it's direction.
     * push to stack
     * or Append to Postfix Equation
     *
     * @param stack            in use for postfix conversion
     * @param incomingOperator to be compared with Stack peek
     * @param postfixEquation  under development postfix equation
     */
    void defineOperatorDirection(Deque<Character> stack, Character incomingOperator,
                                 StringBuilder postfixEquation);

    /**
     * Pop all characters from stack and append to postfix equation
     * until '(' is found in the stack
     *
     * @param stack           in use for postfix conversion
     * @param postfixEquation under development postfix equation
     */
    void popOperatorsFromStack(Deque<Character> stack, StringBuilder postfixEquation);

    /**
     * Check if the character is defined operator
     *
     * @param character is incoming operator from expression string
     * @return true if character is +, -, * or /
     */
    boolean isOperator(Character character);

    /**
     * Check if the character is Opening Bracket, Closing Bracket or Not a Bracket
     *
     * @param character is incoming bracket from expression string
     * @return Bracket Type
     */
    Bracket checkBracketType(Character character);

    /**
     * Compare the precedence of the incoming operators
     *
     * @param character is incoming operator from expression string
     * @return the precedence as int
     */
    int precedenceOf(Character character);
}
