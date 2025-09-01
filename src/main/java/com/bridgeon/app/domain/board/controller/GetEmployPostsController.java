package com.bridgeon.app.domain.board.controller;


import com.bridgeon.app.domain.board.dto.response.EmployPostItemResponseDto;
import com.bridgeon.app.domain.board.service.EmployPostService;
import com.bridgeon.app.domain.user.entity.User;
import com.bridgeon.app.global.dto.response.PageListResponseDto;
import com.bridgeon.app.global.dto.response.ResponseDto;
import com.bridgeon.app.global.enums.user.InterestField;
import com.bridgeon.app.global.enums.user.Region;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.EmployErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employ-posts")
@RequiredArgsConstructor
public class GetEmployPostsController {

    private final EmployPostService employPostService;

    @GetMapping
    public ResponseEntity<ResponseDto<Map<String, Object>>> getEmployPosts(
            @RequestParam(required = false) Region region,
            @RequestParam(required = false) InterestField employType,
            @RequestParam(required = false, name = "title") String employTitle,
//            @AuthenticationPrincipal User user,
            @RequestAttribute(name = "userId", required = false) Long userId,
            @PageableDefault(size = 15, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        var pageResult = employPostService.getEmployPosts(
            region, employType, employTitle, pageable, userId
        );

        //추후에 인증 구현되면 바꾸기
//        var resultPage = employPostService.getEmployPosts(region, employType, title, pageable, user.getId());
        PageListResponseDto<EmployPostItemResponseDto> dto = PageListResponseDto.of(pageResult);


        Map<String, Object> data = new LinkedHashMap<>();
        data.put("employPosts", dto.items());
        data.put("page", dto.page());
        data.put("size", dto.size());
        data.put("totalElements", dto.totalElements());
        data.put("totalPages", dto.totalPages());
        data.put("hasNext", dto.hasNext());
        data.put("hasPrev", dto.hasPrev());

        return ResponseEntity.ok(ResponseDto.success(
                HttpStatus.OK,
                "OK",
                data
            )
        );
    }
}