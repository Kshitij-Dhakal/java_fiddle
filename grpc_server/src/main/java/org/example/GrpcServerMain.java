package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GrpcServerMain {
  public static void main(String[] args) {
    int port = 9000;
    Server server = ServerBuilder.forPort(port).addService(new HelloController()).build();
    try {
      server.start();
      log.info("Server listening on port : {}", port);
      server.awaitTermination();
    } catch (IOException | InterruptedException e) {
      log.error("Failed to start server.", e);
    }
  }
}
