package prefolio.prefolioserver.domain.post.domain;

import lombok.Getter;

@Getter
public enum Path {
    PROFILE("profile"),
    IMAGE("image"),
    THUMBNAIL("thumbnail");

    private final String path;

    Path(String path) {
        this.path = path;
    }
}
