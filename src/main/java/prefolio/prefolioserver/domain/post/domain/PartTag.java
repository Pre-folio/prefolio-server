package prefolio.prefolioserver.domain.post.domain;

import lombok.Getter;

@Getter
public enum PartTag {
    PLAN("plan"),
    DEV("dev"),
    DESIGN("design");

    private final String partTag;

    PartTag(String partTag) {
        this.partTag = partTag;
    }
}
