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
        return "5단계 - 변하는 행동을 객체로 분리";
    }

    /**
     * 현재 단계에서 확인할 핵심 질문을 반환한다.
     *
     * @return 실습 질문
     */
    public static String goal() {
        return "Duck은 비행과 울음을 직접 구현하지 않고 FlyBehavior와 QuackBehavior에 위임한다.";
    }
}
