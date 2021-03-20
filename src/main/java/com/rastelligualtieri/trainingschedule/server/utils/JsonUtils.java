package com.rastelligualtieri.trainingschedule.server.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JsonUtils {

    public static String stringToJson(String field, String value){
        StringBuilder sb = new StringBuilder();
        sb.append("{\"");
        sb.append(field);
        sb.append("\":\"");
        sb.append(value);
        sb.append("\"}");
        return sb.toString();
    }

    public static String objectToJson(Object obj) throws JsonProcessingException {
        DefaultPrettyPrinter p = new DefaultPrettyPrinter();
        DefaultPrettyPrinter.Indenter i = new DefaultIndenter("", "");
        p.indentArraysWith(i);
        p.indentObjectsWith(i);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDefaultPrettyPrinter(p);
        String jsonSchedules = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        return jsonSchedules;
    }

}
