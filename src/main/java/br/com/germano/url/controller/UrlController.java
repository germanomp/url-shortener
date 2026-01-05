package br.com.germano.url.controller;

import br.com.germano.url.dto.UrlRequest;
import br.com.germano.url.dto.UrlResponse;
import br.com.germano.url.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(
        name = "URL Shortener",
        description = "Endpoints para encurtamento e redirecionamento de URLs"
)
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UrlController {

    private final UrlShortenerService service;

    @Operation(
            summary = "Encurta uma URL",
            description = "Recebe uma URL longa e retorna uma URL curta com tempo de expiração configurado"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "URL encurtada com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UrlResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida (URL ausente ou mal formatada)",
                    content = @Content
            )
    })
    @PostMapping("/shorten")
    public UrlResponse shorten(@RequestBody @Valid UrlRequest request) {
        String code = service.shortenUrl(request.getUrl());
        return new UrlResponse("http://localhost:8080/api/" + code);
    }

    @Operation(
            summary = "Redireciona para a URL original",
            description = "Redireciona o usuário para a URL original associada ao código curto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "302",
                    description = "Redirecionamento realizado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Código inválido (formato incorreto)"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Código não encontrado ou URL expirada"
            )
    })
    @GetMapping("/{code}")
    public void redirect(@PathVariable String code, HttpServletResponse response) throws IOException {

        if (!code.matches("^[a-zA-Z0-9]+$")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid short code");
            return;
        }

        String originalUrl = service.getOriginalUrl(code);

        if (originalUrl == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.sendRedirect(originalUrl);
    }
}
