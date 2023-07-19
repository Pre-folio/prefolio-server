package prefolio.prefolioserver.domain.post.domain;

import lombok.Getter;

@Getter
public enum ActTag {
    SOCIETY("society"),
    PROJECT("project"),
    INTERN("intern");

    private final String actTag;

    ActTag(String actTag) {
        this.actTag = actTag;
    }
}
