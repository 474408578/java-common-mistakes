package com.xschen.commonmistakes.calc;


import org.junit.jupiter.api.Test;

import java.util.Base64;

/**
 * @author xschen
 */
public class CalculateTest {

    @Test
    public void testMathCeil() {
        String percentage = "25%1";
        double batchPercent = Double.valueOf(percentage.trim().split("%")[0]) / 100;
        System.out.println(batchPercent);
        int size = 10;
        int a = (int) Math.ceil((size * batchPercent));
        System.out.println(a);
        System.out.println((int)Math.ceil(1.0));
    }

    public static void main(String[] args) {
        String accessToken = "2b2baa2f9a9493a74ff5b3f8379e85a6";
        String user = "api_noc";
        String password = "LSAsjBqBVOfh7CU2Il";
        String base64String = getBase64String(user, password);
        String authorization = "Basic " + base64String;
        System.out.println(authorization);
    }

    public static String getBase64String(String userName, String passWord) {
        String inputString = userName + ":" + passWord;
        return Base64.getEncoder().encodeToString(inputString.getBytes());
    }
}
