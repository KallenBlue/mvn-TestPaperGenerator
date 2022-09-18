package com.version1;

import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;

public class EncodeTest {
    @Test
    public void encode() throws UnsupportedEncodingException {
        String encode = "%E4%B8%AD%E5%B0%8F%E5%AD%A6%E6%95%B0%E5%AD%A6%E5%8D%B7%E5%AD%90%E8%87%AA%E5%8A%A8%E7%94%9F%E6%88%90%E7%A8%8B%E5%BA%8F";
        //解码
        String decode = URLDecoder.decode(encode, "UTF-8");
        System.out.println(decode);
    }

    private static boolean isNumber(String question, int index) {
        return question.charAt(index) >= '0' && question.charAt(index) <= '9';
    }

    /**
     * 获取符号前的数
     *
     * @param question 题目
     * @param index    符号位置
     * @return 符号前的数
     */
    private static String getPreSymbolNum(String question, int index) {
        StringBuilder num = new StringBuilder();
        while (index > 0 && (isNumber(question, index - 1) || question.charAt(index - 1) == '.')) {
            num.insert(0, question.charAt(--index));
        }
        //指向数字的前一位
        index--;
        //判断是否为符号，并非为减号
        if (index>=0 && question.charAt(index) == '-' && (index == 0 || !isNumber(question, index))) {
            num.insert(0, '-');
        }
        return String.valueOf(num);
    }

    private static String getLastSymbolNum(String question, int index) {
        StringBuilder num = new StringBuilder();
        do {
            num.append(question.charAt(++index));
        } while (index + 1 < question.length() && (isNumber(question, index + 1) || question.charAt(index + 1) == '.'));
        return String.valueOf(num);
    }

    private static String replaceQuestionWith2Operands(String question, double result, String operand1, String operand2, int symbolIndex) {
        BigDecimal b = new BigDecimal(result);
        result = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
        int preIndex = symbolIndex - operand1.length();
        int lastIndex = symbolIndex + operand2.length();
        question = question.substring(0, preIndex) + result + question.substring(lastIndex + 1);
        return question;
    }

    private static String replaceQuestionWithPreOperand(String question, double result, String preOperand, int symbolIndex) {
        BigDecimal b = new BigDecimal(result);
        result = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
        int preIndex = symbolIndex - preOperand.length();
        int lastIndex = symbolIndex + 1;
        question = question.substring(0, preIndex) + result + question.substring(lastIndex);
        return question;
    }

    private static String replaceQuestionWithLastOperand(String question, double result, String lastOperand, int symbolIndex) {
        BigDecimal b = new BigDecimal(result);
        result = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
        int preIndex = symbolIndex;
        int lastIndex = symbolIndex + lastOperand.length();
        question = question.substring(0, preIndex) + result + question.substring(lastIndex + 1);
        return question;
    }

    @ParameterizedTest
    @ValueSource(strings = {"cos39-√(6-sin76)-91+sin85^"})
    public String calculate(String question) {
        //判断是否有括号
//        int leftBracket = question.indexOf("(");
//        int rightBracket = question.indexOf(")");
//        if (leftBracket < rightBracket) {
//            //有括号先算括号内的
//            question = question.substring(0, leftBracket) + calculate(question.substring(leftBracket + 1, rightBracket)) + question.substring(rightBracket + 1);
//        }
        while (question.contains("sin")) {
            //符号位置
            int symbolIndex = question.indexOf("sin");
            String lastOperand = getLastSymbolNum(question, symbolIndex+2);
            double result = Math.sin(Double.parseDouble(lastOperand));
            question = replaceQuestionWithLastOperand(question, result, lastOperand, symbolIndex);
        }
        while (question.contains("cos")) {
            //符号位置
            int symbolIndex = question.indexOf("cos");
            String lastOperand = getLastSymbolNum(question, symbolIndex+2);
            double result = Math.cos(Double.parseDouble(lastOperand));
            question = replaceQuestionWithLastOperand(question, result, lastOperand, symbolIndex);
        }
        while (question.contains("tan")) {
            //符号位置
            int symbolIndex = question.indexOf("tan");
            String lastOperand = getLastSymbolNum(question, symbolIndex+2);
            double result = Math.tan(Double.parseDouble(lastOperand));
            question = replaceQuestionWithLastOperand(question, result, lastOperand, symbolIndex);
        }
        while (question.contains("^")) {
            //符号位置
            int symbolIndex = question.indexOf("^");
            String preOperand = getPreSymbolNum(question, symbolIndex);
            double result = Math.pow(Double.parseDouble(preOperand), 2);
            question = replaceQuestionWithPreOperand(question, result, preOperand, symbolIndex);
        }
        while (question.contains("√")) {
            //符号位置
            int symbolIndex = question.indexOf("√");
            String lastOperand = getLastSymbolNum(question, symbolIndex);
            double result = Math.sqrt(Double.parseDouble(lastOperand));
            if (Double.isNaN(result)){
                question = "NaN";
                break;
            }
            question = replaceQuestionWithLastOperand(question, result, lastOperand, symbolIndex);
        }
        if (question.equals("NaN")){
            question = "NaN";
            return question;

        }
        //先算乘除
        while (question.contains("*")) {
            //符号位置
            int symbolIndex = question.indexOf("*");
            String operand1 = getPreSymbolNum(question, symbolIndex);
            String operand2 = getLastSymbolNum(question, symbolIndex);
            double result = Double.parseDouble(operand1) * Double.parseDouble(operand2);
            question = replaceQuestionWith2Operands(question, result, operand1, operand2, symbolIndex);
        }
        while (question.contains("/")) {
            //符号位置
            int symbolIndex = question.indexOf("/");
            String operand1 = getPreSymbolNum(question, symbolIndex);
            String operand2 = getLastSymbolNum(question, symbolIndex);
            double result = Double.parseDouble(operand1) / Double.parseDouble(operand2);
            question = replaceQuestionWith2Operands(question, result, operand1, operand2, symbolIndex);
        }
        //再算加减
        while (question.contains("+")) {
            //符号位置
            int symbolIndex = question.indexOf("+");
            String operand1 = getPreSymbolNum(question, symbolIndex);
            String operand2 = getLastSymbolNum(question, symbolIndex);
            double result = Double.parseDouble(operand1) + Double.parseDouble(operand2);
            question = replaceQuestionWith2Operands(question, result, operand1, operand2, symbolIndex);
        }
        while (question.indexOf("-", 1) != -1) {
            //符号位置
            int symbolIndex = question.indexOf("-", 1);
            String operand1 = getPreSymbolNum(question, symbolIndex);
            //如果只有一个负数了
            if ("".equals(operand1)) {
                break;
            }
            String operand2 = getLastSymbolNum(question, symbolIndex);
            double result = Double.parseDouble(operand1) - Double.parseDouble(operand2);
            question = replaceQuestionWith2Operands(question, result, operand1, operand2, symbolIndex);
        }
        System.out.println(question);
        return question;
    }

    @Test
    public void t() {
        System.out.println(Math.sqrt(-1));
    }
}
