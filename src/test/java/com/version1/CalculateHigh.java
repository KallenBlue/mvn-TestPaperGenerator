package com.version1;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculateHigh {
    @Test
    public void Pri(){
        String question = "1.11+2.22+3.33";
        while (true){
            int index = 0;
            StringBuilder operand1 = new StringBuilder();
            while (index<question.length()&&(question.charAt(index) >= '0' && question.charAt(index) <= '9'||question.charAt(index)=='.')) {
                operand1.append(question.charAt(index++));
            }
            if (index>=question.length()){
                break;
            }
            String symbol = String.valueOf(question.charAt(index++));
            StringBuilder operand2 = new StringBuilder();
            while (index<question.length()&&(question.charAt(index) >= '0' && question.charAt(index) <= '9'||question.charAt(index)=='.')) {
                operand2.append(question.charAt(index++));
            }
            double result = 0;
            switch (symbol){
                case "+":
                    result = Double.parseDouble(String.valueOf(operand1)) + Double.parseDouble(String.valueOf(operand2));
                    break;
                case "-":
                    result = Double.parseDouble(String.valueOf(operand1)) - Double.parseDouble(String.valueOf(operand2));
                    break;
                case "*":
                    result = Double.parseDouble(String.valueOf(operand1)) * Double.parseDouble(String.valueOf(operand2));
                    break;
                case "/":
                    result = Double.parseDouble(String.valueOf(operand1)) / Double.parseDouble(String.valueOf(operand2));
                    break;
            }
            BigDecimal b = new BigDecimal(result);
            result = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
            question = result + question.substring(index);
        }
        System.out.println(question);
    }
    @Test
    public void JH(){
        String question = "11²+87*√73";
        while (question.contains("√")) {
            //数字起始位置
            int index = question.indexOf("√") + 1;
            StringBuilder operand = new StringBuilder();
            while (index<question.length()&&question.charAt(index) >= '0' && question.charAt(index) <= '9') {
                operand.append(question.charAt(index));
                index++;
            }
            double result = Math.sqrt(Double.parseDouble(operand.toString()));
            BigDecimal b = new BigDecimal(result);
            result = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
            question = question.substring(0, question.indexOf("√")) + result + question.substring(index);
        }
        while (question.contains("²")) {
            //数字起始位置
            int index = question.indexOf("²") - 1;
            StringBuilder operand = new StringBuilder();
            while (index >= 0 && question.charAt(index) >= '0' && question.charAt(index) <= '9') {
                operand.insert(0, question.charAt(index));
                index--;
            }
            double result = Math.pow(Double.parseDouble(operand.toString()),2);
            BigDecimal b = new BigDecimal(result);
            result = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
            question = question.substring(0, index+1) + result + question.substring(question.indexOf("²")+1);
        }
        System.out.println(question);
    }
    @Test
    public void calculateHigh(){
        String question = "5+sin30*5+cos60-2+tan0*5";
        while (question.contains("sin")) {
            //数字起始位置
            int index = question.indexOf("sin") + 3;
            StringBuilder operand = new StringBuilder();
            while (question.charAt(index) >= '0' && question.charAt(index) <= '9') {
                operand.append(question.charAt(index));
                index++;
            }
            double result = Math.sin(Double.parseDouble(operand.toString()));
            BigDecimal b = new BigDecimal(result);
            result = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
            question = question.substring(0, question.indexOf("sin")) + result + question.substring(index);
        }
        while (question.contains("cos")) {
            //数字起始位置
            int index = question.indexOf("cos") + 3;
            StringBuilder operand = new StringBuilder();
            while (question.charAt(index) >= '0' && question.charAt(index) <= '9') {
                operand.append(question.charAt(index));
                index++;
            }
            double result = Math.cos(Double.parseDouble(operand.toString()));
            BigDecimal b = new BigDecimal(result);
            result = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
            question = question.substring(0, question.indexOf("cos")) + result + question.substring(index);
        }
        while (question.contains("tan")) {
            //数字起始位置
            int index = question.indexOf("tan") + 3;
            StringBuilder operand = new StringBuilder();
            while (question.charAt(index) >= '0' && question.charAt(index) <= '9') {
                operand.append(question.charAt(index));
                index++;
            }
            double result = Math.tan(Double.parseDouble(operand.toString()));
            BigDecimal b = new BigDecimal(result);
            result = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
            question = question.substring(0, question.indexOf("tan")) + result + question.substring(index);
        }
        System.out.println(question);
    }
}
