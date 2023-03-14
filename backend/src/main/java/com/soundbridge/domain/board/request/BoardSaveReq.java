package com.soundbridge.domain.board.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.soundbridge.domain.board.entity.FeedbackBoard;
import com.soundbridge.domain.member.entity.Member;
import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardSaveReq {

    @NotEmpty(message = "title cannot be empty.")
    private String title;
    @NotNull(message = "startTime cannot be null")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @NotNull(message = "endTIme cannot be null")
    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endTime;

    public FeedbackBoard toEntity(Member member) {
        return FeedbackBoard.builder()
            .title(this.title)
            .member(member)
            .startTime(this.startTime)
            .endTime(this.endTime)
            .build();
    }
}