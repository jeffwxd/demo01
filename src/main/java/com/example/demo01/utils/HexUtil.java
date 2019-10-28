package com.example.demo01.utils;


import org.apache.commons.lang.StringUtils;

import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by zengyh on 2016/3/4.
 * 进制转换工具类
 */
public class HexUtil {

    /**
     * 整型转16进制字符
     * @param value
     * @return
     */
    public static String intToHex(int value){
        Byte[] bytes = {
                (byte) (value >> 24 & 0xff),
                (byte) (value >> 16 & 0xff),
                (byte) (value >> 8 & 0xff),
                (byte) (value & 0xff)
        };
        return Stream.of(bytes).map(b->byteToHex(b)).collect(Collectors.joining());
    }

    /**
     * byte数组转字符串
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for(byte b:bytes) {
            sb.append(byteToHex(b));
        }
        return sb.toString();
    }

    /**
     * 16进制字符
     */
    private final static char[] HEX_ARR = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    /**
     * byte转16进制字符
     * @param b
     * @return
     */
    public static String byteToHex(byte b){
        char[] hex = {
                HEX_ARR[b >> 4 & 0x0f],
                HEX_ARR[b & 0x0f]
        };
        return new String(hex);
    }

    /**
     * 16进制字符串转为字节流
     * @param hexStr
     * @return
     */
    public static byte[] hexStrToBytes(String hexStr){
        byte[] bytes = new byte[ (hexStr.length() + 1) / 2 ];
        char[] byteChar = hexStr.toCharArray();
        for(int i = 0; i < byteChar.length; i += 2){
            if(byteChar.length > i + 1) {
                byte high = hexCharToByte(byteChar[i]);
                byte low = hexCharToByte(byteChar[i + 1]);
                bytes[i / 2] = (byte) (high << 4 | low);
            }
        }
        return bytes;
    }

    /**
     * 16进制字符转换为字节流
     * @param byteChar
     * @return
     */
    public static byte hexCharToByte(char byteChar){
        if( byteChar >= 'A' && byteChar <='F' ){
            return (byte) (byteChar - 'A' + 10);
        }else if(byteChar >= 'a' && byteChar <='f'){
           return (byte) (byteChar - 'a' + 10);
        }else if(byteChar >= '0' && byteChar <='9'){
           return (byte) (byteChar - '0');
        }
        return ' ';
    }

    /**
     * 模拟N进制 转换字符 数组是多长就是多少进制
     */
    private final static char[] CHARSET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    /**
     * 转换数字为N进制字符串 可根据需要 去掉0，1，9，O等字符
     * @param number
     * @param length
     * @return
     */
    public static String longTo32Hex(long number, int length){
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder();
        //从低位到高位依次计算数值 并放入栈中
        for(Long rest = number;rest != 0;rest /= CHARSET.length){
            stack.push(CHARSET[(int) (rest % CHARSET.length)]);
        }
        //从栈中依次取出 字符
        while (!stack.isEmpty()){
            result.append(stack.pop());
        }
        //左补位
        return StringUtils.leftPad(result.toString(),length,'X');
    }
}
