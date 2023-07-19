package prefolio.prefolioserver.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prefolio.prefolioserver.domain.post.domain.Comment;
import prefolio.prefolioserver.domain.post.dto.request.AddCommentRequestDTO;
import prefolio.prefolioserver.domain.post.dto.response.CommentIdResponseDTO;
import prefolio.prefolioserver.domain.post.dto.response.CommentResponseDTO;
import prefolio.prefolioserver.domain.post.mapper.CommentMapper;
import prefolio.prefolioserver.domain.post.repository.CommentRepository;
import prefolio.prefolioserver.domain.user.domain.User;
import prefolio.prefolioserver.domain.post.dto.CommentDTO;
import prefolio.prefolioserver.domain.user.repository.UserRepository;
import prefolio.prefolioserver.domain.user.service.UserDetailsImpl;
import prefolio.prefolioserver.global.error.CustomException;

import java.util.*;

import static prefolio.prefolioserver.global.error.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;


    @Transactional
    public CommentIdResponseDTO saveComment(UserDetailsImpl authUser, AddCommentRequestDTO addCommentRequest) {
        User findUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 댓글 생성
        Comment comment = Comment.of(findUser, addCommentRequest, new Date());
        Comment savedComment = commentRepository.saveAndFlush(comment);
        return CommentIdResponseDTO.from(savedComment);
    }

    @Transactional
    public CommentIdResponseDTO updateComment(UserDetailsImpl authUser, Long commentId, AddCommentRequestDTO addCommentRequest) {
        User findUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Comment findComment = commentRepository.findByIdAndUserId(commentId, findUser.getId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        findComment.update(addCommentRequest);
        commentRepository.save(findComment);

        return CommentIdResponseDTO.from(findComment);
    }

    @Transactional
    public CommentIdResponseDTO deleteComment(UserDetailsImpl authUser, Long commentId) {
        User findUser = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Comment findComment = commentRepository.findByIdAndUserId(commentId, findUser.getId())
                .orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

        commentRepository.deleteById(findComment.getId());

        return CommentIdResponseDTO.from(findComment);
    }

    @Transactional
    public CommentResponseDTO getComments(
            UserDetailsImpl authUser, Integer pageNum, Integer limit
    ) {
        User user = userRepository.findByEmail(authUser.getUsername())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        // 페이지 요청 정보
        PageRequest pageRequest = PageRequest.of(pageNum, limit);

        Page<Comment> findComments = commentRepository.findAll(pageRequest);
        List<CommentDTO> commentsDTOList = commentMapper.toCommentsDTOList(findComments);

        return CommentResponseDTO.of(commentsDTOList, findComments.getTotalPages(), findComments.getTotalElements());
    }
}
