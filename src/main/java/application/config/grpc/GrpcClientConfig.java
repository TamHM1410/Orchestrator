package application.config.grpc;

import io. grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation. Bean;
import org.springframework. context.annotation.Configuration;

import application.proto.SimpleGrpc;

@Configuration
public class GrpcClientConfig {

    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder
                .forAddress("localhost", 12051) // ⚠️ Sửa từ 9090 -> 50051
                .usePlaintext()
                .build();
    }

    @Bean
    public SimpleGrpc.SimpleBlockingStub simpleBlockingStub(ManagedChannel channel) {
        return SimpleGrpc.newBlockingStub(channel);
    }

    // Thêm stub cho streaming
    @Bean
    public SimpleGrpc.SimpleStub simpleStub(ManagedChannel channel) {
        return SimpleGrpc.newStub(channel);
    }
}