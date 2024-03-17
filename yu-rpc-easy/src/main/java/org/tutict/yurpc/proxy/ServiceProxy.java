package org.tutict.yurpc.proxy;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpRequest;
import org.tutict.yurpc.RpcApplication;
import org.tutict.yurpc.model.RpcRequest;
import org.tutict.yurpc.model.RpcResponse;
import org.tutict.yurpc.serializer.JdkSerializer;
import org.tutict.yurpc.serializer.Serializer;
import org.tutict.yurpc.serializer.SerializerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());

        RpcRequest request = RpcRequest.builder().serviceName(method.getDeclaringClass().getName()).methodName(method.getName())
                .paramTypes(method.getParameterTypes()).args(args).build();

        try {
            byte[] bodyBytes = serializer.serialize(request);
            //TODO: 发送请求，等待响应
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                //TODO: 反序列化响应
                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
