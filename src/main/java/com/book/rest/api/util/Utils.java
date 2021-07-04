package com.book.rest.api.util;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class Utils {

	public long generateISBN() {
		Random random = new Random();
	    char[] digits = new char[13];
	    digits[0] = (char) (random.nextInt(9) + '1');
	    for (int i = 1; i < 13; i++) {
	        digits[i] = (char) (random.nextInt(10) + '0');
	    }
	    return Long.parseLong(new String(digits));
	}
}
