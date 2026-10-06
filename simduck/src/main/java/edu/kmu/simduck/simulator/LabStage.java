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
        return "4단계 - Flyable과 Quackable 분리";
    }

    /**
     * 현재 단계에서 확인할 핵심 질문을 반환한다.
     *
     * @return 실습 질문
     */
    public static String goal() {
        return "필요한 오리만 행동 인터페이스를 구현하지만 같은 행동 코드가 여러 클래스에 중복된다.";
    }
}
