package org.example;

import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.example.grpc.HelloRequest;
import org.example.grpc.HelloServiceGrpc;

@Slf4j
public class GrpcClientMain {
  public static void main(String[] args) {
    var channel = ManagedChannelBuilder.forAddress("localhost", 9000).usePlaintext().build();
    var stub = HelloServiceGrpc.newBlockingStub(channel);
    try {
      var response = stub.sayHello(HelloRequest.newBuilder().setName("John").build());
      log.info("Response : {}", response);
    } catch (StatusRuntimeException e) {
      log.error("Status : {}", e.getStatus());
    }
  }
}
