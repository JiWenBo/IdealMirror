package com.example.dllo.idealmirror.net;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

/**
 * MD5网址加密
 * 对外提供getMD5方法
 * Created by dllo on 16/4/7.
 */
public class MDStr {
    public static String getMD5(String val) throws NoSuchElementException{

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(val.getBytes());
        byte[]m = md5.digest();//加密


        return getString(m);
    }
    private static String getString(byte[]b){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<b.length;i++){
            sb.append(b[i]);
        }
        return sb.toString();
    }
    private String getMD5Str(String str){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i=0;i<byteArray.length;i++){
            if (Integer.toHexString(0xFF&byteArray[i]).length() ==1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            }
                else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        //16为加密,从9到24位
            return md5StrBuff.substring(8,24).toString().toUpperCase();
        }
}

