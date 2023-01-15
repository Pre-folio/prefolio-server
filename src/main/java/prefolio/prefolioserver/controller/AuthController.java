package prefolio.prefolioserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import prefolio.prefolioserver.dto.CommonResponseDTO;
import prefolio.prefolioserver.dto.KakaoLoginDTO;
import prefolio.prefolioserver.service.AuthService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "카카오 로그인", description = "소셜로그인 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "kakao 로그인 성공",
                    content = @Content(
                            schema = @Schema(implementation = CommonResponseDTO.class)
                    )
            )
    })
    @GetMapping("/kakao")
    @ResponseBody
    public CommonResponseDTO<KakaoLoginDTO.Response> kakaoLogin(
            @RequestParam(name = "code") String code
    ) {
        log.trace("authorization code = {}", code);
        return CommonResponseDTO.onSuccess("kakao 로그인 성공", authService.kakaoLogin(code));
    }

}
