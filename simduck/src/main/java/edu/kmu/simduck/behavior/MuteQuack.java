package edu.kmu.simduck.behavior;

/**
 * 소리를 내지 않는 울음 행동을 구현한다.
 */
public final class MuteQuack implements QuackBehavior {

    /**
     * 아무 소리도 내지 않는다.
     */
    @Override
    public void quack() {
        // 소리를 내지 않는다.
    }
}
