package com.version2.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class GenerateQuestionsUtil {
    //符号集
    private static final String[] symbols = {"+", "-", "*", "/"};
    private static final String[] highSymbols = {"sin", "cos", "tan"};
    static Random random = new Random(System.currentTimeMillis());

    private static boolean isRepeat(String[] options, int index){
        for (int i = 0; i < index; i++) {
            if (options[index].equals(options[i])){
                return true;
            }
        }
        return false;
    }
    public static String[] getOptions(String question) {
        Random random = new Random(System.currentTimeMillis());
        String[] options = new String[4];
        String rightAnswer = getRightAnswer(question);
        System.out.println("question:"+question+"  rightAnswer:"+rightAnswer);
        options[0] = rightAnswer.equals("NaN") ? "NaN" : rightAnswer;
        for (int i = 1; i < options.length; i++) {
            boolean flag = random.nextBoolean();

            do {
                if (flag){
                    options[i] = String.valueOf(random.nextInt(20) + Double.parseDouble(rightAnswer));
                }
                else {
                    options[i] = String.valueOf(Double.parseDouble(rightAnswer) - random.nextInt(20));
                }
            }while (isRepeat(options,i));

        }
        return options;
    }

    private static String getRightAnswer(String question) {
        question = question.replace("=", "");
        //判断是否有括号
        int leftBracket = question.indexOf("(");
        int rightBracket = question.indexOf(")");
        if (leftBracket < rightBracket) {
            //有括号先算括号内的
            question = question.substring(0, leftBracket) + getRightAnswer(question.substring(leftBracket + 1, rightBracket)) + question.substring(rightBracket + 1);
        }
        //先算sin,cos,tan
        question = calculateHigh(question);
        //再算平方和开根
        question = calculatePH(question);
        if ("NaN".equals(question))
            return question;
        //再算加减乘除
        question = calculatePri(question);
        return question;
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
        if (index >= 0 && question.charAt(index) == '-' && (index == 0 || !isNumber(question, index-1))) {
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

    private static String replaceQuestionWith2Operands(String question, double result, String operand1, String operand2, int symbolIndex,int symbolLength) {
        BigDecimal b = new BigDecimal(result);
        result = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
        int preIndex = symbolIndex - operand1.length();
        int lastIndex = symbolIndex + operand2.length() + symbolLength;
        question = question.substring(0, preIndex) + result + question.substring(lastIndex);
        return question;
    }

    private static String replaceQuestionWithPreOperand(String question, double result, String preOperand, int symbolIndex, int symbolLength) {
        BigDecimal b = new BigDecimal(result);
        result = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
        int preIndex = symbolIndex - preOperand.length();
        int lastIndex = symbolIndex + symbolLength;
        question = question.substring(0, preIndex) + result + question.substring(lastIndex);
        return question;
    }

    private static String replaceQuestionWithLastOperand(String question, double result, String lastOperand, int symbolIndex, int symbolLength) {
        BigDecimal b = new BigDecimal(result);
        result = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
        int preIndex = symbolIndex;
        int lastIndex = symbolIndex + lastOperand.length() +symbolLength;
        question = question.substring(0, preIndex) + result + question.substring(lastIndex);
        return question;
    }

    private static String calculatePri(String question) {
        //先算乘除
        while (question.contains("*")||question.contains("/")) {
            int multiplicationSymbolIndex = question.indexOf("*");
            int divisionSymbolIndex = question.indexOf("/");
            if ((multiplicationSymbolIndex!=-1&&divisionSymbolIndex!=-1&&multiplicationSymbolIndex<divisionSymbolIndex)||divisionSymbolIndex==-1){
                String operand1 = getPreSymbolNum(question, multiplicationSymbolIndex);
                String operand2 = getLastSymbolNum(question, multiplicationSymbolIndex);
                double result = Double.parseDouble(operand1) * Double.parseDouble(operand2);
                question = replaceQuestionWith2Operands(question, result, operand1, operand2, multiplicationSymbolIndex,1);
            }
            else {
                String operand1 = getPreSymbolNum(question, divisionSymbolIndex);
                String operand2 = getLastSymbolNum(question, divisionSymbolIndex);
                double result = Double.parseDouble(operand1) / Double.parseDouble(operand2);
                question = replaceQuestionWith2Operands(question, result, operand1, operand2, divisionSymbolIndex,1);
            }
        }
        //再算加减
        //顺序计算
        while (question.contains("+")||(question.indexOf("-", 1) != -1)) {

            //符号位置
            int plusSymbolIndex = question.indexOf("+");
            int reduceSymbolIndex = question.indexOf("-", 1);
            if ((plusSymbolIndex!=-1&&reduceSymbolIndex!=-1&&plusSymbolIndex<reduceSymbolIndex)||reduceSymbolIndex==-1){
                String operand1 = getPreSymbolNum(question, plusSymbolIndex);
                String operand2 = getLastSymbolNum(question, plusSymbolIndex);
                double result = Double.parseDouble(operand1) + Double.parseDouble(operand2);
                question = replaceQuestionWith2Operands(question, result, operand1, operand2, plusSymbolIndex,1);
            }
            else {
                String operand1 = getPreSymbolNum(question, reduceSymbolIndex);
                //如果只有一个负数了
                if ("".equals(operand1)) {
                    break;
                }
                String operand2 = getLastSymbolNum(question, reduceSymbolIndex);
                double result = Double.parseDouble(operand1) - Double.parseDouble(operand2);
                question = replaceQuestionWith2Operands(question, result, operand1, operand2, reduceSymbolIndex,1);
            }
        }
        return question;
    }

    private static String calculatePH(String question) {
        while (question.contains("²")) {
            //符号位置
            int symbolIndex = question.indexOf("²");
            String preOperand = getPreSymbolNum(question, symbolIndex);
            double result = Math.pow(Double.parseDouble(preOperand), 2);
            question = replaceQuestionWithPreOperand(question, result, preOperand, symbolIndex,1);
        }
        while (question.contains("√")) {
            //符号位置
            int symbolIndex = question.indexOf("√");
            String lastOperand = getLastSymbolNum(question, symbolIndex);
            double result = Math.sqrt(Double.parseDouble(lastOperand));
            if (Double.isNaN(result)) {
                question = "NaN";
                break;
            }
            question = replaceQuestionWithLastOperand(question, result, lastOperand, symbolIndex,1);
        }
        return question;
    }

    private static String calculateHigh(String question) {
        while (question.contains("sin")) {
            //符号位置
            int symbolIndex = question.indexOf("sin");
            String lastOperand = getLastSymbolNum(question, symbolIndex + 2);
            double result = Math.sin(Double.parseDouble(lastOperand));
            question = replaceQuestionWithLastOperand(question, result, lastOperand, symbolIndex,3);
        }
        while (question.contains("cos")) {
            //符号位置
            int symbolIndex = question.indexOf("cos");
            String lastOperand = getLastSymbolNum(question, symbolIndex + 2);
            double result = Math.cos(Double.parseDouble(lastOperand));
            question = replaceQuestionWithLastOperand(question, result, lastOperand, symbolIndex,3);
        }
        while (question.contains("tan")) {
            //符号位置
            int symbolIndex = question.indexOf("tan");
            String lastOperand = getLastSymbolNum(question, symbolIndex + 2);
            double result = Math.tan(Double.parseDouble(lastOperand));
            question = replaceQuestionWithLastOperand(question, result, lastOperand, symbolIndex,3);
        }
//        System.out.println(question);
        return question;
    }

    /**
     * 打乱选项
     *
     * @param options 选项
     * @return 正确答案的位置
     */
    public static int shuffleOptions(String[] options) {
        int index = random.nextInt(4);
        System.out.println(index);
        String tmp = options[index];
        options[index] = options[0];
        options[0] = tmp;
        return index;
    }

    public static String[] generateQuestions(int num, String type) {
        switch (type) {
            case "小学":
                return generateQuestionsPR(num);
            case "初中":
                return generateQuestionsJH(num);
            case "高中":
                return generateQuestionsH(num);
            default:
                return null;
        }
    }

    private static String[] generateQuestionsH(int number) {
        String[] questions = new String[number];
        StringBuilder question = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());
        for (int index = 0; index < number; index++) {
            //操作数数量，1到5个
            int operandsNumber = random.nextInt(5) + 1;
            //括号起始点
            int bracketStart = random.nextInt(operandsNumber);
            //括号终止点
            int bracketEnd = random.nextInt(operandsNumber);
            //初中符号个数
            int juniorSymbolNum = random.nextInt(operandsNumber) + 1;
            //根号个数
            int RadicalNum = random.nextInt(juniorSymbolNum + 1);
            //平方个数
            int squareNum = juniorSymbolNum - RadicalNum;
            //高中符号个数
            int highSymbolNum = operandsNumber == 1 ? 1 : random.nextInt(operandsNumber) + 1;

            //是否有括号
            boolean isBracketIllegal = bracketStart < bracketEnd;
            //至少有一次高中符号
            boolean highSymbolExist = false;
            int[] operands = new int[operandsNumber];
            for (int i = 0; i < operandsNumber; i++) {

                //是否加高中符号
                boolean addHighSymbol = operandsNumber==1||random.nextBoolean();
                if (addHighSymbol){
                    highSymbolExist = true;
                }
                if (i==operandsNumber-1&&!highSymbolExist){
                    addHighSymbol=true;
                }
                //是否加根号
                boolean addRadical = !addHighSymbol && random.nextBoolean();
                //是否加平方
                boolean addSquare = !addRadical && random.nextBoolean();
                operands[i] = random.nextInt(100) + 1;
                if (isBracketIllegal && i == bracketStart) {
                    //在括号外加根号
                    boolean outsideRadical = random.nextBoolean();
                    //在括号外加高中符号
                    boolean outsideHighSymbol = !outsideRadical && random.nextBoolean();
                    if (outsideRadical && RadicalNum > 0) {
                        question.append("√");
                        RadicalNum--;
                    }
                    if (outsideHighSymbol && highSymbolNum > 0) {
                        question.append(highSymbols[random.nextInt(highSymbols.length)]);
                        highSymbolNum--;
                    }
                    question.append("(");
                }
                //普通根号
                if (addRadical && RadicalNum > 0) {
                    question.append("√");
                    RadicalNum--;
                }
                if (addHighSymbol && highSymbolNum > 0) {
                    question.append(highSymbols[random.nextInt(highSymbols.length)]);
                    highSymbolNum--;
                }
                question.append(operands[i]);
                //普通平方
                if (addSquare && squareNum > 0) {
                    question.append("²");
                    squareNum--;
                }
                if (isBracketIllegal && i == bracketEnd) {
                    boolean outsideSquare = random.nextBoolean();

                    question.append(")");
                    //括号外平方
                    if (outsideSquare && squareNum > 0) {
                        question.append("²");
                        squareNum--;
                    }
                }
                if (i < operandsNumber - 1) {
                    int symbolIndex = random.nextInt(symbols.length);
                    question.append(symbols[symbolIndex]);
                }
                //最后一个操作数后面为=号
                else {
                    question.append("=");
                }
            }
            questions[index] = question.toString();
//            System.out.println("question:"+question);
            question.delete(0, question.length());
        }
        return questions;
    }

    private static String[] generateQuestionsJH(int number) {
        String[] questions = new String[number];
        StringBuilder question = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());
        for (int index = 0; index < number; index++) {
            //操作数数量，1到5个
            int operandsNumber = random.nextInt(5) + 1;
            //括号起始点
            int bracketStart = random.nextInt(operandsNumber);
            //括号终止点
            int bracketEnd = random.nextInt(operandsNumber);
            //初中符号个数
            int juniorSymbolNum = operandsNumber == 1 ? 1 : random.nextInt(operandsNumber) + 1;
            //根号个数
            int RadicalNum = random.nextInt(juniorSymbolNum + 1);
            //平方个数
            int squareNum = juniorSymbolNum - RadicalNum;
            //是否有括号
            boolean isBracketIllegal = bracketStart < bracketEnd;
            //是否出现初中符号
            boolean juniorSymbolExist = false;
            int[] operands = new int[operandsNumber];
            for (int i = 0; i < operandsNumber; i++) {
                //是否加根号
                boolean addRadical = operandsNumber == 1 ? RadicalNum == 1 : random.nextBoolean();
                //是否加平方
                boolean addSquare = operandsNumber == 1 ? !addRadical : !addRadical && random.nextBoolean();
                if (i==operandsNumber-1&&!juniorSymbolExist){
                    RadicalNum++;
                    squareNum++;
                    addRadical = random.nextBoolean();
                    addSquare = !addRadical;
                }
                operands[i] = random.nextInt(100) + 1;
                if (isBracketIllegal && i == bracketStart) {
                    //在括号外加根号
                    boolean outsideRadical = random.nextBoolean();
                    if (outsideRadical && RadicalNum > 0) {
                        question.append("√");
                        juniorSymbolExist=true;
                        RadicalNum--;
                    }
                    question.append("(");
                }
                //普通根号
                if (addRadical && RadicalNum > 0) {
                    question.append("√");
                    juniorSymbolExist=true;

                    RadicalNum--;
                }
                question.append(operands[i]);
                //普通平方
                if (addSquare && squareNum > 0) {
                    question.append("²");
                    juniorSymbolExist=true;

                    squareNum--;
                }
                if (isBracketIllegal && i == bracketEnd) {
                    boolean outsideSquare = random.nextBoolean();

                    question.append(")");
                    //括号外平方
                    if (outsideSquare && squareNum > 0) {
                        question.append("²");
                        juniorSymbolExist=true;

                        squareNum--;
                    }
                }
                if (i < operandsNumber - 1) {
                    int symbolIndex = random.nextInt(symbols.length);
                    question.append(symbols[symbolIndex]);
                }
                //最后一个操作数后面为=号
                else {
                    question.append("=");
                }
            }
            questions[index] = question.toString();
//            System.out.println("question:"+question);
            question.delete(0, question.length());
        }
        return questions;
    }

    private static String[] generateQuestionsPR(int number) {
        String[] questions = new String[number];
        //StringBuilder在操作变换的字符串的时候效率更高
        StringBuilder question = new StringBuilder();
        //以当前时间作为随机种子
        Random random = new Random(System.currentTimeMillis());
        for (int index = 0; index < number; index++) {
            //随机生成操作数数量，最少为两个
            int operandsNumber = random.nextInt(4) + 2;
            //随机生成左括号的位置
            int bracketStart = random.nextInt(operandsNumber);
            //随机生成右括号的位置
            int bracketEnd = random.nextInt(operandsNumber);
            //判断括号位置是否合法
            boolean isBracketIllegal = bracketStart < bracketEnd;
            int[] operands = new int[operandsNumber];
            for (int i = 0; i < operandsNumber; i++) {
                //操作数为1~100
                operands[i] = random.nextInt(100) + 1;
                //括号位置合法，并且当前为左括号位置
                if (isBracketIllegal && i == bracketStart) {
                    question.append("(");
                }
                question.append(operands[i]);
                //操作数合法并且当前为右括号位置
                if (isBracketIllegal && i == bracketEnd) {
                    question.append(")");
                }
                if (i < operandsNumber - 1) {
                    //随机从符号集里抽取一个符号
                    int symbolIndex = random.nextInt(symbols.length);
                    question.append(symbols[symbolIndex]);
                }
                //最后一个操作数后面为=号
                else {
                    question.append("=");
                }
            }
            questions[index] = question.toString();
//            System.out.println("question:"+question);
            //重新设置问题
            question.delete(0, question.length());
        }
        return questions;
    }

    public static void main(String[] args) {
        //√(16/15*76)=, sin1^=, cos35=
        System.out.println(GenerateQuestionsUtil.getRightAnswer("cos35="));
    }
}
