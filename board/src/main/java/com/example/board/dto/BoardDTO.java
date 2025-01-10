package com.example.board.dto;

import com.example.board.entity.Board;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class BoardDTO {

    private int id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime rdate;

    public static BoardDTO toDTO(Board board) {
        return BoardDTO.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .rdate(board.getRdate())
                .build();
    }
}
