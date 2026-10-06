package edu.kmu.simduck.simulator;

/**
 * 현재 브랜치가 나타내는 전략 패턴 실습 단계를 설명한다.
 */
public final class LabStage {
    private LabStage() {
    }

    public static String title() {
        return "1단계 - Duck에 fly() 추가";
    }

    public static String goal() {
        return "비행 요구사항을 Duck에 넣으면 모든 하위 오리가 자동으로 fly()를 상속한다.";
    }

    public static boolean showFlyButton() {
        return true;
    }

    public static boolean showStructure() {
        return false;
    }

    public static String preferredDuck() {
        return "MallardDuck";
    }
}
