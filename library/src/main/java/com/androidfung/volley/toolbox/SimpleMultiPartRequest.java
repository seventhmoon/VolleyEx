package com.androidfung.volley.toolbox;

/*
 * Original from https://github.com/DWorkS/VolleyPlus/blob/bdfee8b0d457a0eec364a5b219d0d0589f242372/library/src/com/android/volley/request/SimpleMultiPartRequest.java
 */
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

/**
 * A Simple request for making a Multi Part request whose response is retrieve as String
 */
@SuppressWarnings("CanBeFinal")
public class SimpleMultiPartRequest extends MultiPartRequest<String> {

	private Listener<String> mListener;

    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Request.Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    @SuppressWarnings("unused")
    public SimpleMultiPartRequest(int method, String url, Listener<String> listener, ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        mListener = listener;
    }

    /**
     * Creates a new GET request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
	@SuppressWarnings("unused")
    public SimpleMultiPartRequest(String url, Listener<String> listener, ErrorListener errorListener) {
		super(Request.Method.POST, url, listener, errorListener);
		mListener = listener;
	}

    @Override
    protected void deliverResponse(String response) {
    	if(null != mListener){
    		mListener.onResponse(response);
    	}
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}