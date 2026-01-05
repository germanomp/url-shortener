package br.com.germano.url.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Resposta contendo a URL encurtada")
public class UrlResponse {

    @Schema(
            description = "URL curta gerada",
            example = "http://localhost:8080/aZ3F"
    )
    private String shortUrl;
}
