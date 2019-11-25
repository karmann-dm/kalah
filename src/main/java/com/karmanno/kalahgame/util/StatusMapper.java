package com.karmanno.kalahgame.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class StatusMapper {
    public static Map<Integer, Integer> stringToMap(String status) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<HashMap<Integer, Integer>> typeRef
                = new TypeReference<>() {
        };
        return objectMapper.readValue(status, typeRef);
    }
}
