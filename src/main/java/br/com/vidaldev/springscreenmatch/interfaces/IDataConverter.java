package br.com.vidaldev.springscreenmatch.interfaces;

public interface IDataConverter {

    <T> T convertData(String json, Class<T> toClass);
}
