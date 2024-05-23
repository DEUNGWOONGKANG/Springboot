package com.nuriggum.nuriframe.common.util;

import com.nuriggum.nuriframe.common.message.model.MessageKey;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NuriPassValidationUtil {


    public static HashMap<String, Object> isValidPassword(String password, String loginId) {
		boolean isFlag = true;
		HashMap<String,Object> resultMap = new HashMap<String,Object>();

        int passwordMaxLength = Integer.parseInt(NuriPropertiesUtil.getProperty("password.length.max"));
        int passwordMinLength = Integer.parseInt(NuriPropertiesUtil.getProperty("password.length.min"));
        // Check length
        if (password.length() < passwordMinLength || password.length() > passwordMaxLength) {
        	isFlag = false;
            //Argument 세팅
            String[] args = {String.valueOf(passwordMinLength), String.valueOf(passwordMaxLength)};
            resultMap.put("msg", MessageKey.PASSWORD_LENGTH_ERROR);
            resultMap.put("args", args);
        	resultMap.put("flag", isFlag);
        	resultMap.put("code", 400);
            return resultMap;
        }

        int passwordRepeatCount = Integer.parseInt(NuriPropertiesUtil.getProperty("password.repeat.count"));
        // Check repeated characters
        if (isRepeatedCharacters(password, passwordRepeatCount)) {
        	isFlag = false;
            String[] args = {String.valueOf(passwordRepeatCount)};
        	resultMap.put("msg", MessageKey.REPEAT_COUNT_ERROR);
            resultMap.put("args", args);
        	resultMap.put("flag", isFlag);
            resultMap.put("code", 400);
            return resultMap;
        }
        int passwordSimilarCount = Integer.parseInt(NuriPropertiesUtil.getProperty("password.similar.count"));

        // Check similarity to ID
        if (isSimilarToID(password, loginId, passwordSimilarCount)) {
        	isFlag = false;
            String[] args = {String.valueOf(passwordSimilarCount)};
        	resultMap.put("msg", MessageKey.SIMILAR_COUNT_ERROR);
            resultMap.put("args", args);
        	resultMap.put("flag", isFlag);
            resultMap.put("code", 400);
            return resultMap;
        }

        // Check character types
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*\\d)(?=.*[!@#$^&+*<>]).*$");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
        	isFlag = false;
        	resultMap.put("msg", MessageKey.PASSWORD_PATTERN_ERROR);
        	resultMap.put("flag", isFlag);
            resultMap.put("code", 400);
            return resultMap;
        }
        
        Pattern pattern1 = Pattern.compile("^[a-zA-Z0-9!@#$^&+*<>]+$");
        Matcher matcher1 = pattern1.matcher(password);
        if (!matcher1.matches()) {
        	isFlag = false;
        	resultMap.put("msg", MessageKey.PASSWORD_SPECIAL_CHARACTER_ERROR);
        	resultMap.put("flag", isFlag);
            resultMap.put("code", 400);
        	return resultMap;
        }
        
        resultMap.put("msg", "사용가능");
    	resultMap.put("flag", isFlag);
        resultMap.put("code", 200);

        // Password is valid
        return resultMap;
    }

    private static boolean isRepeatedCharacters(String password, int passwordRepeatCount) {
        Pattern pattern = Pattern.compile("(\\w)\\1{" + (passwordRepeatCount-1) + ",}"
        );
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    private static boolean isSimilarToID(String password, String loginId, int passwordSimilarCount) {
        if (loginId.length() < passwordSimilarCount) {
            return false;
        }

        String idSubstring = loginId.substring(0, Math.min(loginId.length(), passwordSimilarCount));
        return password.contains(idSubstring);
    }
}
