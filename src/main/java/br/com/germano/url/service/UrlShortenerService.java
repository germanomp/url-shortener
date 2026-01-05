package br.com.germano.url.service;

import br.com.germano.url.util.Base62Encoder;
import br.com.germano.url.util.UrlStorage;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class UrlShortenerService {

    private final UrlStorage storage;
    private final AtomicLong counter = new AtomicLong(1000);

    public UrlShortenerService(UrlStorage storage) {
        this.storage = storage;
    }

    public String shortenUrl(String originalUrl) {
        long id = counter.incrementAndGet();
        String code = Base62Encoder.encode(id);

        storage.save(code, originalUrl);
        return code;
    }

    public String getOriginalUrl(String code) {
        return storage.find(code);
    }
}
