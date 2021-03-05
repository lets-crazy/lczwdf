package com.lcz.wdf.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author linwei
 * @date 2017/5/18
 */
public class StringUtil {

    private static final Pattern CHINESE_NUMBER_PATTERN = Pattern.compile("[零一二三四五六七八九十百千万]+");

    private static final Pattern BLANK_PATTERN = Pattern.compile("[\\s\t\r\n]");

    private static final Pattern ID_PREFIX_PATTERN = Pattern.compile("(0{2})*$");

    private static final Pattern NUMBER_OF_TEXT_PATTERN = Pattern.compile("[^0-9]");

    private static final Pattern ID_NUMBER_OF_TEXT_PATTERN = Pattern.compile("\\d*[\\dxX]");

    private static final Pattern NUMBER_OF_START_PATTERN = Pattern.compile("[0-9]+");

    private static final Pattern NUMBER_OF_ALL_PATTERN = Pattern.compile("[0-9]*");

    private static final Pattern NUMBER_OF_FLOAT_DOUBLE_PATTERN = Pattern.compile("^[-\\+]?\\d*[.]\\d+$");


    private StringUtil() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 处理接受参数中的特殊字符
     *
     * @param str
     * @return
     */
    public static String escape(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.replaceAll("((?=[\\x21-\\x7e]+)[^A-Za-z0-9])", "\\\\$1");
    }

    /**
     * 去掉接受参数中的特殊字符
     *
     * @param str
     * @return
     */
    public static String removeSpecialChar(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.replaceAll("[^\\u4e00-\\u9fa5a-zA-Z0-9]", "");
    }

    /**
     * 去掉接受参数中的冒号、括号
     *
     * @param str
     * @return
     */
    public static String removeBracketsAndColon(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.replaceAll("[:：\\(\\)（）]", "");
    }

    /**
     * 去掉接受参数中的空白字符、换行符
     *
     * @param str
     * @return
     */
    public static String removeBlank(String str) {
        if (isEmpty(str)) {
            return "";
        } else {
            Matcher m = BLANK_PATTERN.matcher(str);
            return m.replaceAll("");
        }
    }

    /**
     * 匹配字符串中最长非全0数字
     *
     * @param text
     * @return
     */
    public static String getIdPrefix(String text) {
        Matcher matcher = ID_PREFIX_PATTERN.matcher(text);
        if (matcher.find()) {
            return text.substring(0, matcher.start());
        }
        return "";
    }

    public static String[] getIdPrefix(String[] text) {
        if (Objects.isNull(text)) {
            return new String[0];
        }
        for (int i = 0; i < text.length; i++) {
            text[i] = getIdPrefix(text[i]);
        }
        return text;
    }

    public static List<String> getIdPrefix(List<String> textList) {
        if (textList == null) {
            return Collections.emptyList();
        }
        return textList.stream().map(StringUtil::getIdPrefix).collect(Collectors.toList());
    }

    /**
     * 提取字符串中的数字
     *
     * @param text
     * @return
     */
    public static String getNumberStrFromText(String text) {
        if (StringUtil.isEmpty(text)) {
            return "";
        }
        Matcher matcher = NUMBER_OF_TEXT_PATTERN.matcher(text);
        return matcher.replaceAll("");
    }

