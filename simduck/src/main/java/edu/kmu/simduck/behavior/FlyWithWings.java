package edu.kmu.simduck.behavior;

/**
 * 날개를 사용해 실제로 비행하는 행동을 구현한다.
 */
public final class FlyWithWings implements FlyBehavior {

    /**
     * 날개 비행 행동을 수행한다.
     */
    @Override
    public void fly() {
        System.out.println("날고 있습니다.");
    }
}
