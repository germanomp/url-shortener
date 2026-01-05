package br.com.germano.url.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlRequest {

    @NotBlank
    private String url;

}
