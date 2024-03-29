package org.tutict.yurpc.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.tutict.yurpc.model.RpcRequest;
import org.tutict.yurpc.model.RpcResponse;

public class JsonSerializer implements Serializer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public <T> byte[] serialize(T object) throws Exception {
        return OBJECT_MAPPER.writeValueAsBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws Exception {
        T obj = OBJECT_MAPPER.readValue(bytes, clazz);
        if (obj instanceof RpcRequest) {
            return handleRequest((RpcRequest) obj, clazz);
        }
        return obj;
    }

    private <T> T handleRequest(RpcRequest request, Class<T> type) throws Exception {
        Class<?>[] paramTypes = request.getParamTypes();
        Object[] args = request.getArgs();
        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> clazz = paramTypes[i];
            if (!clazz.isAssignableFrom(args[i].getClass())) {
                byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(args[i]);
                args[i] = OBJECT_MAPPER.readValue(bytes, clazz);
            }
        }
        return type.cast(request);
    }

    private <T> T handleResponse(RpcResponse rpcResponse, Class<T> type) throws Exception {
        byte[] dataBytes = OBJECT_MAPPER.writeValueAsBytes(rpcResponse.getData());

        rpcResponse.setData(OBJECT_MAPPER.readValue(dataBytes, rpcResponse.getDataType()));
        return type.cast(rpcResponse);
    }
}
