package com.visni.smartchannelling.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

@Component
public class DateFormatterUtil {

	public String monthFirstformat(String dateString)throws ParseException{
		
		SimpleDateFormat format =new SimpleDateFormat("MM/dd/yyyy");
		format.parse(dateString);

		return dateString;
	}

}
