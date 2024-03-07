package org.tutict.yurpc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RpcRequest {

  private String serviceName;
  private String methodName;
  private Class<?>[]  paramTypes;
  private Object[] args;

}
