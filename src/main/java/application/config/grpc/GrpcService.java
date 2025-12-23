package application.config.grpc;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import application.proto.HelloReply;
import application.proto.HelloRequest;
import application.proto.SimpleGrpc;

@AllArgsConstructor
@Service
public class GrpcService {

    private final SimpleGrpc.SimpleBlockingStub stub;

    public String sayHello(String name) {
        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();
        HelloReply reply = stub.sayHello(request);
        return reply.getMessage();
    }

}
