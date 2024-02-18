package br.com.vidaldev.springscreenmatch.services;

import br.com.vidaldev.springscreenmatch.interfaces.IDataConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

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

    public <T> List<T> convertList(String json, Class<T> toClass){
        CollectionType list = mapper.getTypeFactory()
                .constructCollectionType(List.class, toClass);

        try {
            return mapper.readValue(json, list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
