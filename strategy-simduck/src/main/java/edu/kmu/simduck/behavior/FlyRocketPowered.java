package edu.kmu.simduck.behavior;

/**
 * 로켓 추진 비행 행동을 구현한다.
 */
public final class FlyRocketPowered implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("로켓 추진으로 날아갑니다.");
    }
}
