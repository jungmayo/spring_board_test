package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResponseDTO;
import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    //글 등록
    public BoardDTO insert(BoardDTO boardDTO) {
         Board board1 = boardRepository.save(Board.builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .build());
        log.info(board1);

        BoardDTO boardDTO1 = boardDTO.builder()
                .title(board1.getTitle())
                .content(board1.getContent())
                .id(board1.getId())
                .build();
        log.info(boardDTO1);
        return boardDTO1;
    }

    // 글 조회
    public PageResponseDTO<BoardDTO> select(PageRequestDTO pageRequestDTO) {

        // Pageable 객체 생성
        Pageable pageable = pageRequestDTO.getPageable("rdate");

        // 데이터 조회 ( 페이징 처리)
        Page<Board> result = boardRepository.findAll(pageable);

        List<BoardDTO> boardDTOS = result.stream().map(board -> new BoardDTO(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getWriter(),
                board.getRdate()
        )).collect(Collectors.toList());

        return new PageResponseDTO<>(pageRequestDTO, boardDTOS, (int) result.getTotalElements());
//        List<Board> boardList = boardRepository.findAll();
//        List<BoardDTO> boardDTOS = new ArrayList<>();
//        for (Board board : boardList) {
//            BoardDTO boardDTO = new BoardDTO();
//            boardDTO.setTitle(board.getTitle());
//            boardDTO.setContent(board.getContent());
//            boardDTO.setId(board.getId());
//            boardDTOS.add(boardDTO);
//        }
//        return boardDTOS;
    }

//    // 글 조회
//    public List<BoardDTO> select(){
//        List<Board> boardList = boardRepository.findAll();
//        List<BoardDTO> boardDTOS = new ArrayList<>();
//        for (Board board : boardList) {
//            BoardDTO boardDTO = new BoardDTO();
//            boardDTO.setTitle(board.getTitle());
//            boardDTO.setContent(board.getContent());
//            boardDTO.setId(board.getId());
//            boardDTOS.add(boardDTO);
//        }
//        return boardDTOS;
//    }

    // 단일 조회
    public BoardDTO selectOne(int BoardId) {
        Board board = boardRepository.findById(BoardId).get();
        BoardDTO boardDTO = new BoardDTO().builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
        log.info("이거 뭐라 나오노 : " + boardDTO);
        return boardDTO;

    }

    //수정
    public BoardDTO update(BoardDTO boardDTO) {
        Board board = boardRepository.findById(boardDTO.getId()).get();
        if(board.getTitle() != null){
            board.setTitle(boardDTO.getTitle());
        }
        if(board.getContent() != null){
            board.setContent(boardDTO.getContent());
        }
        boardRepository.save(board);
        BoardDTO boardDTO1 = new BoardDTO().builder()
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .build();
        log.info(boardDTO1);
        return boardDTO1;

    }
    //삭제
    public ResponseEntity<?> delete(int BoardId) {
        Board board = boardRepository.findById(BoardId).get();
        boardRepository.delete(board);
        return ResponseEntity.ok().build();
    }
}
