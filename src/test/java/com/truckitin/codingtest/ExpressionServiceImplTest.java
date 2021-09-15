package com.truckitin.codingtest;

import com.truckitin.codingtest.enums.Bracket;
import com.truckitin.codingtest.services.impl.ExpressionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Author: kashif Rizwan
 * Date: 14th Sept 2021
 */
@SpringBootTest
class ExpressionServiceImplTest {

    private static final String TEST_INFIX_EQUATION_2 = "(4+2)+(3*(5-1))";
    private static final String RESULT_POSTFIX_EQUATION_2 = "42+351-*+";
    private static final int RESULT_SOLVED_EQUATION_2 = 18;

    private static final String TEST_INFIX_EQUATION_3 = "((3+4)*2)/7";
    private static final String RESULT_POSTFIX_EQUATION_3 = "34+2*7/";
    private static final int RESULT_SOLVED_EQUATION_3 = 2;

    private static final String TEST_INFIX_EQUATION_4 = "4*(5-(7+2))";
    private static final String RESULT_POSTFIX_EQUATION_4 = "4572+-*";
    private static final int RESULT_SOLVED_EQUATION_4 = -16;

    private static final String TEST_INFIX_EQUATION_5 = "?*(4+(2*(3+(5-1))))";
    private static final String RESULT_POSTFIX_EQUATION_5 = "42351-+*+*";
    private static final String RESULT_SOLVED_EQUATION_5 = "Invalid Operand Found";

    private static final String POPPED_OPERATORS_RESULT_1 = "+-+";
    private static final String POPPED_OPERATORS_RESULT_2 = "*-+";
    private static final String POPPED_OPERATORS_RESULT_3 = "";

    private static final Character[] STACK_DATA_SET_1 = {'+', '-', '+', '(', '+'};
    private static final Character[] STACK_DATA_SET_2 = {'*', '-', '+'};
    private static final Character[] STACK_DATA_SET_3 = {'(', '*', '+', '-', '+'};

    @Autowired
    private ExpressionServiceImpl expressionServiceImpl;

    /**
     * Test incoming arithmetic infix equation and calculate result
     * assert result with expected value
     */
    @Test
    void test_solve_equation(){
        int result = (int) expressionServiceImpl.solveEquation(TEST_INFIX_EQUATION_2);
        assertEquals(result, RESULT_SOLVED_EQUATION_2);

        result = (int) expressionServiceImpl.solveEquation(TEST_INFIX_EQUATION_3);
        assertEquals(result, RESULT_SOLVED_EQUATION_3);

        result = (int) expressionServiceImpl.solveEquation(TEST_INFIX_EQUATION_4);
        assertEquals(result, RESULT_SOLVED_EQUATION_4);

        String errorResult = (String) expressionServiceImpl.solveEquation(TEST_INFIX_EQUATION_5);
        assertEquals(errorResult, RESULT_SOLVED_EQUATION_5);
    }

    /**
     * Test the conversion of incoming infix equation to postfix equation
     * Assert different results for different data sets with expected values
     */
    @Test
    void test_convert_to_post_fix(){
        String result = expressionServiceImpl.convertToPostFix(TEST_INFIX_EQUATION_2);
        assertEquals(result, RESULT_POSTFIX_EQUATION_2);

        result = expressionServiceImpl.convertToPostFix(TEST_INFIX_EQUATION_3);
        assertEquals(result, RESULT_POSTFIX_EQUATION_3);

        result = expressionServiceImpl.convertToPostFix(TEST_INFIX_EQUATION_4);
        assertEquals(result, RESULT_POSTFIX_EQUATION_4);

        String errorResult = expressionServiceImpl.convertToPostFix(TEST_INFIX_EQUATION_5);
        assertEquals(errorResult, RESULT_POSTFIX_EQUATION_5);
    }

