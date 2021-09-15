package com.krcode.postfix.models;

/**
 * Author: kashif Rizwan
 * Date: 14th Sept 2021
 * Data Class
 */
public class EvaluationResult {

    private String postFix;
    private Object result;

    public EvaluationResult(String postFix, Object result) {
        this.postFix = postFix;
        this.result = result;
    }

    public String getPostFix() {
        return postFix;
    }

    public void setPostFix(String postFix) {
        this.postFix = postFix;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
