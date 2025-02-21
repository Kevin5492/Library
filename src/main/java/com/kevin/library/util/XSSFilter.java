package com.kevin.library.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class XSSFilter {
	public static String sanitize(String input) {
        return Jsoup.clean(input, Safelist.basic());
    }
}
