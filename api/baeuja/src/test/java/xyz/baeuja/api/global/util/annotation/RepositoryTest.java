package xyz.baeuja.api.global.util.annotation;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test")
@Sql(scripts = {
        "/sql/truncate_all.sql",
        "/sql/content.sql",
        "/sql/unit.sql",
        "/sql/sentence.sql",
        "/sql/word.sql",
        "/sql/sentence_word.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
public @interface RepositoryTest {
}
