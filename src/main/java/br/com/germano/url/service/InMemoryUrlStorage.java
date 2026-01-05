package br.com.germano.url.service;

import br.com.germano.url.util.UrlStorage;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Profile("dev")
@Service
public class InMemoryUrlStorage implements UrlStorage {

    private final Map<String, String> storage = new ConcurrentHashMap<>();

    @Override
    public void save(String code, String url) {
        storage.put(code, url);
    }

    @Override
    public String find(String code) {
        return storage.get(code);
    }
}
