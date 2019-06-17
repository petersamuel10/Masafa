package com.vavisa.masafah.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class GsonUtils {

    public static <T> List<T> toList(JsonElement json, Class<T> clazz) {

        if (null == json)
            return null;

        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<T>() {}.getType());
    }
}
