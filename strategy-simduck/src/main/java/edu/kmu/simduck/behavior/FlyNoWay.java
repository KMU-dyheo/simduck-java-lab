package edu.kmu.simduck.behavior;

/**
 * 날 수 없는 행동을 구현한다.
 */
public final class FlyNoWay implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("저는 날 수 없어요.");
    }
}
