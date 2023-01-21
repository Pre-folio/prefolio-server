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
import prefolio.prefolioserver.domain.constant.Path;
import prefolio.prefolioserver.dto.CommonResponseDTO;
import prefolio.prefolioserver.dto.response.GetPathResponseDTO;
import prefolio.prefolioserver.service.SourceService;
import prefolio.prefolioserver.service.UserDetailsImpl;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/source")
@RequiredArgsConstructor
public class SourceController {

    private final SourceService sourceService;

    @Operation(
            summary = "presigned_url",
            description = "presigned URL 발급 메서드입니다. 프로필 이미지: profile, 게시글 이미지: image, 썸네일: thumbs",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(
                            schema = @Schema(implementation = GetPathResponseDTO.class)
                    )
            )
    })
    @GetMapping("/url")
    @ResponseBody
    public CommonResponseDTO<GetPathResponseDTO> createURL(
            @AuthenticationPrincipal UserDetailsImpl authUser,
            @RequestParam(name = "path") Path path
            ) {
        return CommonResponseDTO.onSuccess("Presigned URL", sourceService.createURL(authUser, path));
    }
}
