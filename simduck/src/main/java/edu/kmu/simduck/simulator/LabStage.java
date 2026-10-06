package edu.kmu.simduck.simulator;

/**
 * 현재 브랜치가 나타내는 전략 패턴 실습 단계를 설명한다.
 */
public final class LabStage {
    private LabStage() {
    }

    /**
     * 현재 단계의 제목을 반환한다.
     *
     * @return 단계 제목
     */
    public static String title() {
        return "3단계 - 맞지 않는 행동을 재정의";
    }

    /**
     * 현재 단계에서 확인할 핵심 질문을 반환한다.
     *
     * @return 실습 질문
     */
    public static String goal() {
        return "문제는 막았지만 새 오리마다 상속받은 행동을 취소하는 재정의가 반복된다.";
    }
}