    /**
     * 提取字符串中的身份证号码
     *
     * @param text
     * @return
     */
    public static String getIDNumberFromText(String text) {
        if (StringUtil.isEmpty(text)) {
            return "";
        }
        Matcher matcher = ID_NUMBER_OF_TEXT_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    /**
     * 将stream转成string，各项以逗号分割
     *
     * @param stream
     * @return
     */
    public static String streamToString(Stream<Object> stream) {
        return stream.map(String.class::cast).distinct().reduce((s1, s2) -> s1 + "," + s2).orElse("");
    }

    /**
     * 将字符串中的数字转换成数字类型，支持中文或者阿拉伯数字
     *
     * @param numberStr
     * @return
     */
    public static int getNumberFromText(String numberStr) {
        if (isEmpty(numberStr)) {
            return -1;
        }
        String numberFromText = getNumberStrFromText(numberStr);
        if (isNotEmpty(numberFromText)) {
            return Integer.parseInt(numberFromText);
        } else {
            return convertChineseNumber(numberStr);
        }
    }

    /**
     * 将匹配到的第一个中文数字转换成阿拉伯数字,仅支持一亿以下规范的中文数字
     *
     * @param text
     * @return
     */
    public static int convertChineseNumber(String text) {
        if (isEmpty(text)) {
            return -1;
        }
        //判断是否有中文数字
        Matcher matcher = CHINESE_NUMBER_PATTERN.matcher(text);
        if (matcher.find()) {
            String chineseNumber = matcher.group(0);
            String numberStr = "零一二三四五六七八九";
            String unitStr = "十百千万";
            //处理个位数的情况
            if (chineseNumber.length() == 1) {
                return "十".equals(chineseNumber) ? 10 : numberStr.indexOf(chineseNumber);
            }
            try {
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < chineseNumber.length(); i++) {
                    char currentChar = chineseNumber.charAt(i);
                    int number = numberStr.indexOf(currentChar);
                    //零
                    if (number == 0) {
                        char previousUnit = chineseNumber.charAt(i - 1);
                        String laterStr = chineseNumber.substring(i + 1);
                        char laterUnit = findFirstUnit(laterStr, unitStr);
                        if ('个' == laterUnit) {
                            int zeroCount = unitStr.indexOf(previousUnit);
                            for (; zeroCount > 0; zeroCount--) {
                                result.append("0");
                            }
                        }
                        //后面还有单位的情况
                        else {
                            int zeroCount;
                            //XX零X万
                            if ('万' == laterUnit) {
                                zeroCount = unitStr.indexOf(previousUnit);
                            }
                            //X万零X百、X万零X十、X千零X十
                            else {
                                zeroCount = unitStr.indexOf(previousUnit) - unitStr.indexOf(laterUnit) - 1;
                            }
                            for (; zeroCount > 0; zeroCount--) {
                                result.append("0");
                            }
                        }
                    }
                    //大于零的数字
                    else if (number > 0) {
                        result.append(number);
                    }
                    //处于首尾的单位
                    else if (i == 0) {
                        result.append("1");
                        //如果后面也是单位
                        String laterStr = chineseNumber.substring(i + 1);
                        char laterUnit = findFirstUnit(laterStr, unitStr);
                        if (unitStr.indexOf(laterUnit) > -1) {
                            int zeroCount = unitStr.indexOf(currentChar) + 1;
                            for (; zeroCount > 0; zeroCount--) {
                                result.append("0");
                            }
                        }
                    }
                    //处于末尾的单位
                    else if (i == chineseNumber.length() - 1) {
                        int zeroCount = unitStr.indexOf(currentChar) + 1;
                        for (; zeroCount > 0; zeroCount--) {
                            result.append("0");
                        }
                    }
                }
                return Integer.parseInt(result.toString());
            } catch (Exception e) {
                return -1;
            }
        }
        return -1;
    }

