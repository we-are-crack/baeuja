package we_are_crack.baeuja.hello.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloApiController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseResult<HelloData> hello(@RequestParam String name) {
        HelloData helloData = new HelloData(name);
        return new ResponseResult<>(helloData);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResponseResult<HelloData> helloV2(@RequestBody HelloRequest request) {
        HelloData helloData = new HelloData(request.getName());
        return new ResponseResult<>(helloData);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class HelloRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class ResponseResult<T> {

        private T data;
    }

    @Getter
    static class HelloData {

        private final String name;
        private final String greeting;

        public HelloData(String name) {
            this.name = name;
            this.greeting = "Hello, " + name;
        }
    }
}