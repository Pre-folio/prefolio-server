package prefolio.prefolioserver.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Configuration
public class OpenAPIDefinitionConfiguration {
    @Component
    @Profile("dev")
    @OpenAPIDefinition(servers = @Server(url = "https://prefolio.net"))
    public static class PrdOpenAPIDefinitionConfiguration {
    }

    @Component
    @Profile("local")
    @OpenAPIDefinition(servers = @Server(url = "http://localhost:8080"))
    public static class LocalOpenAPIDefinitionConfiguration {
    }
}
