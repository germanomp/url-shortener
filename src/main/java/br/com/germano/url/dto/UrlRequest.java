package br.com.germano.url.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@Schema(description = "Requisição para encurtar uma URL")
public class UrlRequest {

    @Schema(
            description = "URL original que será encurtada",
            example = "https://www.google.com"
    )
    @NotBlank
    @URL
    private String url;
}
