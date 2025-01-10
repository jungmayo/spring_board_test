package com.example.board.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class PageRequestDTO {

    // 1페이지
    @Builder.Default
    private int pg = 1;

    @Builder.Default
    private int id = 1;

    @Builder.Default
    private int size = 5;

    // 0부터 카운트하기때문에 -1로 시작
    public Pageable getPageable(String sort) {
        return PageRequest.of(this.pg -1, this.size, Sort.by(sort).descending());
    }
}
