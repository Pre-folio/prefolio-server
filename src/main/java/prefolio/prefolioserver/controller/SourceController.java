package prefolio.prefolioserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import prefolio.prefolioserver.dto.CommonResponseDTO;
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
            description = "presigned URL 발급 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(
                            schema = @Schema(implementation = CommonResponseDTO.class)
                    )
            )
    })
    @GetMapping("/url")
    @ResponseBody
    public CommonResponseDTO<String> createURL(
            @AuthenticationPrincipal UserDetailsImpl authUser,
            String filePath
    ) {
        //user nickname 파일 이름으로?
        return CommonResponseDTO.onSuccess("Presigned URL", sourceService.createURL(authUser, filePath));
    }
}
