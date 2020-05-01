package example.micronaut;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.HttpStatus;

@Controller("/bigInteger")
public class BigIntegerController {

    @Get("/")
    public HttpStatus index() {
        return HttpStatus.OK;
    }
}