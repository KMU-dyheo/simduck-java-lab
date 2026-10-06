package edu.kmu.simduck.simulator;

/**
 * 현재 브랜치가 나타내는 전략 패턴 실습 단계를 설명한다.
 */
public final class LabStage {
    private LabStage() {
    }

    public static String title() {
        return "4단계 - Flyable과 Quackable 분리";
    }

    public static String goal() {
        return "필요한 오리만 행동 인터페이스를 구현하지만 같은 행동 코드가 여러 클래스에 중복된다.";
    }

    public static boolean showFlyButton() {
        return true;
    }

    public static boolean showStructure() {
        return true;
    }

    public static String preferredDuck() {
        return "MallardDuck";
    }
}
