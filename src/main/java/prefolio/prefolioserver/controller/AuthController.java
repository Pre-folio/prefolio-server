package prefolio.prefolioserver.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prefolio.prefolioserver.dto.CommonResponseDTO;
import prefolio.prefolioserver.dto.UserInfoDTO;
import prefolio.prefolioserver.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @Operation(summary = "카카오 인가 코드", description = "소셜로그인 - 카카오 인가 코드 발급 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "인가 코드 발급",
                    content = @Content(
                            schema = @Schema(implementation = CommonResponseDTO.class)
                    )
            )
    })
    @GetMapping("/kakao")
    @ResponseBody
    public CommonResponseDTO<UserInfoDTO.Response> userInfo(@RequestBody UserInfoDTO.Request userInfoRequest) {
        return CommonResponseDTO.onSuccess("유저 정보 저장 성공", userService.saveUserInfo(userInfoRequest));
    }

}
