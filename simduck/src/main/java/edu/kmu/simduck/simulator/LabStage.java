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
        return "2단계 - RubberDuck도 fly()를 상속";
    }

    /**
     * 현재 단계에서 확인할 핵심 질문을 반환한다.
     *
     * @return 실습 질문
     */
    public static String goal() {
        return "고무 오리가 Duck을 상속하자 의도하지 않은 비행 행동까지 함께 상속된다.";
    }
}
