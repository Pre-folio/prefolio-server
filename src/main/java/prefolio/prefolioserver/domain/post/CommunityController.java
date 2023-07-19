package prefolio.prefolioserver.domain.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import prefolio.prefolioserver.domain.post.domain.SortBy;
import prefolio.prefolioserver.domain.post.dto.CommonResponseDTO;
import prefolio.prefolioserver.domain.post.dto.request.AddCommentRequestDTO;
import prefolio.prefolioserver.domain.post.dto.request.AddPostRequestDTO;
import prefolio.prefolioserver.domain.post.dto.response.*;
import prefolio.prefolioserver.domain.post.service.CommentService;
import prefolio.prefolioserver.domain.post.service.PostService;
import prefolio.prefolioserver.global.config.user.UserDetails;

@Slf4j
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/posts")
@RequiredArgsConstructor
public class CommunityController {

    private final PostService postService;
    private final CommentService commentService;

    @Operation(
            summary = "메인피드 게시물 조회",
            description = "메인피드 게시물 조회 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = MainPostResponseDTO.class)
                    ))
    })
    @GetMapping("/all")
    @ResponseBody
    public CommonResponseDTO<MainPostResponseDTO> getAllPosts(
            @AuthenticationPrincipal UserDetails authUser,
            @RequestParam(name = "sortBy") SortBy sortBy,
            @RequestParam(name = "partTagList", required = false) String partTagList,
            @RequestParam(name = "actTagList", required = false) String actTagList,
            @RequestParam(name = "pageNum") Integer pageNum,
            @RequestParam(name = "limit") Integer limit
    ) {
        log.info("메인피드 게시물 조회");
        return CommonResponseDTO.onSuccess(
                "메인피드 게시물 조회 성공",
                postService.getAllPosts(authUser, sortBy, partTagList, actTagList, pageNum, limit)
        );
    }

    @Operation(
            summary = "검색 결과 조회",
            description = "검색 결과 게시물 조회 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = MainPostResponseDTO.class)
                    )
            )
    })
    @GetMapping("/search")
    @ResponseBody
    public CommonResponseDTO<MainPostResponseDTO> getSearchPosts(
            @AuthenticationPrincipal UserDetails authUser,
            @RequestParam(name = "sortBy") SortBy sortBy,
            @RequestParam(name = "partTagList", required = false) String partTagList,
            @RequestParam(name = "actTagList", required = false) String actTagList,
            @RequestParam(name = "pageNum") Integer pageNum,
            @RequestParam(name = "limit") Integer limit,
            @RequestParam(name = "searchWord") String searchWord
    ) {
        log.info("검색 결과 조회");
        return CommonResponseDTO.onSuccess(
                "검색 결과 게시물 조회 성공",
                postService.getSearchPosts(authUser, sortBy, partTagList, actTagList, pageNum, limit, searchWord));
    }

    @Operation(
            summary = "글 작성",
            description = "글 작성 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "글 생성 성공",
                    content = @Content(
                            schema = @Schema(implementation = PostIdResponseDTO.class)
                    )
            )
    })
    @PostMapping("/post")
    @ResponseBody
    public CommonResponseDTO<PostIdResponseDTO> addPost(
            @AuthenticationPrincipal UserDetails authUser,
            @RequestBody AddPostRequestDTO addPostRequest
    ) {
        log.info("글 작성");
        return CommonResponseDTO.onSuccess("글 생성 성공", postService.savePost(authUser, addPostRequest));
    }

    @Operation(
            summary = "글 수정",
            description = "글 수정 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "글 수정 성공",
                    content = @Content(
                            schema = @Schema(implementation = PostIdResponseDTO.class)
                    )
            )
    })
    @PutMapping("/post/{postId}")
    @ResponseBody
    public CommonResponseDTO<PostIdResponseDTO> updatePost(
            @AuthenticationPrincipal UserDetails authUser,
            @PathVariable Long postId,
            @RequestBody AddPostRequestDTO addPostRequest
    ) {
        log.info("글 수정");
        return CommonResponseDTO.onSuccess("글 수정 성공", postService.updatePost(authUser, postId, addPostRequest));
    }
    @Operation(
            summary = "글 삭제",
            description = "글 삭제 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "삭제 성공",
                    content = @Content(
                            schema = @Schema(implementation = PostIdResponseDTO.class)
                    )
            )
    })
    @DeleteMapping("/post/{postId}")
    @ResponseBody
    public CommonResponseDTO<PostIdResponseDTO> deletePost(
            @AuthenticationPrincipal UserDetails authUser,
            @PathVariable Long postId
    ) {
        log.info("글 삭제");
        return CommonResponseDTO.onSuccess("글 삭제 성공", postService.deletePost(authUser, postId));
    }

    @Operation(
            summary = "게시글 조회",
            description = "게시글 한 개 조회 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = GetPostResponseDTO.class)
                    )
            )
    })
    @GetMapping("/post/{postId}")
    @ResponseBody
    public CommonResponseDTO<GetPostResponseDTO> getPost(
            @AuthenticationPrincipal UserDetails authUser,
            @PathVariable(name = "postId") Long postId
    ) {
        log.info("게시글 조회");
        return CommonResponseDTO.onSuccess("게시글 조회 성공", postService.findPostById(authUser, postId));
    }

    @Operation(
            summary = "유저 게시글 모두 조회",
            description = "유저 게시글을 모두 조회하는 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = CardPostResponseDTO.class)
                    )
            )
    })
    @GetMapping("/{userId}")
    @ResponseBody
    public CommonResponseDTO<CardPostResponseDTO> findPostByUserId(
            @AuthenticationPrincipal UserDetails authUser,
            @PathVariable(name = "userId") Long userId,
            @RequestParam(name = "partTagList", required = false) String partTagList,
            @RequestParam(name = "actTagList", required = false) String actTagList,
            @RequestParam(name = "pageNum") Integer pageNum,
            @RequestParam(name = "limit") Integer limit
    ) {
        log.info("유저 게시글 모두 조회");
        return CommonResponseDTO.onSuccess("SUCCESS", postService.findPostByUserId(authUser, userId, partTagList, actTagList, pageNum, limit));
    }

    @Operation(
            summary = "스크랩한 게시글 모두 조회",
            description = "스크랩한 게시글을 모두 조회하는 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = CardPostResponseDTO.class)
                    )
            )
    })
    @GetMapping("/scraps")
    @ResponseBody
    public CommonResponseDTO<CardPostResponseDTO> findScrapByUserId(
            @AuthenticationPrincipal UserDetails authUser,
            @RequestParam(name = "partTagList", required = false) String partTagList,
            @RequestParam(name = "actTagList", required = false) String actTagList,
            @RequestParam(name = "pageNum") Integer pageNum,
            @RequestParam(name = "limit") Integer limit
    ) {
        log.info("스크랩한 게시글 모두 조회");
        return CommonResponseDTO.onSuccess("SUCCESS", postService.findMyScrap(authUser, partTagList, actTagList, pageNum, limit));
    }

    @Operation(
            summary = "좋아요 버튼 누르기",
            description = "좋아요 누름/취소 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "SUCCESS",
                    content = @Content(
                            schema = @Schema(implementation = ClickLikeResponseDTO.class)
                    )
            )
    })
    @GetMapping("/likes/{postId}")
    @ResponseBody
    public CommonResponseDTO<ClickLikeResponseDTO> clickLike(
            @AuthenticationPrincipal UserDetails authUser,
            @PathVariable(name = "postId") Long postId
    ) {
        log.info("좋아요 버튼 누르기");
        return CommonResponseDTO.onSuccess("SUCCESS", postService.clickLike(authUser, postId));
    }

    @Operation(
            summary = "스크랩 버튼 누르기",
            description = "스크랩 누름/취소 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = ClickScrapResponseDTO.class)
                    )
            )
    })
    @GetMapping("/scraps/{postId}")
    @ResponseBody
    public CommonResponseDTO<ClickScrapResponseDTO> clickScrap(
            @AuthenticationPrincipal UserDetails authUser,
            @PathVariable(name = "postId") Long postId
    ) {
        log.info("스크랩 버튼 누르기");
        return CommonResponseDTO.onSuccess("SUCCESS", postService.clickScrap(authUser, postId));
    }

    @Operation(
            summary = "댓글 조회",
            description = "댓글 조회 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "댓글 조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = CommentResponseDTO.class)
                    ))
    })
    @GetMapping("/comment/{postId}")
    @ResponseBody
    public CommonResponseDTO<CommentResponseDTO> getComments(
            @AuthenticationPrincipal UserDetails authUser,
            @RequestParam(name = "pageNum") Integer pageNum,
            @RequestParam(name = "limit") Integer limit
    ) {
        log.info("댓글 조회");
        return CommonResponseDTO.onSuccess(
                "댓글 조회 성공",
                commentService.getComments(authUser, pageNum, limit)
        );
    }

    @Operation(
            summary = "댓글 작성",
            description = "댓글 작성 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "댓글 생성 성공",
                    content = @Content(
                            schema = @Schema(implementation = CommentIdResponseDTO.class)
                    )
            )
    })
    @PostMapping("/comment")
    @ResponseBody
    public CommonResponseDTO<CommentIdResponseDTO> addPost(
            @AuthenticationPrincipal UserDetails authUser,
            @RequestBody AddCommentRequestDTO addCommentRequest
    ) {
        log.info("댓글 작성");
        return CommonResponseDTO.onSuccess("댓글 생성 성공", commentService.saveComment(authUser, addCommentRequest));
    }

    @Operation(
            summary = "댓글 수정",
            description = "댓글 수정 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "댓글 수정 성공",
                    content = @Content(
                            schema = @Schema(implementation = CommentIdResponseDTO.class)
                    )
            )
    })
    @PutMapping("/comment/{commentId}")
    @ResponseBody
    public CommonResponseDTO<CommentIdResponseDTO> updateComment(
            @AuthenticationPrincipal UserDetails authUser,
            @PathVariable Long commentId,
            @RequestBody AddCommentRequestDTO addCommentRequest
    ) {
        log.info("댓글 수정");
        return CommonResponseDTO.onSuccess("댓글 수정 성공", commentService.updateComment(authUser, commentId, addCommentRequest));
    }
    @Operation(
            summary = "댓글 삭제",
            description = "댓글 삭제 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "삭제 성공",
                    content = @Content(
                            schema = @Schema(implementation = PostIdResponseDTO.class)
                    )
            )
    })
    @DeleteMapping("/comment/{commentId}")
    @ResponseBody
    public CommonResponseDTO<CommentIdResponseDTO> deleteComment(
            @AuthenticationPrincipal UserDetails authUser,
            @PathVariable Long commentId
    ) {
        log.info("댓글 삭제");
        return CommonResponseDTO.onSuccess("댓글 삭제 성공", commentService.deleteComment(authUser, commentId));
    }
}
