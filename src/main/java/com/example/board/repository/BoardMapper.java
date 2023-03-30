package com.example.board.repository;

import com.example.board.model.board.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void saveBoard(Board board);
    List<Board> findAllBoards();
    Board findBoard(Long board_id);
    void updateBoard(Board updateBoard);
    void removeBoard(Long board_id);
}
