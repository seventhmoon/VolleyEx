package com.androidfung.gson;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by funglam on 2/23/17.
 * Serializer for parsing date format
 */

public class DateSerializer implements JsonDeserializer<Date> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("M/d/yyyy h:mm:ss a", Locale.ENGLISH);

    @Override
    public Date deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {

        try {
            return DATE_FORMAT.parse(arg0.getAsString());
        } catch (ParseException e) {
            Log.d("Error", e.toString());
            return null;
        }

    }
}