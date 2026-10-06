package edu.kmu.simduck.simulator;

/**
 * 현재 브랜치가 나타내는 전략 패턴 실습 단계를 설명한다.
 */
public final class LabStage {
    private LabStage() {
    }

    public static String title() {
        return "3단계 - 맞지 않는 행동을 재정의";
    }

    public static String goal() {
        return "잘못 상속된 행동을 재정의하면 동작은 고칠 수 있지만 하위 클래스마다 예외 처리가 늘어난다.";
    }

    public static boolean showFlyButton() {
        return true;
    }

    public static boolean showStructure() {
        return false;
    }

    public static String preferredDuck() {
        return "DecoyDuck";
    }
}
