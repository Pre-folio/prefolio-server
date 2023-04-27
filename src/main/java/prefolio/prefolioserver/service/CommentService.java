package prefolio.prefolioserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import prefolio.prefolioserver.domain.*;
import prefolio.prefolioserver.domain.constant.SortBy;
import prefolio.prefolioserver.dto.CommentDTO;
import prefolio.prefolioserver.dto.MainPostDTO;
import prefolio.prefolioserver.dto.request.AddCommentRequestDTO;
import prefolio.prefolioserver.dto.response.*;
import prefolio.prefolioserver.error.CustomException;
import prefolio.prefolioserver.query.PostSpecification;
import prefolio.prefolioserver.repository.*;

import java.util.*;

import static prefolio.prefolioserver.error.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;


    public CommentIdResponseDTO saveComment(UserDetailsImpl authUser, AddCommentRequestDTO addCommentRequest) {
        User findUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Comment comment = Comment.builder()
                .user(findUser)
                .post(addCommentRequest.getPost())
                .contents(addCommentRequest.getContents())
                .createdAt(new Date())
                .build();
        Comment savedComment = commentRepository.saveAndFlush(comment);
        return new CommentIdResponseDTO(savedComment);
    }

    public CommentIdResponseDTO updateComment(UserDetailsImpl authUser, Long commentId, AddCommentRequestDTO addCommentRequest) {
        User findUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Comment findComment = commentRepository.findByIdAndUserId(commentId, findUser.getId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        findComment.update(addCommentRequest);
        commentRepository.save(findComment);

        return new CommentIdResponseDTO(findComment);
    }

    public CommentIdResponseDTO deleteComment(UserDetailsImpl authUser, Long commentId) {
        User findUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Comment findComment = commentRepository.findByIdAndUserId(commentId, findUser.getId())
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

        commentRepository.deleteById(findComment.getId());

        return new CommentIdResponseDTO(findComment);
    }

    public CommentResponseDTO getComments(
            UserDetailsImpl authUser, Integer pageNum, Integer limit
    ) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 페이지 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit);

        Page<Comment> findComments = commentRepository.findAll(pageRequest);
        List<CommentDTO> commentsList = new ArrayList<>();
        for (Comment c : findComments) {
            CommentDTO commentDTO = new CommentDTO(c);
            commentsList.add(commentDTO);
        }

        return new CommentResponseDTO(commentsList, findComments.getTotalPages(), findComments.getTotalElements());
    }
}
