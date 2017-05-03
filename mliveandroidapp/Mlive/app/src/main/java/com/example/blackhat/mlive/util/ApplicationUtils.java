package com.example.blackhat.mlive.util;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SonyVaio on 3/13/2017.
 */

public final class ApplicationUtils {
    public static String generateJSONFromObject(final Object object) {

        final ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;

        try {

            // objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);


            jsonString = objectMapper.writeValueAsString(object);

        } catch (final JsonGenerationException e) {
            e.printStackTrace();
        } catch (final JsonMappingException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static <T> T generateObjectFromJSON(final String jSONString, final Class<T> clazz) {

        ObjectMapper objectMapper = null;
        T object = null;

        if (!isEmpty(jSONString)) {

            try {

                object = clazz.newInstance();
                objectMapper = new ObjectMapper();
                object = objectMapper.readValue(jSONString, clazz);

            } catch (final JsonParseException e) {
                e.printStackTrace();


            } catch (IllegalAccessException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            } catch (InstantiationException e) {
                e.printStackTrace();


            }
        }
        return object;
    }
    public static <T> List<T> generateObjectFromJSONArray(final String jSONString, final Class<T> clazz) {

        ObjectMapper objectMapper = null;
        List<T> object = null;

        if (!isEmpty(jSONString)) {

            try {

                object = new ArrayList<T>();
                objectMapper = new ObjectMapper();
//                object = objectMapper.readValue(jSONString,
//                        TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
//                                clazz));
                object= objectMapper.readValue(jSONString, new TypeReference<List<T>>(){});
            } catch (final JsonParseException e) {
                e.printStackTrace();
            } catch (final JsonMappingException e) {
                e.printStackTrace();
            } catch (final IOException e) {
                e.printStackTrace();
            } catch (final Exception e1) {
                e1.printStackTrace();
            }
        }
        return object;
    }
    public static boolean isEmpty(final String param) {
        final boolean error = false;
        if (param == null || param.trim().length() <= 0) {
            return true;
        }
        return error;
    }

}
