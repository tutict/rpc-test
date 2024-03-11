package org.tutict.yurpc.config;

import lombok.Data;
import org.tutict.yurpc.serializer.SerializerKeys;

@Data
public class RpcConfig {

    private String name = "yu-rpc";
    private String version = "1.0";
    private String serverHost = "localhost";
    private Integer serverPort = 8080;

    private boolean mock = false;
    private String serializer = SerializerKeys.JDK;
}
