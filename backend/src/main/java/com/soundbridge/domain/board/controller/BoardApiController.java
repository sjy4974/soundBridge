package com.soundbridge.domain.board.controller;

import com.soundbridge.domain.board.request.BoardSaveReq;
import com.soundbridge.domain.board.response.BoardDetailRes;
import com.soundbridge.domain.board.service.BoardService;
import com.soundbridge.domain.member.response.MemberAccessRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feedback-boards")
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping
    @Operation(summary = "피드백 상담 게시글 등록")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "피드백 상당 게시글 등록 성공"),
        @ApiResponse(responseCode = "400", description = "입력 파라미터 오류"),
        @ApiResponse(responseCode = "404", description = "존재 하지 않는 유저")
    })
    public ResponseEntity feedbackBoardSave(@Valid @RequestBody BoardSaveReq req,
        Authentication authentication) {
        Long memberId = ((MemberAccessRes) authentication.getPrincipal()).getId();
        boardService.saveFeedbackBoard(req, memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "피드백 상담 게시글 조회")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "피드백 상당 게시글 조회 성공"),
        @ApiResponse(responseCode = "404", description = "존재 하지 않는 유저")
    })
    public ResponseEntity<Slice<BoardDetailRes>> feedbackBoardList(
        @PageableDefault(size = 5) Pageable pageable,
        @RequestParam(required = false) Long cursorId,
        Authentication authentication) {
        Long memberId = ((MemberAccessRes) authentication.getPrincipal()).getId();
        return ResponseEntity.ok(boardService.findAllWithPaging(pageable, cursorId, memberId));

    }

    @DeleteMapping("/{feedbackBoardId}")
    @Operation(summary = "피드백 상담 게시글 삭제")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "피드백 상당 게시글 삭제 성공"),
        @ApiResponse(responseCode = "403", description = "권한 없는 유저"),
        @ApiResponse(responseCode = "404", description = "존재 하지 않는 유저"),
        @ApiResponse(responseCode = "404", description = "존재 하지 않는 게시글")
    })
    public ResponseEntity feedbackBoardDelete(
        @PathVariable Long feedbackBoardId,
        Authentication authentication) {
        Long memberId = ((MemberAccessRes) authentication.getPrincipal()).getId();
        boardService.deleteBoard(feedbackBoardId, memberId);
        return ResponseEntity.ok().build();
    }

}
