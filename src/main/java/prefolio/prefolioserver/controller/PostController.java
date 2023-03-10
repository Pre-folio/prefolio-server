package prefolio.prefolioserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prefolio.prefolioserver.dto.*;
import prefolio.prefolioserver.service.PostService;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "메인피드 게시물 조회", description = "메인피드 게시물 조회 메서드입니다.")
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
    public String getAllPosts(HttpServletRequest request) {
        return "getAllPosts";
    }

    @Operation(summary = "검색 결과 조회", description = "검색 결과 게시물 조회 메서드입니다.")
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
    public String getSearchPosts(HttpServletRequest request) {
        return "getSearchPosts";
    }

    @Operation(summary = "글 작성", description = "글 작성 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "글 생성 성공",
                    content = @Content(
                            schema = @Schema(implementation = CommonResponseDTO.class)
                    )
            )
    })
    @PostMapping("/")
    @ResponseBody
    public CommonResponseDTO<AddPostDTO.Response> addPost(@RequestBody AddPostDTO.Request addPostRequest) {
        return CommonResponseDTO.onSuccess("글 생성 성공", postService.savePost(addPostRequest));
    }

    @Operation(summary = "게시글 조회", description = "게시글 한 개 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = CommonResponseDTO.class)
                    )
            )
    })
    @GetMapping("/{postId}")
    @ResponseBody
    public CommonResponseDTO<GetPostDTO.Response> getPost(
            @PathVariable(name = "postId") Long postId
    ) {
        return CommonResponseDTO.onSuccess("게시글 조회 성공", postService.findPostById(postId));
    }

    @Operation(summary = "유저 게시글 모두 조회", description = "유저 게시글을 모두 조회하는 메서드입니다.")
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
    public String getUserPosts(HttpServletRequest request) {
        return "getUserPosts";
    }

    @Operation(summary = "스크랩한 게시글 모두 조회", description = "스크랩한 게시글을 모두 조회하는 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = MainPostDTO.class)
                    )
            )
    })
    @GetMapping("/scraps")
    @ResponseBody
    public String getScrapPosts(HttpServletRequest request) {
        return "getScrapPosts";
    }

//    @Operation(summary = "좋아요 버튼 누르기", description = "좋아요 누름/취소 메서드입니다.")
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "SUCCESS",
//                    content = @Content(
//                            schema = @Schema(implementation = ClickLikeDTO.Response.class)
//                    )
//            )
//    })
//    @GetMapping("/likes/{postId}")
//    @ResponseBody
//    public CommonResponseDTO<ClickLikeDTO.Response> clickLike(
//            @PathVariable(name = "postId") Long postId,
//            @RequestParam(name = "isLiked") Boolean isLiked
//    ) {
//        return CommonResponseDTO.onSuccess("SUCCESS", postService.clickLike(postId, isLiked));
//    }
//
//    @Operation(summary = "스크랩 버튼 누르기", description = "스크랩 누름/취소 메서드입니다.")
//    @ApiResponses(value = {
//            @ApiResponse(
//                    responseCode = "200",
//                    description = "조회 성공",
//                    content = @Content(
//                            schema = @Schema(implementation = ClickScrapDTO.Response.class)
//                    )
//            )
//    })
//    @GetMapping("/scraps/{postId}")
//    @ResponseBody
//    public CommonResponseDTO<ClickScrapDTO.Response> clickScrap(
//            @PathVariable(name = "postId") Long postId,
//            @RequestParam(name = "isScrapped") Boolean isScrapped
//    ) {
//        return CommonResponseDTO.onSuccess("SUCCESS", postService.clickScrap(postId, isScrapped));
//    }

}
