package org.tutict.example.provider;


import org.tutict.common.service.UserService;
import org.tutict.yurpc.RpcApplication;
import org.tutict.yurpc.registry.LocalRegistry;
import org.tutict.yurpc.server.HttpServer;
import org.tutict.yurpc.server.VertxHttpServer;

public class EasyProviderExample {

    public static void main(String[] args) {

        RpcApplication.init();
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
