package prefolio.prefolioserver.dto;

import lombok.*;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseDTO<T> {

    String message;
    T data;

    public static <T> CommonResponseDTO<T> onSuccess(String message, T data) {
        return new CommonResponseDTO<>(message, data);
    }

    public static CommonResponseDTO onFailure(String message) {
        return new CommonResponseDTO<>(message, null);
    }
}