    /**
     * Test incoming operator precedence with stack peek and define it's direction.
     */
    @Test
    void test_pop_operators_from_stack(){

        StringBuilder inbuiltPostfixEquation = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>(Arrays.asList(STACK_DATA_SET_1));

        expressionServiceImpl.popOperatorsFromStack(stack, inbuiltPostfixEquation);
        assertEquals(inbuiltPostfixEquation.toString(), POPPED_OPERATORS_RESULT_1);

        inbuiltPostfixEquation = new StringBuilder();
        stack = new ArrayDeque<>(Arrays.asList(STACK_DATA_SET_2));

        expressionServiceImpl.popOperatorsFromStack(stack, inbuiltPostfixEquation);
        assertEquals(inbuiltPostfixEquation.toString(), POPPED_OPERATORS_RESULT_2);

        inbuiltPostfixEquation = new StringBuilder();
        stack = new ArrayDeque<>(Arrays.asList(STACK_DATA_SET_3));

        expressionServiceImpl.popOperatorsFromStack(stack, inbuiltPostfixEquation);
        assertEquals(inbuiltPostfixEquation.toString(), POPPED_OPERATORS_RESULT_3);
    }

    /**
     * Test all possible test cases about popping all characters from stack and append to postfix equation
     * until '(' is found in the stack
     * assert results against expected values
     */
    @Test
    void test_define_operator_direction() {

        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder inbuiltPostfixEquation = new StringBuilder();

        expressionServiceImpl.defineOperatorDirection(stack, '+', inbuiltPostfixEquation);
        assertEquals('+', stack.peek());

        stack = new ArrayDeque<>(Arrays.asList(STACK_DATA_SET_3));
        expressionServiceImpl.defineOperatorDirection(stack, '+', inbuiltPostfixEquation);
        assertEquals('+', stack.peek());

        stack = new ArrayDeque<>(Arrays.asList(STACK_DATA_SET_1));
        expressionServiceImpl.defineOperatorDirection(stack, '*', inbuiltPostfixEquation);
        assertEquals('*', stack.peek());

        stack = new ArrayDeque<>(Arrays.asList(STACK_DATA_SET_2));
        expressionServiceImpl.defineOperatorDirection(stack, '+', inbuiltPostfixEquation);
        assertEquals("*", inbuiltPostfixEquation.toString());

        stack = new ArrayDeque<>(Arrays.asList(STACK_DATA_SET_2));
        inbuiltPostfixEquation = new StringBuilder();
        expressionServiceImpl.defineOperatorDirection(stack, '*', inbuiltPostfixEquation);
        assertEquals("*", inbuiltPostfixEquation.toString());
        assertEquals('*', stack.peek());
    }

    /**
     * Test if the character is defined operator or not
     */
    @Test
    void test_is_operator(){
        boolean result = expressionServiceImpl.isOperator('+');
        assertTrue(result);

        result = expressionServiceImpl.isOperator('-');
        assertTrue(result);

        result = expressionServiceImpl.isOperator('*');
        assertTrue(result);

        result = expressionServiceImpl.isOperator('/');
        assertTrue(result);

        result = expressionServiceImpl.isOperator('^');
        assertFalse(result);

        result = expressionServiceImpl.isOperator('%');
        assertFalse(result);
    }

    /**
     * Test if the character is Opening Bracket, Closing Bracket or Not a Bracket
     */
    @Test
    void test_bracket_type(){
        Bracket bracket = expressionServiceImpl.checkBracketType('(');
        assertEquals(bracket, Bracket.OpeningBracket);

        bracket = expressionServiceImpl.checkBracketType(')');
        assertEquals(bracket, Bracket.ClosingBracket);

        bracket = expressionServiceImpl.checkBracketType('-');
        assertEquals(bracket, Bracket.NotABracket);

        bracket = expressionServiceImpl.checkBracketType('?');
        assertEquals(bracket, Bracket.NotABracket);
    }

    /**
     * Test the Comparison of the precedence of the incoming operators
     */
    @Test
    void test_precedence_of(){
        int result = expressionServiceImpl.precedenceOf('+');
        assertEquals(result, 0);

        result = expressionServiceImpl.precedenceOf('-');
        assertEquals(result, 0);

        result = expressionServiceImpl.precedenceOf('*');
        assertEquals(result, 1);

        result = expressionServiceImpl.precedenceOf('/');
        assertEquals(result, 1);

        result = expressionServiceImpl.precedenceOf('%');
        assertEquals(result, -1);

        result = expressionServiceImpl.precedenceOf('^');
        assertEquals(result, -1);
    }
}
