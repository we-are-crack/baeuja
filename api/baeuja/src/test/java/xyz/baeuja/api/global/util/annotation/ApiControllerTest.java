package xyz.baeuja.api.global.util.annotation;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
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
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
public @interface ApiControllerTest {
}
