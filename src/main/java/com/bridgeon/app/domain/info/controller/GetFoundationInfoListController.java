package com.bridgeon.app.domain.info.controller;


import com.bridgeon.app.domain.info.dto.response.FoundationInfoItemResponseDto;
import com.bridgeon.app.domain.info.usecase.FoundationInfoListUseCase;
import com.bridgeon.app.global.dto.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/foundation-info-list")
public class GetFoundationInfoListController {

    private final FoundationInfoListUseCase listUseCase;

    @GetMapping
    public ResponseDto<Page<FoundationInfoItemResponseDto>> getFoundationInfos(
            @PageableDefault(size = 15, sort = "infoStartDate", direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        Page<FoundationInfoItemResponseDto> result = listUseCase.getList(pageable);
        return new ResponseDto<>(
                HttpStatus.OK.value(),
                "창업 정보 목록 조회 성공",
                result
        );
    }
}
