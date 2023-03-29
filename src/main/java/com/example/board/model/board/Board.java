package com.example.board.model.board;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Board {
    private Long board_id;
    private String title;
    private String contents;
    private String member_id;
    private Long hit;
    private LocalDateTime created_time;

    public void addHit() {
        this.hit++;
    }
}
