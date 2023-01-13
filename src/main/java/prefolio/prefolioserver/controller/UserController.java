package prefolio.prefolioserver.controller;

        import io.swagger.v3.oas.annotations.Operation;
        import io.swagger.v3.oas.annotations.media.Content;
        import io.swagger.v3.oas.annotations.media.Schema;
        import io.swagger.v3.oas.annotations.responses.ApiResponse;
        import io.swagger.v3.oas.annotations.responses.ApiResponses;
        import lombok.RequiredArgsConstructor;
        import org.springframework.web.bind.annotation.*;
        import prefolio.prefolioserver.dto.CheckUserDTO;
        import prefolio.prefolioserver.dto.CommonResponseDTO;
        import prefolio.prefolioserver.dto.UserInfoDTO;
        import prefolio.prefolioserver.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 정보 저장", description = "유저 정보 저장 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "저장 성공",
                    content = @Content(
                            schema = @Schema(implementation = CommonResponseDTO.class)
                    )
            )
    })
    @PostMapping("/join")
    @ResponseBody
    public CommonResponseDTO<UserInfoDTO.Response> userInfo(@RequestBody UserInfoDTO.Request userInfoRequest) {
        return CommonResponseDTO.onSuccess("유저 정보 저장 성공", userService.saveUserInfo(userInfoRequest));
    }

    @Operation(summary = "유저 닉네임 중복", description = "유저 정보 저장 메서드입니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "닉네임 사용 가능",
                    content = @Content(
                            schema = @Schema(implementation = CommonResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "201",
                    description = "닉네임 중복",
                    content = @Content(
                            schema = @Schema(implementation = CommonResponseDTO.class)
                    )
            )
    })
    @PostMapping("/nickname")
    @ResponseBody
    public CommonResponseDTO<CheckUserDTO.Response> checkUser(@RequestBody CheckUserDTO.Request checkUserRequest) {
        if(!userService.checkUser(checkUserRequest.getNickname()).is_used()){
            return CommonResponseDTO.onSuccess("닉네임 사용 가능", new CheckUserDTO.Response());

        }
        return CommonResponseDTO.onSuccess("닉네임 중복", new CheckUserDTO.Response());
    }
}
