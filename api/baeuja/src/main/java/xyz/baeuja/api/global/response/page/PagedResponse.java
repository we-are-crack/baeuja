package xyz.baeuja.api.global.response.page;

import java.util.List;

public record PagedResponse<T>(List<T> content, PageInfo pageInfo) {
}
