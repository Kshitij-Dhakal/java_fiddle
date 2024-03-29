package org.example;

import io.grpc.stub.StreamObserver;
import org.apache.commons.lang3.StringUtils;
import org.example.grpc.HelloRequest;
import org.example.grpc.HelloResponse;
import org.example.grpc.HelloServiceGrpc;

public class HelloController extends HelloServiceGrpc.HelloServiceImplBase {
  @Override
  public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
    try {
      var name = request.getName();
      if (StringUtils.isBlank(name)) {
        throw new ValidationException("Name must not be blank");
      }
      var response = HelloResponse.newBuilder().setGreeting("Hello " + name + "!").build();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
    } catch (Exception e) {
      responseObserver.onError(e);
    }
  }
}
