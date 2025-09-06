package com.bridgeon.app.domain.board.service;


import com.bridgeon.app.domain.board.dto.response.EmployPostItemResponseDto;
import com.bridgeon.app.domain.board.entity.EmployBoard;
import com.bridgeon.app.domain.board.repository.EmployBoardJpaRepository;
import com.bridgeon.app.domain.board.repository.EmployScrapJpaRepository;
import com.bridgeon.app.global.enums.user.InterestField;
import com.bridgeon.app.global.enums.user.Region;
import com.bridgeon.app.global.exception.custom.BusinessException;
import com.bridgeon.app.global.exception.error.EmployErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class EmployPostService {

    private final EmployBoardJpaRepository employBoardJpaRepository;
    private final EmployScrapJpaRepository employScrapJpaRepository;

    @Transactional(readOnly = true)
    public Page<EmployPostItemResponseDto> getEmployPosts(
            Region region, // 선택값
            InterestField employType, // 선택값
            String employTitle,  // 선택값(부분검색)
            Pageable pageable,
            Long currentUserId
    ) {
        // 페이지 크기 제한 검사
        if (pageable.getPageSize() > 50) {
            throw new BusinessException(EmployErrorCode.PAGE_SIZE_TOO_LARGE);
        }

        // 제목 필수 검사
        if (employTitle != null && employTitle.isBlank()) {
            throw new BusinessException(EmployErrorCode.TITLE_REQUIRED);
        }

        // 제목 정규화
        final String normalizedTitle = (employTitle == null || employTitle.isBlank()) ? null : employTitle;

        // 기본 정렬 보장
        final Pageable ensured = pageable.getSort().isSorted()
                ? pageable
                : PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "createdAt"));

        // QBE prode 구성, 검색기능
        EmployBoard probe = EmployBoard.builder()
                .region(region)
                .employType(employType)
                .employTitle(normalizedTitle)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues() //prode에서 null인 필드는 조건에서 뺌
                .withMatcher("employTitle", m -> m.contains().ignoreCase()) //대소문자 무시
                .withIgnorePaths(
                        "id", "user",
                        "employContent",
                        "employStartDate", "employEndDate",
                        "createdAt", "updatedAt", "deletedAt"
                );

        Example<EmployBoard> example = Example.of(probe, matcher);

        Page<EmployBoard> boards = employBoardJpaRepository.findAll(example, ensured);

        // DTO 매핑
        return boards.map(e -> {
            boolean isScraped = currentUserId != null &&
                    employScrapJpaRepository.existsByUserIdAndEmployBoardId(currentUserId, e.getId());

            boolean authorized = false; // 목록 조회에서는 열람 승인 여부 의미 없음(상세에서 체크)

            return EmployPostItemResponseDto.of(e, authorized, isScraped);
        });
    }
}