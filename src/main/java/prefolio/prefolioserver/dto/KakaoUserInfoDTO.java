package prefolio.prefolioserver.dto;

        import lombok.Builder;
        import lombok.Getter;
        import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class KakaoUserInfoDTO {

    private String email;

    public KakaoUserInfoDTO(String email) {
        this.email = email;
    }
}