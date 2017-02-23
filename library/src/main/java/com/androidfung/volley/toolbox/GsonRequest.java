package com.androidfung.volley.toolbox;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;
import com.androidfung.gson.DateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.Map;


/**
 * @param <T> Gson data model
 * @author funglam
 */
public abstract class GsonRequest<T> extends JsonRequest<T> {

    protected static final String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/x-www-form-urlencoded");


    public static final String TAG = GsonRequest.class.getSimpleName();

    protected Gson getGson() {
        return gson;
    }

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateSerializer())
            .create();


    /**
     * Make a request and return a parsed object
     *
     * @param url      URL of the request to make
     * @param formData map contains the parameters
     */
    public GsonRequest(int method, @NonNull String url, @Nullable Map<String, String> formData,
                       @NonNull Response.Listener<T> listener, @NonNull Response.ErrorListener errorListener) {
        super(method, url, getFormDataString(formData), listener, errorListener);

    }

    /**
     * Make a request and return a parsed object
     *
     * @param url      URL of the request to make
     * @param formData map contains the parameters
     */
    public GsonRequest(int method, @NonNull String url, @Nullable String formData,
                       @NonNull Response.Listener<T> listener, @NonNull Response.ErrorListener errorListener) {


        super(method, url, formData, listener, errorListener);

    }


    @Override
    protected abstract Response<T> parseNetworkResponse(NetworkResponse response);


    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    private static String getFormDataString(Map<String, String> formData){

        StringBuilder params = new StringBuilder();
        if (formData != null) {
            for (String key : formData.keySet()) {
                params.append("&").append(key).append("=").append(formData.get(key));
            }

            return params.toString().substring(1);
        }else {
            return null;
        }
    }

}