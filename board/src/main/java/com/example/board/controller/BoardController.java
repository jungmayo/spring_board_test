package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResponseDTO;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@Log4j2
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/write")
    public String BoardWrite() {
        return "boardWrite";
    }

    @PostMapping("/write")
    @ResponseBody
    public ResponseEntity<?> BoardWrites(@ModelAttribute BoardDTO boardDTO) {
        log.info("왜 안들어와? " + boardDTO);
        BoardDTO boardDTO1 = boardService.insert(boardDTO);
        log.info("이건..? : " + boardDTO1);
        return ResponseEntity.ok(boardDTO1);
    }

//    @GetMapping({"/", "/index", "/list"})
//    public String BoardList(Model model, PageRequestDTO pageRequestDTO, @RequestParam(value = "pg", defaultValue = "1") int pg) {
//        pageRequestDTO.setPg(pg);
//        List<BoardDTO> boardDTOS = boardService.select();
//        model.addAttribute("boardDTOS", boardDTOS);
//        log.info("뭐야이거 : " + boardDTOS);
//        return "index";
//    }

        @GetMapping({"/", "/index", "/list"})
    public String BoardList(Model model, PageRequestDTO pageRequestDTO, @RequestParam(value = "pg", defaultValue = "1") int pg) {
        pageRequestDTO.setPg(pg);
        PageResponseDTO<BoardDTO> pageResponseDTO = boardService.select(pageRequestDTO);
        model.addAttribute("pageResponseDTO", pageResponseDTO);
        model.addAttribute("boardDTOS", pageResponseDTO.getDtoList());

        log.info("뭐야이거 : " + pageResponseDTO);
        return "index";
    }

    @GetMapping("/modify/{boardId}")
    public String BoardModify(@PathVariable int boardId, Model model) {
        log.info("먀먀먀먐 : " + boardId);
        BoardDTO boardDTO = boardService.selectOne(boardId);
        model.addAttribute("boardDTO", boardDTO);
        log.info("이건 뭐라 나오냐!! : " + boardDTO);
        return "boardModify";
    }

    @PostMapping("/modify")
    public String BoardModify(@ModelAttribute BoardDTO boardDTO) {
        log.info("이거머얌?: " + boardDTO);
        boardService.update(boardDTO);
        return "redirect:/list";
    }



    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<?> BoardDelete(@PathVariable int boardId){
        return boardService.delete(boardId);
    }
}
