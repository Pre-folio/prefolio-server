package prefolio.prefolioserver.domain.constant;

public enum ActTag {
    SOCIETY("society"),
    PROJECT("project"),
    INTERN("intern");

    private String actTag;

    ActTag(String actTag) {
        this.actTag = actTag;
    }

    public String getActTag() {
        return this.actTag;
    }
}
