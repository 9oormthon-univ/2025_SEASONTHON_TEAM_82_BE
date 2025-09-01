package com.bridgeon.app.domain.board.service;


import com.bridgeon.app.domain.board.dto.response.EmployPostItemResponseDto;
import com.bridgeon.app.domain.board.entity.EmployBoard;
import com.bridgeon.app.domain.board.repositroy.EmployBoardJpaRepository;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.EmployErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmployDetailService {

    private final EmployBoardJpaRepository employBoardJpaRepository;

    public EmployPostItemResponseDto getDetail(Long employBoardId) {
        EmployBoard board = employBoardJpaRepository.findByIdAndDeletedAtIsNull(employBoardId)
                .orElseThrow(() -> new BusinessException(EmployErrorCode.EMPLOY_BOARD_NOT_FOUND));

        // TODO: 인증 붙으면 현재 사용자 스크랩 여부 확인
        return EmployPostItemResponseDto.of(board, false);
    }
}
