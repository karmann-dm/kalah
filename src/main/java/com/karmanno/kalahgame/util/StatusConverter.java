package com.karmanno.kalahgame.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StatusConverter {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public Map<Integer, Integer> statusToBoard(String status) {
        TypeReference<HashMap<Integer, Integer>> typeReference =
                new TypeReference<HashMap<Integer, Integer>>() {};
        return objectMapper.readValue(status, typeReference);
    }

    @SneakyThrows
    public String boardToStatus(Map<Integer, Integer> board) {
        return objectMapper.writeValueAsString(board);
    }
}
