package xyz.baeuja.api.content.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.baeuja.api.content.domain.Classification;
import xyz.baeuja.api.content.domain.Content;

public interface ContentRepository extends JpaRepository<Content, Long> {

    Slice<Content> findAllByClassification(Classification classification, Pageable pageable);
}
