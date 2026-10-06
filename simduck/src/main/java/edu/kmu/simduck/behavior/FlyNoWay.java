package edu.kmu.simduck.behavior;

/**
 * 비행할 수 없는 행동을 구현한다.
 */
public final class FlyNoWay implements FlyBehavior {

    /**
     * 비행할 수 없음을 출력한다.
     */
    @Override
    public void fly() {
        System.out.println("날 수 없습니다.");
    }
}
