package br.com.germano.url.controller;

import br.com.germano.url.dto.UrlRequest;
import br.com.germano.url.dto.UrlResponse;
import br.com.germano.url.service.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class Controller {

    private final UrlShortenerService service;

    @RequestMapping("/shorten")
    public UrlResponse shorten(@RequestBody @Valid UrlRequest request) {
        String code = service.shortenUrl(request.getUrl());
        return new UrlResponse("http://localhost:8080/" + code);
    }

    @RequestMapping("/{code}")
    public void redirect(@PathVariable String code, HttpServletResponse response) throws IOException {
        String originalUrl = service.getOriginalUrl(code);

        if (originalUrl == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.sendRedirect(originalUrl);
    }
}
