package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.UserTest;
import codesquad.dto.IssueDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.*;
import support.test.AcceptanceTest;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiIssueAcceptanceTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiIssueAcceptanceTest.class);

    @Test
    public void create() {
        String location = createResource_login("/api/issues", createIssueDto()); //javajigi가 질문을 생성후 생성한 질문의 경로를 가져옴
        log.debug("location:{}", location);
        ResponseEntity<IssueDto> dbIssue = template.getForEntity(location, IssueDto.class);  //로그인 안한 사용자가 javajigi가 만든 질문에 접근한다.
        log.debug("dbIssue:{}", dbIssue.getBody());
//        도대체 왜 writer의 정보를 가져오지 못할까 (apiController에서 반환하기직전에는 잘 가져오는데...)
        softly.assertThat(dbIssue.getBody()).isNotNull();
    }

    @Test
    public void crate_no_login() {
        ResponseEntity<Void> response = template.postForEntity("/api/issues", createIssueDto(), Void.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private IssueDto createIssueDto() {
        return new IssueDto("제목입니다.", "내용입니다.");
    }

    private IssueDto updatingIssueDto() {
        return new IssueDto("업데이트 제목", "업데이트 내용");
    }

    @Test
    public void show_by_other_user() {
        String location = createResource_login("/api/issues", createIssueDto());           // javajigi가 질문을 남김
        ResponseEntity<IssueDto> response = basicAuthTemplate(UserTest.SANJIGI).getForEntity(location, IssueDto.class);            // sanjigi가 위질문에 접근한다.
        log.debug("response:{}", response);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void update_success() {
        String location = createResource_login("/api/issues", createIssueDto());    //javajigi가 질문을 남김
        log.debug("location:{}", location);
        ResponseEntity<IssueDto> responseEntity =
                basicAuthTemplate().exchange(location, HttpMethod.PUT, createHttpEntity(updatingIssueDto()), IssueDto.class);
        log.debug("responseEntity:{}", responseEntity);
        // javajigi가 위질문을 수정하려고 한다.
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(responseEntity.getBody().getSubject()).isEqualTo(updatingIssueDto().getSubject());
//        responseEntity안에 있는 제네릭 타입에 있는 객체에서 getter를 통해 데이터를 가져온다. (수시간의 삽질 끝에 드디어 알게되었다....

    }

    @Test
    public void update_by_other_user() {
        String location = createResource_login("/api/issues", createIssueDto());

        ResponseEntity<Issue> responseEntity =
                basicAuthTemplate(findByUserId("sanjigi")).exchange(location, HttpMethod.PUT, createHttpEntity(updatingIssueDto()), Issue.class);
        log.debug("responseEntity:{}", responseEntity);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void update_no_login() {
        String location = createResource_login("/api/issues", createIssueDto());

        ResponseEntity<Issue> responseEntity =
                template.exchange(location, HttpMethod.PUT, createHttpEntity(updatingIssueDto()), Issue.class);
        log.debug("reponseEntity:{}", responseEntity);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void delete_success() {
        String location = createResource_login("/api/issues",createIssueDto());

        ResponseEntity<IssueDto> responseEntity =
            basicAuthTemplate().exchange(location, HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()) ,IssueDto.class);
        log.debug("responseEntity:{}",responseEntity);
        softly.assertThat(responseEntity.getBody().isDeleted()).isTrue();
    }

    @Test
    public void delete_no_login() {
        String location = createResource_login("/api/issues",createIssueDto());

        ResponseEntity<IssueDto> responseEntity =
            template().exchange(location,HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()),IssueDto.class);
        log.debug("responseEntity:{}",responseEntity);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void delete_by_other_user() {
        String location = createResource_login("/api/issues",createIssueDto());

        ResponseEntity<IssueDto> responseEntity =
            basicAuthTemplate(findByUserId("sanjigi")).exchange(location, HttpMethod.DELETE, new HttpEntity<>(new HttpHeaders()),IssueDto.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
