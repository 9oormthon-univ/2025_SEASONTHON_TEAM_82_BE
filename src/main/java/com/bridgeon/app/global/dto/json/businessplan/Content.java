package com.bridgeon.app.global.dto.json.businessplan;

public record Content(
        String overview, // 사업 개요
        String marketAnalysis, // 시장 분석
        String businessModel, // BM
        String actionPlan // 실행 계획
) {
}
