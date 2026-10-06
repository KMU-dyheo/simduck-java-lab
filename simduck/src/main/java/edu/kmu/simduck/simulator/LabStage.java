package edu.kmu.simduck.simulator;

/**
 * 현재 브랜치가 나타내는 전략 패턴 실습 단계를 설명한다.
 */
public final class LabStage {
    private LabStage() {
    }

    public static String title() {
        return "5단계 - 변하는 행동을 객체로 분리";
    }

    public static String goal() {
        return "Duck은 비행과 울음을 직접 구현하지 않고 FlyBehavior와 QuackBehavior에 위임한다.";
    }

    public static boolean showFlyButton() {
        return true;
    }

    public static boolean showStructure() {
        return true;
    }

    public static String preferredDuck() {
        return "RubberDuck";
    }
}
