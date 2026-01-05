package br.com.germano.url.service;

import br.com.germano.url.util.IdGenerator;
import br.com.germano.url.util.UrlStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {

    @Mock
    private UrlStorage storage;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private UrlShortenerService service;

    @Test
    void shouldGenerateShortCodeAndStoreUrl() {
        when(idGenerator.nextId()).thenReturn(100L);

        String code = service.shortenUrl("https://example.com");

        assertThat(code).isNotNull();
        verify(storage).save(eq(code), eq("https://example.com"));
    }

    @Test
    void shouldReturnOriginalUrl() {
        when(storage.find("abc")).thenReturn("https://google.com");

        String url = service.getOriginalUrl("abc");

        assertThat(url).isEqualTo("https://google.com");
    }
}