    private static char findFirstUnit(String str, String unitStr) {
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            for (int j = 0; j < unitStr.length(); j++) {
                if (unitStr.charAt(j) == currentChar) {
                    return currentChar;
                }
            }
        }
        return '个';
    }

    /**
     * 合并ID，使list只包含最高级别编号
     *
     * @param ids
     * @return
     */
    public static List<String> mergeId(List<String> ids) {
        //去重，排序，保证同一区域的高级别类型在低级别类型的前面
        List<String> tempIds = ids.stream().distinct().sorted().collect(Collectors.toList());

        List<String> parentIdList = new ArrayList<>();
        for (String id : tempIds) {
            //编号前缀是否匹配，不匹配则加入
            if (parentIdList.stream().noneMatch(parentId -> StringUtil.getIdPrefix(id).startsWith(StringUtil.getIdPrefix(parentId)))) {
                parentIdList.add(id);
            }
        }
        return parentIdList;
    }

    /**
     * 获取最小编辑距离
     *
     * @param str1
     * @param str2
     * @return
     */
    public static int minEditDistance(String str1, String str2) {
        if (str1.length() == 0 || str2.length() == 0) {
            return str1.length() == 0 ? str2.length() : str1.length();
        }

        int[][] arr = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i < str1.length() + 1; i++) {
            arr[i][0] = i;
        }
        for (int j = 0; j < str2.length() + 1; j++) {
            arr[0][j] = j;
        }

        for (int i = 0; i < str1.length(); i++) {
            for (int j = 0; j < str2.length(); j++) {
                if (str1.charAt(i) == str2.charAt(j)) {
                    arr[i + 1][j + 1] = arr[i][j];
                } else {
                    int replace = arr[i][j] + 1;
                    int insertStr1 = arr[i][j + 1] + 1;
                    int insertStr2 = arr[i + 1][j] + 1;
                    arr[i + 1][j + 1] = Math.min(Math.min(replace, insertStr1), insertStr2);
                }
            }
        }
        return arr[str1.length()][str2.length()];
    }

    public static boolean isStartOfNumber(String name) {
        Matcher matcher = NUMBER_OF_START_PATTERN.matcher(name.substring(0, 1));
        return matcher.matches();
    }

    public static List<String> splitForKeyWords(String keywords) {
        if (isEmpty(keywords)) {
            return Collections.emptyList();
        }
        StringBuilder builder = new StringBuilder();
        // 先排除掉 - 后跟着的 ()
        String[] tempList = keywords.split("-");
        for (int i = 0; i < tempList.length; i++) {
            // 从第二个开始，截取第一个()
            if (i > 0) {
                tempList[i] = excludeWords(tempList[i].trim());
            }
            tempList[i] = tempList[i].trim().replaceAll("[?|&()（）]+", "!");
            builder.append(tempList[i]);
        }
        return Arrays.stream(builder.toString().split("[!]+")).filter(StringUtil::isNotEmpty).collect(Collectors.toList());
    }

    private static String excludeWords(String temp) {
        // 记录第一个(
        int flag = 0;
        int index = 0;
        char[] chars = temp.toCharArray();
        // 遇到（则加一
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') {
                flag++;
            } else if (chars[i] == ')') {
                flag--;
            }
            if (flag == 0) {
                index = i;
                break;
            }
        }
        return temp.substring(index);
    }

    /**
     * 去除特定字符串
     *
     * @param textList 字符串文本数组
     * @param specials 特殊字符串
     * @return 处理后的文本
     * @author lin.xuancheng
     * @date 2020.09.07
     */
    public static String removeSpecialChar(List<String> textList, String... specials) {
        return textList.stream().filter(s -> {
            for (String special : specials) {
                if (s.contains(special)) {
                    return false;
                }
            }
            return true;
        }).map(s -> s.substring(0, s.indexOf('/'))).collect(Collectors.joining());
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    /**
     * 判断字符串是否全是数字
     *
     * @param str
     * @return boolean
     * @author li.chengzhen
     * @since 2020/11/25 14:49
     */
    public static boolean isNumeric(String str) {
        return NUMBER_OF_ALL_PATTERN.matcher(str).matches();
    }

    /**
     * 判断字符串是否是float或double
     *
     * @param str
     * @return boolean
     * @author li.chengzhen
     * @since 2021/2/5 14:32
     */
    public static boolean isFloatOrDouble(String str) {
        return NUMBER_OF_FLOAT_DOUBLE_PATTERN.matcher(str).matches();
    }

    /**
     * ELF算法
     */
    public static int ELFHash(String str) {
        int hash = 0;
        int x = 0;

        for (int i = 0; i < str.length(); i++) {
            hash = (hash << 4) + str.charAt(i);
            if ((x = (int) (hash & 0xF0000000L)) != 0) {
                hash ^= (x >> 24);
                hash &= ~x;
            }
        }

        return (hash & 0x7FFFFFFF);
    }

}
