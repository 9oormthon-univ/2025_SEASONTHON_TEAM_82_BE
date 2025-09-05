package com.bridgeon.app.domain.board.controller;


import com.bridgeon.app.domain.board.dto.response.SentApplicationItemResponseDto;
import com.bridgeon.app.domain.board.usecase.GetSentApplicationsUseCase;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employ/applications")
@RequiredArgsConstructor
public class GetSentApplicationController {

    private final GetSentApplicationsUseCase getSentApplicationsUseCase;


    //보낸 지원 목록 조회
    @GetMapping("me")
    public ResponseDto<PageListResponseDto<SentApplicationItemResponseDto>> getSentApplications(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) ApplyStatus status,
            @PageableDefault(size = 15, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Long currentUserId = (user == null) ? null : user.getId();

        Page<SentApplicationItemResponseDto> page =
                getSentApplicationsUseCase.execute(currentUserId, status, pageable);

        PageListResponseDto<SentApplicationItemResponseDto> dto = PageListResponseDto.of(page);

        return ResponseDto.success(
                HttpStatus.OK,
                "내 지원 목록 조회 성공",
                dto
        );
    }
}


