package br.com.vidaldev.springscreenmatch.services;

import br.com.vidaldev.springscreenmatch.interfaces.IDataConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonDataConverter implements IDataConverter {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T convertData(String json, Class<T> toClass) {
        try {
            return mapper.readValue(json, toClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
