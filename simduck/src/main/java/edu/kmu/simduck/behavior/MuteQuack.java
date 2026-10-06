package edu.kmu.simduck.behavior;

/**
 * 소리를 내지 않는 울음 행동을 구현한다.
 */
public final class MuteQuack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("조용합니다.");
    }
}
