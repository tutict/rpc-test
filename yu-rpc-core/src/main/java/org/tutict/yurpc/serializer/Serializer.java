package org.tutict.yurpc.serializer;

public interface Serializer {

    <T> byte[] serialize(T object) throws Exception;

    <T> T deserialize(byte[] bytes, Class<T> clazz) throws Exception;
}
