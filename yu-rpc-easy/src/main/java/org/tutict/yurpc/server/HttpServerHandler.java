package org.tutict.yurpc.server;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import org.tutict.yurpc.RpcApplication;
import org.tutict.yurpc.model.RpcRequest;
import org.tutict.yurpc.model.RpcResponse;
import org.tutict.yurpc.registry.LocalRegistry;
import org.tutict.yurpc.serializer.JdkSerializer;
import org.tutict.yurpc.serializer.Serializer;
import org.tutict.yurpc.serializer.SerializerFactory;

import java.lang.reflect.Method;

public class HttpServerHandler implements Handler<HttpServerRequest> {

    @Override
    public void handle(HttpServerRequest request) {

        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        System.out.println("Received request: " + request.method() + " " + request.uri());

        request.bodyHandler(body -> {
            byte[] bytes = body.getBytes();
            RpcRequest rpcRequest = null;
            try {
                rpcRequest = serializer.deserialize(bytes, RpcRequest.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            RpcResponse rpcResponse = new RpcResponse();
            if (rpcRequest == null) {
                rpcResponse.setMessage("rpcRequest is null");
                doResponse(request, rpcResponse, serializer);
                return;
            }

            try {
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                rpcResponse.setData(result);

                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            doResponse(request, rpcResponse, serializer);
        });
    }

    void doResponse(HttpServerRequest request, RpcResponse rpcResponse, Serializer serializer) {
        HttpServerResponse httpServerResponse = request.response()
                .putHeader("Content-Type", "application/json; charset=utf-8");
        try {
            byte[] bytes = serializer.serialize(rpcResponse);
            httpServerResponse.end(Buffer.buffer(bytes));
        } catch (Exception e) {
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }
}
