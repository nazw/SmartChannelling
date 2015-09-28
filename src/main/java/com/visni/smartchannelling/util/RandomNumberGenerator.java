package com.visni.smartchannelling.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component(value = "randomNumberGenerator")
public class RandomNumberGenerator {

    //this method generates a random number with the given length
    public long getRandomnumber(int length) throws Exception {
        String randomStr = "";
        while (randomStr.length() <= length) {
            Random randomGenerator = new Random();

            int randomInt = randomGenerator.nextInt(100);
            String str = Integer.toString(randomInt);
            randomStr = randomStr + str;


        }
        return Long.parseLong(randomStr);
    }
}
