package br.com.vidaldev.springscreenmatch.interfaces;

import java.util.List;

public interface IDataConverter {

    <T> T convertData(String json, Class<T> toClass);
    <T> List<T> convertList(String json, Class<T> toClass);
}
