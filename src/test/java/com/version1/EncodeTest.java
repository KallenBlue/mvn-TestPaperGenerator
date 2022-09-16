package com.version1;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class EncodeTest {
    @Test
    public void encode() throws UnsupportedEncodingException {
        String encode = "%E4%B8%AD%E5%B0%8F%E5%AD%A6%E6%95%B0%E5%AD%A6%E5%8D%B7%E5%AD%90%E8%87%AA%E5%8A%A8%E7%94%9F%E6%88%90%E7%A8%8B%E5%BA%8F";
        //解码
        String decode = URLDecoder.decode(encode,"UTF-8");
        System.out.println(decode);
    }
}
