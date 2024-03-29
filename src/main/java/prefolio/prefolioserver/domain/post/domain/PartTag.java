package prefolio.prefolioserver.domain.post.domain;

public enum PartTag {
    PLAN("plan"),
    DEV("dev"),
    DESIGN("design");

    private String partTag;

    PartTag(String partTag) {
        this.partTag = partTag;
    }

    public String getPartTag() {
        return this.partTag;
    }
}
