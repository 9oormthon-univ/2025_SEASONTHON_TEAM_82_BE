package com.bridgeon.app.domain.board.controller;


import com.bridgeon.app.domain.board.dto.response.EmployPostItemResponseDto;
import com.bridgeon.app.domain.board.service.EmployDetailService;
import com.bridgeon.app.global.dto.response.PageListResponseDto;
import com.bridgeon.app.global.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employ/posts")
@RequiredArgsConstructor
public class GetEmployDetailController {

    private final EmployDetailService employDetailService;

    @GetMapping("/{employBoardId}")
    public ResponseEntity<ResponseDto<EmployPostItemResponseDto>> getEmployBoardDetail(
            @PathVariable Long employBoardId
    ) {
        var item = employDetailService.getDetail(employBoardId);

        return ResponseEntity.ok(
                ResponseDto.success(
                        HttpStatus.OK,
                        "OK",
                        item
                )
        );
    }
}