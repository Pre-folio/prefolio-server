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
import prefolio.prefolioserver.dto.CommonResponseDTO;
import prefolio.prefolioserver.dto.request.CheckUserRequestDTO;
import prefolio.prefolioserver.dto.request.UserInfoRequestDTO;
import prefolio.prefolioserver.dto.response.GetUserInfoResponseDTO;
import prefolio.prefolioserver.dto.response.CheckUserResponseDTO;
import prefolio.prefolioserver.dto.response.UserInfoResponseDTO;
import prefolio.prefolioserver.service.UserDetailsImpl;
import prefolio.prefolioserver.service.UserService;


@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "유저 정보 저장",
            description = "유저 정보 저장 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "저장 성공",
                    content = @Content(
                            schema = @Schema(implementation = UserInfoResponseDTO.class)
                    )
            )
    })
    @PostMapping("/join")
    @ResponseBody
    public CommonResponseDTO<UserInfoResponseDTO> updateUser(
            @AuthenticationPrincipal UserDetailsImpl authUser,
            @RequestBody UserInfoRequestDTO UserInfoRequest
    ) {
        return CommonResponseDTO.onSuccess("유저 정보 저장 성공", userService.setUserInfo(authUser, UserInfoRequest));
    }

    @Operation(
            summary = "유저 정보 수정",
            description = "유저 정보 수정 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "수정 성공",
                    content = @Content(
                            schema = @Schema(implementation = UserInfoResponseDTO.class)
                    )
            )
    })
    @PutMapping("/join")
    @ResponseBody
    public CommonResponseDTO<UserInfoResponseDTO> setUserInfo(
            @AuthenticationPrincipal UserDetailsImpl authUser,
            @RequestBody UserInfoRequestDTO UserInfoRequest
    ) {
        return CommonResponseDTO.onSuccess("유저 정보 수정 성공", userService.setUserInfo(authUser, UserInfoRequest));
    }

    @Operation(
            summary = "유저 닉네임 중복",
            description = "유저 닉네임 중복 확인 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "닉네임 사용 가능",
                    content = @Content(
                            schema = @Schema(implementation = CheckUserResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "201",
                    description = "닉네임 중복",
                    content = @Content(
                            schema = @Schema(implementation = CheckUserResponseDTO.class)
                    )
            )
    })
    @PostMapping("/nickname")
    @ResponseBody
    public CommonResponseDTO<CheckUserResponseDTO> checkUser(@RequestBody CheckUserRequestDTO checkUserRequest) {
        return CommonResponseDTO.onSuccess("닉네임 확인", userService.findUserByNickname(checkUserRequest));
    }

    @Operation(
            summary = "유저 아이디",
            description = "유저 아이디 조회 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(
                            schema = @Schema(implementation = UserInfoResponseDTO.class)
                    )
            )
    })
    @GetMapping("/token")
    @ResponseBody
    public CommonResponseDTO<UserInfoResponseDTO> getUserInfo(
            @AuthenticationPrincipal UserDetailsImpl authUser
    ) {
        return CommonResponseDTO.onSuccess("유저 아이디", userService.getUserId(authUser));
    }

    @Operation(
            summary = "유저 정보",
            description = "유저 정보 메서드입니다.",
            security = {@SecurityRequirement(name = "jwtAuth")}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(
                            schema = @Schema(implementation = GetUserInfoResponseDTO.class)
                    )
            )
    })
    @GetMapping("/{userId}")
    @ResponseBody
    public CommonResponseDTO<GetUserInfoResponseDTO> getUserInfo(
            @PathVariable(name = "userId") Long userId
    ) {
        return CommonResponseDTO.onSuccess("유저 정보", userService.getUserInfo(userId));
    }
}
