    package application.controller.test;

    import application.config.grpc.GrpcService;
    import lombok.AllArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    @AllArgsConstructor
    public class TestController {
        private  final GrpcService grpcService;
        @GetMapping("/public/helloworld")
        public ResponseEntity<?> publicMethod() {
            Object returnedData=grpcService.sayHello("Hello \n my Love");
            return ResponseEntity.ok(returnedData);
        }

        @GetMapping("/private")
        public String privateMethod() {
            return "private";
        }
    }
