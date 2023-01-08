package prefolio.prefolioserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import prefolio.prefolioserver.domain.Post;
import prefolio.prefolioserver.dto.MainPostDTO;
import prefolio.prefolioserver.dto.PostDTO;
import prefolio.prefolioserver.dto.PostIdDTO;
import prefolio.prefolioserver.service.PostService;

@Controller
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
    public String getSearchPosts(HttpServletRequest request) {
        return "getSearchPosts";
    }


    @Operation(summary = "글 작성", description = "글 작성 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "글 생성 성공",
                    content = @Content(
                            schema = @Schema(implementation = PostIdDTO.class)
                    )
            )
    })
    @PostMapping("/")
    public String addPost(HttpServletRequest request) {
        return "addPost";
    }

    @Operation(summary = "게시글 조회", description = "게시글 한 개 조회 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "글 생성 성공",
                    content = @Content(
                            schema = @Schema(implementation = PostDTO.class)
                    )
            )
    })
    @GetMapping("/:post_id")
    public String getPost(HttpServletRequest request) {
        return "getPost";
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
    @GetMapping("/:user_id")
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
    public String getScrapPosts(HttpServletRequest request) {
        return "getScrapPosts";
    }

    @Operation(summary = "좋아요 버튼 누르기", description = "좋아요 누름/취소 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = MainPostDTO.class)
                    )
            )
    })
    @GetMapping("/likes/:post_id")
    public String clickLike(HttpServletRequest request) {
        return "clickLike";
    }

    @Operation(summary = "스크랩 버튼 누르기", description = "스크랩 누름/취소 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            schema = @Schema(implementation = MainPostDTO.class)
                    )
            )
    })
    @GetMapping("/scraps/:post_id")
    public String clickScrap(HttpServletRequest request) {
        return "clickScrap";
    }
}
