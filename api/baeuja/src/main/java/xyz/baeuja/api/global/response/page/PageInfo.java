package xyz.baeuja.api.global.response.page;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PageInfo {

    private final int pageNumber;
    private final int pageSize;
    private final boolean hasNext;
    private final boolean hasPrevious;

    public static PageInfo of(Page<?> page) {
        return new PageInfo(page.getNumber(), page.getSize(), page.hasNext(), page.hasPrevious());
    }

    public static PageInfo of(Slice<?> page) {
        return new PageInfo(page.getNumber(), page.getSize(), page.hasNext(), page.hasPrevious());
    }
}
