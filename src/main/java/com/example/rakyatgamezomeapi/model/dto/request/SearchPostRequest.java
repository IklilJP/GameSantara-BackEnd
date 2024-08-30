package com.example.rakyatgamezomeapi.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchPostRequest {
    private String query;
    private Boolean status;
    private Integer page;
    private Integer size;
}
