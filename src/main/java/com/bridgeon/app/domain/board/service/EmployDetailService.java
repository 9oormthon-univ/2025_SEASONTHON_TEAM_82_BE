package com.bridgeon.app.domain.board.service;


import com.bridgeon.app.domain.board.dto.response.EmployPostItemResponseDto;
import com.bridgeon.app.domain.board.entity.EmployBoard;
import com.bridgeon.app.domain.board.repositroy.EmployBoardJpaRepository;
import com.bridgeon.app.domain.board.repositroy.EmployScrapJpaRepository;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.EmployErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmployDetailService {

    private final EmployBoardJpaRepository employBoardJpaRepository;
    private final EmployScrapJpaRepository employScrapJpaRepository;

    public EmployPostItemResponseDto getDetail(Long employBoardId, Long userId) {
        EmployBoard board = employBoardJpaRepository.findByIdAndDeletedAtIsNull(employBoardId)
                .orElseThrow(() -> new BusinessException(EmployErrorCode.EMPLOY_BOARD_NOT_FOUND));


        boolean isScraped = employScrapJpaRepository.existsByUserIdAndEmployBoardId(userId, board.getId());


        return EmployPostItemResponseDto.of(board, false);
    }
}
