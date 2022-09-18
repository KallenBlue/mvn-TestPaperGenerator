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
    private static boolean isNumber(String question,int index){
        return question.charAt(index) >= '0' && question.charAt(index) <= '9';
    }

    /**
     * 获取符号前的数
     * @param question 题目
     * @param index 符号位置
     * @return 符号前的数
     */
    private static String getPreSymbolNum(String question,int index){
        StringBuilder num = new StringBuilder();
        while (index>0&&(isNumber(question,index-1)||question.charAt(index-1)=='.')){
            num.insert(0,question.charAt(--index));
        }
        //判断是否为符号，并非为减号
        if (question.charAt(index)=='-'&&(index==0||!isNumber(question,index-1))){
            num.insert(0,'-');
        }
        return String.valueOf(num);
    }
    private static String getLastSymbolNum(String question,int index){
        StringBuilder num = new StringBuilder();
        do {
            num.append(question.charAt(++index));
        }while (index+1<question.length()&&(isNumber(question,index+1)||question.charAt(index+1)=='.'));
        return String.valueOf(num);
    }
    @ParameterizedTest
    @ValueSource(strings  = {"-5*6-6*7"})
    public void calculate(String question) {
        //先算乘除
        while (question.contains("*")) {
            //符号位置
            int symbolIndex = question.indexOf("*");
            String operand1 = getPreSymbolNum(question,symbolIndex);
            String operand2 = getLastSymbolNum(question,symbolIndex);
            double result = Double.parseDouble(operand1) * Double.parseDouble(operand2);
            BigDecimal b = new BigDecimal(result);
            result = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
            int preIndex = symbolIndex - operand1.length();
            int lastIndex = symbolIndex + operand2.length();
            question = question.substring(0, preIndex) + result + question.substring(lastIndex+1);
        }
        System.out.println(question);
    }
}
