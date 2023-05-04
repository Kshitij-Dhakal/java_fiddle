package org.example;

import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.example.grpc.HelloRequest;
import org.example.grpc.HelloServiceGrpc;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        var channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .build();
        var stub = HelloServiceGrpc.newBlockingStub(channel);
        try {
            var response = stub.sayHello(HelloRequest.newBuilder()
                    .build());
            System.out.println(response);
        } catch (StatusRuntimeException e) {
            System.err.println("Status : " + e.getStatus());
        }
    }
}