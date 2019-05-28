package com.yqw.youview.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字转换、截取、正则 等
 * Created by Administrator on 2016/4/8.
 */
public class NumUtils {
    /**
     * String转double数
     *
     * @param num
     * @return
     */
    public static double String2double(String num) {
        double i = 0;
        if (null != num && !"".equals(num) && !"null".equals(num)) {
            i =  Double.valueOf(num);
//            i = Double.parseDouble(num);
        } else {
            i = 0;
        }
        return i;
    }

    /**
     * 获取字符串中的数字
     *
     * @param age
     * @return
     */
    public static String getNum(String age) {
        String num = Pattern.compile("[^0-9]").matcher(age).replaceAll("");
        return num;
    }

    /**
     * 去掉字符串中的数字
     *
     * @param age
     * @return
     */
    public static String delNum(String age) {
        String str = age.replaceAll("\\d+", "");
        return str;
    }

    public static boolean isNumeric(String string){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(string);
        if( !isNum.matches() ){
            return false;
        }
        return true;

    }

    /**
     * 手机号正则表达 1+(3~8)+9个随机数
     */
    public static boolean isMobile(String mobile) {
        // ^(1[3-8])\\d{9}$ ^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$
        if (!ObjectUtils.notEmpty(mobile))
            return false;
        Pattern p = Pattern.compile("^(1[3-8])\\d{9}$"); //

        Matcher m = p.matcher(mobile);

        System.out.println(m.matches() + "---");

        return m.matches();
    }

    /**
     * 密码正则表达 密码不能为纯数字，纯字母，纯字符 应该为此三种类型任意2种或2种以上
     */
    public static boolean isPassword(String password) {
        Pattern p = Pattern
                .compile("(?!^[0-9]+$)(?!^[A-z]+$)(?!^[^A-z0-9]+$)^.{6,16}$"); //

        Matcher m = p.matcher(password);

        System.out.println(m.matches() + "---");

        return m.matches();
    }
//    /**
//     * 比较真实完整的判断身份证号码的工具
//     *
//     * @param IdCard 用户输入的身份证号码
//     * @return [true符合规范, false不符合规范]
//     */
//    public static boolean isRealIDCard(String IdCard) {
//        if (IdCard != null) {
//            int correct = new IdCardUtil(IdCard).isCorrect();
//            if (0 == correct) {// 符合规范
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * String转int数
     *
     * @param num
     * @return
     */
    public static Integer string2Integer(String num) {
        Integer i = 0;
        if (null != num && !"".equals(num) && !"null".equals(num)) {
//            i = Integer.getInteger(num);
            i = Integer.valueOf(num);
        } else {
            i = 0;
        }
        return i;
    }

    public static int string2int(String num) {

        if (null != num && !"".equals(num) && !"null".equals(num)) {
            try {
                Integer it = new Integer(num);
                int i = it.intValue();
                i = Integer.valueOf(num);
                return i;
            }catch (Exception e){
                //传递了非数字的字符串
                return -1;
            }

        } else {
            return 0;
        }
    }

    /**
     * String转int数
     *
     * @param num
     * @return
     */
    public static Long string2long(String num) {
        long i = 0;
        if (null != num && !"".equals(num) && !"null".equals(num)) {
            i = Long.valueOf(num);
        } else {
            i = 0;
        }
        return i;
    }


    /**
     * String->Float
     *
     * @return
     */
    public static float string2float(String s) {
        float f = Float.parseFloat(s);
        return f;
    }

    /**
     * 身份证中间8位隐藏
     * 隐藏出生年月
     * @param idCard 身份证号
     */
    public static String idCardHide(String idCard) {
        String idCardHide = idCard.replaceAll("(\\d{6})\\d{8}(\\w{4})","$1*****$2");
        return idCardHide;
    }

//    /**
//     * Float->String
//     * @return
//     */
//    public static String float2String(float f){
//        String s =
//        return
//    }
}
