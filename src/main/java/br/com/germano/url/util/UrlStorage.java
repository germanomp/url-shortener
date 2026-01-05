package br.com.germano.url.util;

public interface UrlStorage {

    void save(String code, String url);

    String find(String code);
}
