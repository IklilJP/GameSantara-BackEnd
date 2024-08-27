package com.example.rakyatgamezomeapi.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagingResponse {
    private Integer totalPages;
    private Long totalElements;
    private Integer page;
    private Integer size;
    private Boolean hasPrevious;
    private Boolean hasNext;
}
