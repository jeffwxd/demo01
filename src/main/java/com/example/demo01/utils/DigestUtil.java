package com.example.demo01.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by zengyh on 2017/10/26.
 */
public final class DigestUtil {

    /**
     * 编码格式
     */
    public enum Charset{
        GB2312("GB2312"),
        GBK("GBK"),
        UFT_8("UTF-8"),
        DEFAULT("ISO-8859-1");
        Charset(String value){
            this.value = value;
        }
        private String value;
    }

    /**
     * 加密类型
     */
    public enum SignType{
        MD5, HMACSHA256, RSA, DES, AES
    }

    /**
     * JDK工具包MD5加密
     * @param data 待加密数据
     * @return 加密结果
     */
    public static String md5(String data,Charset charset) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data.getBytes(charset.value));
            StringBuilder sb = new StringBuilder();
            for(byte item:array){
                sb.append(HexUtil.byteToHex(item));
                //sb.append(Integer.toHexString(item & 0xFF | 0x100).substring(1,3));
            }
            return sb.toString();
        }  catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * JDK工具包生成 hmacsha256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String hmacsha256(String data, String key, Charset charset) {
        try {
            Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(charset.value), "HmacSHA256");
            hmacSHA256.init(secretKey);
            byte[] array = hmacSHA256.doFinal(data.getBytes(charset.value));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(HexUtil.byteToHex(item));
                //sb.append(Integer.toHexString(item & 0xFF | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
