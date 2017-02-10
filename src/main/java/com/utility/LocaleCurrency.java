package com.utility;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by rpiotrowicz on 2017-02-06.
 */
public class LocaleCurrency {

    public String getLocaleCurrencyCode(Locale locale) {;
        return Currency.getInstance(locale).getCurrencyCode();
    }
}
