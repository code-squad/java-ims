package support.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.domain.UriGeneratable;

import java.net.URI;

public class RestResponseEntityMaker {

    public static <T extends UriGeneratable> ResponseEntity<T> of(T bodyPayload, HttpStatus status) {
        return new ResponseEntity<>(bodyPayload, makeHeaders(bodyPayload), status);
    }

    public static ResponseEntity<Void> of(HttpStatus status) {
        return new ResponseEntity<>(status);
    }

    private static <T extends UriGeneratable> HttpHeaders makeHeaders(T bodyPayload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(bodyPayload.generateUri()));
        return headers;
    }
}
