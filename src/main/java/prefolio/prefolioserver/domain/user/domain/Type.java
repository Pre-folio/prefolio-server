package prefolio.prefolioserver.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum Type {
    PLAN("기획"),
    DESIGN("디자인"),
    DEV("개발");

    @JsonValue
    private final String type;

    @JsonCreator
    public static Type parsing(String inputValue) {
        return Stream.of(Type.values())
                .filter(category -> category.getType().equals(inputValue))
                .findFirst()
                .orElse(null);
    }
}
