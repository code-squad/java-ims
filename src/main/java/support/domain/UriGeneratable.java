package support.domain;

public interface UriGeneratable {

    String generateUri();

    default String generateRedirectUri() {
        return "redirect:" + generateUri();
    }
}
