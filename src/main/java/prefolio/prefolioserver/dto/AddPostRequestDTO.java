package prefolio.prefolioserver.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import prefolio.prefolioserver.domain.ActTag;
import prefolio.prefolioserver.domain.PartTag;
import prefolio.prefolioserver.domain.Post;

import java.sql.Date;

public class AddPostRequestDTO {

    private String thumbnail;

    @NotEmpty
    private String title;

    @NotEmpty
    private String startDate;

    @NotEmpty
    private String endDate;

    private Integer contribution;

    private String tools;

    @NotEmpty
    private PartTag partTag;

    @NotEmpty
    private ActTag actTag;

    @NotEmpty
    private String contents;

    @Builder
    public AddPostRequestDTO(
            String thumbnail,
            String title,
            String startDate,
            String endDate,
            Integer contribution,
            String tools,
            PartTag partTag,
            ActTag actTag,
            String contents
    ) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contribution = contribution;
        this.tools = tools;
        this.partTag = partTag;
        this.actTag = actTag;
        this.contents = contents;
    }

    public Post toEntity() {
        return Post.builder()
                .id(toEntity().getId())
                .thumbnail(thumbnail)
                .title(title)
                .startDate(startDate)
                .endDate(endDate)
                .contribution(contribution)
                .tools(tools)
                .partTag(partTag)
                .actTag(actTag)
                .contents(contents)
                .build();
    }
}
