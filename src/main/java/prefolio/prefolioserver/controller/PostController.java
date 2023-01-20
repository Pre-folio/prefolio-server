package prefolio.prefolioserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import prefolio.prefolioserver.domain.constant.ActTag;
import prefolio.prefolioserver.domain.constant.PartTag;
import prefolio.prefolioserver.domain.constant.SortBy;
import prefolio.prefolioserver.dto.*;
import prefolio.prefolioserver.dto.request.AddPostRequestDTO;
import prefolio.prefolioserver.dto.response.*;
import prefolio.prefolioserver.service.PostService;
import prefolio.prefolioserver.service.UserDetailsImpl;


@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

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
                            schema = @Schema(implementation = MainPostDTO.class)
                    ))
    })
    @GetMapping("/all")
    @ResponseBody
    public CommonResponseDTO<MainPostResponseDTO> getAllPosts(
            @AuthenticationPrincipal UserDetailsImpl authUser,
            @RequestParam(name = "sortBy") SortBy sortBy,
            @RequestParam(name = "partTag", required = false) PartTag partTag,
            @RequestParam(name = "actTag", required = false) ActTag actTag,
            @RequestParam(name = "pageNum") Integer pageNum,
            @RequestParam(name = "limit") Integer limit
            ) {
        return CommonResponseDTO.onSuccess(
                "메인피드 게시물 조회 성공",
                postService.getAllPosts(authUser, sortBy, partTag, actTag, pageNum, limit)
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
                            schema = @Schema(implementation = MainPostDTO.class)
                    )
            )
    })
    @GetMapping("/search")
    @ResponseBody
    public CommonResponseDTO<MainPostResponseDTO> getSearchPosts(
            @AuthenticationPrincipal UserDetailsImpl authUser,
            @RequestParam(name = "sortBy") SortBy sortBy,
            @RequestParam(name = "partTag", required = false) PartTag partTag,
            @RequestParam(name = "actTag", required = false) ActTag actTag,
            @RequestParam(name = "pageNum") Integer pageNum,
            @RequestParam(name = "limit") Integer limit,
            @RequestParam(name = "searchWord") String searchWord
    ) {
        return CommonResponseDTO.onSuccess(
                "검색 결과 게시물 조회 성공",
                postService.getSearchPosts(authUser, sortBy, partTag, actTag, pageNum, limit, searchWord));
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
                            schema = @Schema(implementation = CommonResponseDTO.class)
                    )
            )
    })
    @PostMapping("/post")
    @ResponseBody
    public CommonResponseDTO<AddPostResponseDTO> addPost(
            @AuthenticationPrincipal UserDetailsImpl authUser,
            @RequestBody AddPostRequestDTO addPostRequest
    ) {
        return CommonResponseDTO.onSuccess("글 생성 성공", postService.savePost(authUser, addPostRequest));
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
            @AuthenticationPrincipal UserDetailsImpl authUser,
            @PathVariable(name = "postId") Long postId
    ) {
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
                            schema = @Schema(implementation = MainPostDTO.class)
                    )
            )
    })
    @GetMapping("/{userId}")
    @ResponseBody
    public CommonResponseDTO<CardPostResponseDTO> findPostByUserId(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(name = "partTag", required = false) PartTag partTag,
            @RequestParam(name = "actTag", required = false) ActTag actTag,
            @RequestParam(name = "pageNum") Integer pageNum,
            @RequestParam(name = "limit") Integer limit) {
        return CommonResponseDTO.onSuccess("SUCCESS", postService.findPostByUserId(userId, partTag, actTag, pageNum, limit));
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
                            schema = @Schema(implementation = GetScrapResponseDTO.class)
                    )
            )
    })
    @GetMapping("/scraps")
    @ResponseBody
    public CommonResponseDTO<CardPostResponseDTO> findScrapByUserId(
            @AuthenticationPrincipal UserDetailsImpl authUser,
            @RequestParam(name = "partTag", required = false) PartTag partTag,
            @RequestParam(name = "actTag", required = false) ActTag actTag,
            @RequestParam(name = "pageNum") Integer pageNum,
            @RequestParam(name = "limit") Integer limit) {
        return CommonResponseDTO.onSuccess("SUCCESS", postService.findScrapByUserId(authUser, partTag, actTag, pageNum, limit));
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
            @AuthenticationPrincipal UserDetailsImpl authUser,
            @PathVariable(name = "postId") Long postId,
            @RequestParam(name = "isLiked") Boolean isLiked
    ) {
        return CommonResponseDTO.onSuccess("SUCCESS", postService.clickLike(authUser, postId, isLiked));
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
            @AuthenticationPrincipal UserDetailsImpl authUser,
            @PathVariable(name = "postId") Long postId,
            @RequestParam(name = "isScrapped") Boolean isScrapped
    ) {
        return CommonResponseDTO.onSuccess("SUCCESS", postService.clickScrap(authUser, postId, isScrapped));
    }
}
