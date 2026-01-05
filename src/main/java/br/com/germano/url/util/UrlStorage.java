package br.com.germano.url.util;

import java.time.Duration;

public interface UrlStorage {

    void save(String shortCode, String originalUrl);

    String find(String shortCode);

    void delete(String shortCode);
}
