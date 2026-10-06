package edu.kmu.simduck.simulator;

/**
 * 현재 브랜치가 나타내는 전략 패턴 실습 단계를 설명한다.
 */
public final class LabStage {
    private LabStage() {
    }

    public static String title() {
        return "2단계 - RubberDuck도 fly()를 상속";
    }

    public static String goal() {
        return "고무 오리가 Duck을 상속하자 의도하지 않은 비행 행동까지 함께 상속된다.";
    }

    public static boolean showFlyButton() {
        return true;
    }

    public static boolean showStructure() {
        return false;
    }

    public static String preferredDuck() {
        return "RubberDuck";
    }
}
