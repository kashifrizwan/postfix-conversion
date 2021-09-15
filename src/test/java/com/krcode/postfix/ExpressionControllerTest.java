package com.krcode.postfix;

import com.krcode.postfix.controllers.ExpressionController;
import com.krcode.postfix.models.EvaluationResult;
import com.krcode.postfix.models.InputExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Author: kashif Rizwan
 * Date: 14th Sept 2021
 */
@SpringBootTest
public class ExpressionControllerTest {

    private static final String TEST_INFIX_EQUATION_1 = "";
    private static final String RESULT_POSTFIX_EQUATION_1 = "Equation is empty";
    private static final int RESULT_SOLVED_EQUATION_1 = 0;

    private static final String TEST_INFIX_EQUATION_2 = "(4+2)+(3*(5-1))";
    private static final String RESULT_POSTFIX_EQUATION_2 = "42+351-*+";
    private static final int RESULT_SOLVED_EQUATION_2 = 18;

    @Autowired
    private ExpressionController expressionController;

    @Test
    void test_evaluate_expression(){
        InputExpression inputExpression = new InputExpression();
        inputExpression.setEquation(TEST_INFIX_EQUATION_1);
        EvaluationResult result = expressionController.evaluateExpression(inputExpression);
        Assertions.assertEquals(result.getPostFix(), RESULT_POSTFIX_EQUATION_1);
        Assertions.assertEquals(result.getResult(), RESULT_SOLVED_EQUATION_1);

        inputExpression = new InputExpression();
        inputExpression.setEquation(TEST_INFIX_EQUATION_2);
        result = expressionController.evaluateExpression(inputExpression);
        Assertions.assertEquals(result.getPostFix(), RESULT_POSTFIX_EQUATION_2);
        Assertions.assertEquals(result.getResult(), RESULT_SOLVED_EQUATION_2);
    }
}
