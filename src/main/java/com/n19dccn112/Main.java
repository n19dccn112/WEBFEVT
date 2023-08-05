package com.n19dccn112;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication

/**
Với @OpenAPIDefinition, đây là một annotation để xác định API của ứng dụng.
@Info: Cung cấp thông tin về API, bao gồm tiêu đề, mô tả, thông tin liên hệ và giấy phép.
@Contact: Chỉ định thông tin liên hệ của người phát triển API.
@License: Chỉ định giấy phép sử dụng API.
@Server: Định nghĩa một máy chủ để chạy API.
@SecurityRequirement: Chỉ định các yêu cầu bảo mật cho API. Trong trường hợp này, nó yêu cầu một bearer token để truy cập API.
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Web API Vật Tư",
                description = "N19DCCN112",
                contact = @Contact(
                        name = "",
                        url = "",
                        email = "mynth30@gmail.com"
                ),
                version = "1",
                license = @License(
                        name = "MIT Licence",
                        url = "https://github.com/thombergs/code-examples/blob/master/LICENSE"
                )
        )
)
/**
 annotation @SecuritySchemes và @SecurityScheme được sử dụng để định nghĩa
 và cấu hình các hệ thống bảo mật cho API của ứng dụng. Trong trường hợp này,
 nó định nghĩa hệ thống bảo mật "bearerToken" sử dụng HTTP, với loại chứng chỉ là "bearer" và token được sử dụng là JWT.
 */
@SecuritySchemes({
        @SecurityScheme(
                name = "brearerToken",
                type = SecuritySchemeType.HTTP,
                scheme = "brearer",
                bearerFormat = "JWT"
        )
})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}