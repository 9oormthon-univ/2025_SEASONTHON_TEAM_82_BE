package com.bridgeon.app.domain.board.controller;


import com.bridgeon.app.domain.board.dto.response.ReceivedApplicationItemResponseDto;
import com.bridgeon.app.domain.board.service.ReceivedApplicationService;
import com.bridgeon.app.domain.board.usecase.EmployApplicationUseCase;
import com.bridgeon.app.domain.board.usecase.GetReceivedApplicationsUseCase;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.global.dto.response.PageListResponseDto;
import com.bridgeon.app.global.dto.response.ResponseDto;
import com.bridgeon.app.global.enums.board.ApplyStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employ/posts")
@RequiredArgsConstructor
public class GetReceivedApplicationController {


    private final GetReceivedApplicationsUseCase getReceivedApplicationsUseCase;


    @GetMapping("/{employBoardId}/applications")
    public ResponseDto<PageListResponseDto<ReceivedApplicationItemResponseDto>> getReceivedApplications(
            @AuthenticationPrincipal User user,
            @PathVariable Long employBoardId,
            @RequestParam(required = false) ApplyStatus status,
            @PageableDefault(size = 15, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Long currentUserId = (user == null) ? null : user.getId();

        Page<ReceivedApplicationItemResponseDto> page =
                getReceivedApplicationsUseCase.execute(employBoardId, status, pageable, currentUserId);

        PageListResponseDto<ReceivedApplicationItemResponseDto> dto = PageListResponseDto.of(page);

        return ResponseDto.success(
                HttpStatus.OK,
                "지원자 목록 조회 성공",
                dto
        );
    }
}
