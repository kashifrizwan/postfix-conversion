package com.krcode.postfix.services.impl;

import com.krcode.postfix.enums.Bracket;
import com.krcode.postfix.services.ExpressionService;
import org.springframework.stereotype.Service;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: kashif Rizwan
 * Date: 14th Sept 2021
 */
@Service
public class ExpressionServiceImpl implements ExpressionService {

    private static final String JAVASCRIPT_ENGINE = "JavaScript";
    private static final Logger logger = Logger.getAnonymousLogger();

    @Override
    public Object solveEquation(String infixEquation) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName(JAVASCRIPT_ENGINE);
        try {
            return engine.eval(infixEquation);
        } catch (ScriptException exception) {
            logger.log(Level.SEVERE, "Invalid Operand Found in Expression", exception);
            return "Invalid Operand Found";
        }
    }

    @Override
    public String convertToPostFix(String infixEquation) {

        StringBuilder postfixEquation = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < infixEquation.length(); i++) {
            char character = infixEquation.charAt(i);

            if (checkBracketType(character) == Bracket.OpeningBracket) {
                stack.push(character);
            } else if (Character.isDigit(character)) {
                postfixEquation.append(character);
            } else if (isOperator(character)) {
                defineOperatorDirection(stack, character, postfixEquation);
            } else if (checkBracketType(character) == Bracket.ClosingBracket) {
                popOperatorsFromStack(stack, postfixEquation);
            }
        }

        /*
        Append all remaining characters from stack to postfixEquation
         */
        while (!stack.isEmpty()) {
            postfixEquation.append(stack.pop());
        }

        return postfixEquation.toString();
    }

    @Override
    public void defineOperatorDirection(Deque<Character> stack, Character incomingOperator,
                                        StringBuilder postfixEquation) {

        if (stack.isEmpty() || checkBracketType(stack.peek()) == Bracket.OpeningBracket ||
                precedenceOf(incomingOperator) > precedenceOf(stack.peek())) {
            stack.push(incomingOperator);
        } else if (precedenceOf(incomingOperator) < precedenceOf(stack.peek())) {

            /*
            If incoming operator has lower precedence the stack peek
            Pop and append the stack peek to postfix equation
            Compare the incoming operator with stack peek again
             */
            do {
                postfixEquation.append(stack.pop());
                if (stack.peek() == null) {
                    break;
                }
            } while (precedenceOf(incomingOperator) < precedenceOf(stack.peek()));

        } else if (precedenceOf(incomingOperator) == precedenceOf(stack.peek())) {
            postfixEquation.append(stack.pop());
            stack.push(incomingOperator);
        }
    }

    @Override
    public void popOperatorsFromStack(Deque<Character> stack, StringBuilder postfixEquation) {
        Character poppedCharacter = stack.pop();
        while (poppedCharacter != '(') {
            postfixEquation.append(poppedCharacter);
            if (stack.isEmpty()) { break; }
            poppedCharacter = stack.pop();
        }
    }

    @Override
    public boolean isOperator(Character character) {
        return character == '+' || character == '-' || character == '*' || character == '/';
    }

    @Override
    public Bracket checkBracketType(Character character) {
        switch (character) {
            case '(':
                return Bracket.OpeningBracket;
            case ')':
                return Bracket.ClosingBracket;
            default:
                return Bracket.NotABracket;
        }
    }

    @Override
    public int precedenceOf(Character character) {
        switch (character) {
            case '+':
            case '-':
                return 0;
            case '*':
            case '/':
                return 1;
            default:
                return -1;
        }
    }
}
