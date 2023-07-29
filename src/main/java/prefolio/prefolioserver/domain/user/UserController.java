package prefolio.prefolioserver.domain.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import prefolio.prefolioserver.domain.post.dto.request.TokenRequestDTO;
import prefolio.prefolioserver.domain.user.dto.request.CheckUserRequestDTO;
import prefolio.prefolioserver.domain.user.dto.request.UserInfoRequestDTO;
import prefolio.prefolioserver.domain.user.dto.response.GetUserInfoResponseDTO;
import prefolio.prefolioserver.domain.user.dto.response.CheckUserResponseDTO;
import prefolio.prefolioserver.domain.user.dto.response.TokenResponseDTO;
import prefolio.prefolioserver.domain.user.dto.response.UserInfoResponseDTO;
import prefolio.prefolioserver.global.config.user.UserDetails;
import prefolio.prefolioserver.domain.user.service.UserService;


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
    public UserInfoResponseDTO updateUser(
            @AuthenticationPrincipal UserDetails authUser,
            @RequestBody UserInfoRequestDTO UserInfoRequest
    ) {
        return userService.setUserInfo(authUser, UserInfoRequest);
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
    public UserInfoResponseDTO setUserInfo(
            @AuthenticationPrincipal UserDetails authUser,
            @RequestBody UserInfoRequestDTO UserInfoRequest
    ) {
        return userService.setUserInfo(authUser, UserInfoRequest);
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
    public CheckUserResponseDTO checkUser(
            @RequestBody CheckUserRequestDTO checkUserRequest
    ) {
        return userService.findUserByNickname(checkUserRequest);
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
    public UserInfoResponseDTO getUserInfo(
            @AuthenticationPrincipal UserDetails authUser
    ) {
        return userService.getUserId(authUser);
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
    public GetUserInfoResponseDTO getUserInfo(
            @PathVariable(name = "userId") Long userId
    ) {
        return userService.getUserInfo(userId);
    }
    @Operation(
            summary = "토큰 재발급",
            description = "토큰 재발급 메서드입니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(
                            schema = @Schema(implementation = TokenResponseDTO.class)
                    )
            )
    })
    @PostMapping("/refresh/{userId}")
    @ResponseBody
    public TokenResponseDTO getNewToken(
            @RequestBody TokenRequestDTO refreshToken,
            @PathVariable(name = "userId") Long userId
    ) {
        return userService.getNewToken(refreshToken, userId);
    }
}
