package com.project.house.common.utils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;


/**
 * Created by user on 2018-04-19.
 */
public class HashUtils {

    private static final HashFunction FUNCTION = Hashing.md5();

    private static final String SALT = "mooc.com";

    public static String encryPassword(String password){
        HashCode hashCode =	FUNCTION.hashString(password+SALT, Charset.forName("UTF-8"));
        return hashCode.toString();
    }

}
