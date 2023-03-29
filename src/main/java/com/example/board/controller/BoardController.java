package com.example.board.controller;

import com.example.board.model.board.Board;
import com.example.board.model.board.BoardWriteForm;
import com.example.board.model.member.Member;
import com.example.board.repository.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("board")
@Controller
public class BoardController {

    private final BoardMapper boardMapper;

    // 글쓰기 페이지 이동
    @GetMapping("write")
    public String writeForm(Model model) {
        model.addAttribute("writeForm", new BoardWriteForm());
        return "board/write";
    }

    // 게시글 쓰기
    @PostMapping("write")
    public String write(@Validated @ModelAttribute("writeForm") BoardWriteForm boardWriteForm,
                        BindingResult result,
                        @SessionAttribute(value = "loginMember") Member loginMember) {
        log.info("board: {}", boardWriteForm);

        if (result.hasErrors()) {
            return "board/write";
        }

        Board board = BoardWriteForm.toBoard(boardWriteForm);
        board.setMember_id(loginMember.getMember_id());
        boardMapper.saveBoard(board);

        return "redirect:/board/list";
    }

    // 게시글 전체 보기
    @GetMapping("list")
    public String list(@SessionAttribute(value = "loginMember", required = false) Member loginMember,
                       Model model) {
        if (loginMember == null) {
            return "redirect:/member/login";
        }

        List<Board> boards = boardMapper.findAllBoards();
        model.addAttribute("boards", boards);

        return "board/list";
    }

    // 게시글 읽기
    @GetMapping("read")
    public String read(@RequestParam Long board_id,
                       Model model) {
        log.info("id: {}", board_id);

        Board board = boardMapper.findBoard(board_id);
        board.addHit();
        boardMapper.updateBoard(board);

        model.addAttribute("board", board);

        return "board/read";
    }

    // 게시글 수정 페이지 이동
    @GetMapping("update")
    public String updateForm(@RequestParam Long id,
                             Model model) {
        log.info("id: {}", id);

        return "board/update";
    }

    // 게시글 수정
    @PostMapping("update")
    public String update(@RequestParam Long id,
                         @ModelAttribute Board updateBoard) {
        log.info("board: {}", updateBoard);

        return "redirect:/board/list";
    }

    // 게시글 삭제
    @PostMapping("delete")
    public String remove(@RequestParam Long id,
                         @RequestParam String password) {

        return "redirect:/board/list";
    }

}
