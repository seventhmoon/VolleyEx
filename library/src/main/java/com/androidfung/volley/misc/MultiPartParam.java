package com.androidfung.volley.misc;

/*
 * Original from https://github.com/DWorkS/VolleyPlus/blob/bdfee8b0d457a0eec364a5b219d0d0589f242372/library/src/com/android/volley/misc/MultiPartParam.java
 *
 */

/**
 * A representation of a MultiPart parameter
 *
 */
public final class MultiPartParam {

    public String contentType;
    public String value;

    /**
     * Initialize a multipart request param with the value and content type
     *
     * @param contentType The content type of the param
     * @param value       The value of the param
     */
    public MultiPartParam(String contentType, String value) {
        this.contentType = contentType;
        this.value = value;
    }
}