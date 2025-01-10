package com.example.board.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class PageResponseDTO<T> {

    //현재 페이지
    private int pg;

    //페이지 크기
    private int size;

    //전체 게시물 수
    private int total;

    //게시물 리스트
    private List<T> dtoList;

    private boolean prev;
    private boolean next;

    private int startNo;


    private int start;


    private int end;

    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<T> dtoList, int total) {
        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.dtoList = dtoList;
        this.total = total;

        // 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) total / size);

        // 페이지 네비게이션 계산
        int pageCount = 10; // 표시할 페이지 번호의 개수
        int currentBlock = (pg - 1) / pageCount;

        start = currentBlock * pageCount + 1;
        end = Math.min(start + pageCount - 1, totalPages);

        prev = pg > 1; // 현재 페이지가 1보다 크면 이전 페이지가 있음
        next = pg < totalPages; // 현재 페이지가 총 페이지 수보다 작으면 다음 페이지가 있음
    }

}
