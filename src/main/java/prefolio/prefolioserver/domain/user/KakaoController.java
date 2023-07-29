package prefolio.prefolioserver.domain.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import prefolio.prefolioserver.domain.user.dto.response.KakaoLoginResponseDTO;
import prefolio.prefolioserver.domain.user.service.KakaoService;

@Slf4j
@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    @Operation(summary = "카카오 로그인", description = "소셜로그인 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "kakao 로그인 성공",
                    content = @Content(
                            schema = @Schema(implementation = KakaoLoginResponseDTO.class)
                    )
            )
    })
    @GetMapping("/login")
    @ResponseBody
    public KakaoLoginResponseDTO kakaoLogin(
            @RequestParam(name = "code") String code
    ) {
        return kakaoService.kakaoLogin(code);
    }
}
