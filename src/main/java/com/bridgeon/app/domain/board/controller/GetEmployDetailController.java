package com.bridgeon.app.domain.board.controller;


import com.bridgeon.app.domain.board.dto.response.EmployPostItemResponseDto;
import com.bridgeon.app.domain.board.service.EmployDetailService;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.global.dto.response.PageListResponseDto;
import com.bridgeon.app.global.dto.response.ResponseDto;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseDto<EmployPostItemResponseDto> getEmployDetail(
            @AuthenticationPrincipal User user,
            @PathVariable Long employBoardId
    ) {
        if (user == null) {
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
        }

        EmployPostItemResponseDto result = employDetailService.getDetail(employBoardId, user.getId());


        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "구인게시판 상세 조회 성공",
                result
        );
    }
}