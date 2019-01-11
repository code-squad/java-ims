package codesquad;

import codesquad.domain.issue.Issue;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseTest {
    @Test
    public void test(){
        ResponseEntity<Void> response = new ResponseEntity<Void>(HttpStatus.OK);
        System.out.println(response.toString());
    }

    @Test
    public void name() {
        ResponseEntity<Issue> response = new ResponseEntity<>(new Issue(), HttpStatus.OK);
        System.out.println(response.toString());
    }
}
