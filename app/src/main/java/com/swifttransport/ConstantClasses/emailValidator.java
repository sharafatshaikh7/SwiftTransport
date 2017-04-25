package com.swifttransport.ConstantClasses;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sunil on 2/27/2017.
 */

public class emailValidator {


    /**
     * validate your email address format. Ex-akhi@mani.com
     */
    //for cheking the email pattern right or not
    public boolean emailValidatorcheck(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        Log.e("matches",String.valueOf(matcher.matches()));
        return matcher.matches();
    }
}
