package prefolio.prefolioserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseDTO<T> {

    private String resultMessage;
    private T resultData;

    public ResponseDTO(final String resultMessage) {
        this.resultMessage = resultMessage;
        this.resultData = null;
    }

    public static<T> ResponseDTO<T> res(final String resultMessage) {
        return res(resultMessage, null);
    }

    public static<T> ResponseDTO<T> res(final String resultMessage, final T t) {
        return ResponseDTO.<T>builder()
                .resultData(t)
                .resultMessage(resultMessage)
                .build();
    }
}
