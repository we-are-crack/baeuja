package xyz.baeuja.api.content.cache;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.baeuja.api.content.repository.WordRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@RequiredArgsConstructor
public class WordIdCache {

    private final WordRepository wordRepository;
    private List<Long> ids = new ArrayList<>();

    @PostConstruct
    public void init() {
        this.ids = wordRepository.findAllIds();
    }

}
