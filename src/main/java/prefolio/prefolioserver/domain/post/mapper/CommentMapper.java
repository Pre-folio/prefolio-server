package prefolio.prefolioserver.domain.post.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import prefolio.prefolioserver.domain.post.domain.Comment;
import prefolio.prefolioserver.domain.post.dto.CommentDTO;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentMapper {

    public List<CommentDTO> toCommentsDTOList(Page<Comment> comments) {
        List<CommentDTO> commentsDTOList = new ArrayList<>();
        for (Comment c : comments) {
            CommentDTO commentDTO = CommentDTO.from(c);
            commentsDTOList.add(commentDTO);
        }

        return commentsDTOList;
    }
}
