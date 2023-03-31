package com.example.asif;

import android.content.Context;

/**
 * A class that defines a JavaScript interface for communication between the WebView and the Android application.
 */
public class WebAppInterface {

    Context mContext;

    /**
     * Constructs a new WebAppInterface with the specified context.
     *
     * @param c the context in which the interface will be used
     */
    WebAppInterface(Context c){
        mContext = c;
    }
}
