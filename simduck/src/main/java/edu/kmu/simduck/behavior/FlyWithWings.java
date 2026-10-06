package edu.kmu.simduck.behavior;

/**
 * 날개로 나는 행동을 구현한다.
 */
public final class FlyWithWings implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("날개로 날아갑니다.");
    }
}
