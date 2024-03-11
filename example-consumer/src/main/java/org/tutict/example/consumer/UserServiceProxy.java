package org.tutict.example.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import org.tutict.common.model.User;
import org.tutict.common.service.UserService;
import org.tutict.yurpc.serializer.JdkSerializer;
import org.tutict.yurpc.serializer.Serializer;
import org.tutict.yurpc.model.RpcRequest;
import org.tutict.yurpc.model.RpcResponse;


public class UserServiceProxy implements UserService {

    public User getUser(User user){
        Serializer serializer = new JdkSerializer();

        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName("getUser")
                .paramTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();

        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
            }
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

