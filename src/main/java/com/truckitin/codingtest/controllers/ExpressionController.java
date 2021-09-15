package com.truckitin.codingtest.controllers;

import com.truckitin.codingtest.models.EvaluationResult;
import com.truckitin.codingtest.models.InputExpression;
import com.truckitin.codingtest.services.ExpressionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: kashif Rizwan
 * Date: 14th Sept 2021
 */
@RestController
public class ExpressionController {

    private final ExpressionService expressionService;

    public ExpressionController(ExpressionService expressionService) {
        this.expressionService = expressionService;
    }

    /**
     * POST endpoint to capture the incoming arithmetic expression
     *
     * @param expression, contains the incoming equation
     * @return EvaluationResult object with postfix expression and resolved equation result
     */
    @PostMapping("/evaluateExpression")
    public EvaluationResult evaluateExpression(@RequestBody InputExpression expression) {

        String equation = expression.getEquation();
        if (equation.isEmpty()) {
            return new EvaluationResult("Equation is empty", 0);
        }

        return new EvaluationResult(
                expressionService.convertToPostFix(equation),
                expressionService.solveEquation(equation)
        );
    }
}