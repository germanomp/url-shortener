package br.com.germano.url.service;

import br.com.germano.url.util.Base62Encoder;
import br.com.germano.url.util.IdGenerator;
import br.com.germano.url.util.UrlStorage;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

    private final UrlStorage storage;
    private final IdGenerator idGenerator;

    public UrlShortenerService(UrlStorage storage, IdGenerator idGenerator) {
        this.storage = storage;
        this.idGenerator = idGenerator;
    }

    public String shortenUrl(String originalUrl) {
        long id = idGenerator.nextId();
        String code = Base62Encoder.encode(id);

        storage.save(code, originalUrl);
        return code;
    }

    public String getOriginalUrl(String code) {
        return storage.find(code);
    }
}
