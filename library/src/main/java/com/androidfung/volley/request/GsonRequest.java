package com.androidfung.volley.request;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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



    public class BooleanSerializer implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {

        @Override
        public JsonElement serialize(Boolean arg0, Type arg1, JsonSerializationContext arg2) {
            return new JsonPrimitive(arg0 ? 1 : 0);
        }

        @Override
        public Boolean deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
            return arg0.getAsInt() == 1 || arg0.getAsString().equals("1");
        }
    }

    public class DateSerializer implements JsonDeserializer<Date> {
        SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy h:mm:ss a", Locale.ENGLISH);

        @Override
        public Date deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {

            try {
                return df.parse(arg0.getAsString());
            } catch (ParseException e) {
                Log.d("Error", e.toString());
                return null;
            }

        }
    }
}