package org.one.global.pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPagingDto {
    private Integer page = 0;
    private Integer size = 10;
    private String sort = "id";
    private String direction = "DESC";

    public Pageable toPageable() {
        Sort.Direction dir = Sort.Direction.fromString(direction);
        return PageRequest.of(page, size, Sort.by(dir, sort));
    }
}