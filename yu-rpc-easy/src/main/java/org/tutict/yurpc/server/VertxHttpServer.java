package org.tutict.yurpc.server;

import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer{

    public void doStart(int port) {
        Vertx vertx = Vertx.vertx();

        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        server.requestHandler(request -> {
            System.out.println("Receivedrequest:" + request.method() + " " + request.uri());

            request.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x HTTP server!");
        });

        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("HTTP server is listening on port " + port);
            } else {
                System.err.println("Failed to start HTTP server: " + result.cause());
            }
        });
    }

}

