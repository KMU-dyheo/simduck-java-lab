package edu.kmu.simduck.simulator;

/**
 * 현재 브랜치가 나타내는 전략 패턴 실습 단계를 설명한다.
 */
public final class LabStage {
    private LabStage() {
    }

    public static String title() {
        return "시작 단계 - 상속으로 공통 행동 재사용";
    }

    public static String goal() {
        return "현재 요구사항에서는 Duck의 공통 행동을 상속하는 구조가 자연스럽다.";
    }

    public static boolean showFlyButton() {
        return false;
    }

    public static boolean showStructure() {
        return false;
    }

    public static String preferredDuck() {
        return "MallardDuck";
    }
}
