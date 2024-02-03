package org.example.grpc;

import io.grpc.stub.StreamObserver;
import org.example.grpc.protos.GreeterGrpc;
import org.example.grpc.protos.Hello.HelloReply;
import org.example.grpc.protos.Hello.HelloRequest;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        GreeterImpl greeter = new GreeterImpl();
        StreamObserver<HelloReply> responseObserver = new StreamObserver<>() {
            @Override
            public void onNext(HelloReply helloReply) {
                System.out.println("received reply: " + helloReply);
            }

            @Override
            public void onError(Throwable throwable) {
                System.err.println("received error: " + throwable);
            }

            @Override
            public void onCompleted() {
                System.out.println("completed was called");
            }
        };
        greeter.sayHello(HelloRequest.newBuilder().setName("Jon").build(), responseObserver);
    }

    private static class GreeterImpl extends GreeterGrpc.GreeterImplBase {

        @Override
        public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
            // Generate a greeting message for the original method
            HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();

            // Send the reply back to the client.
            responseObserver.onNext(reply);

            // Indicate that no further messages will be sent to the client.
            responseObserver.onCompleted();
        }
    }
}
