package xyz.baeuja.api.content.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import xyz.baeuja.api.content.domain.Classification;

@Component
public class ClassificationConverter implements Converter<String, Classification> {

    @NonNull
    @Override
    public Classification convert(@NonNull String source) {
        return Classification.valueOf(source.trim().toUpperCase());
    }
}
