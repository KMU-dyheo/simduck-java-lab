package edu.kmu.simduck.behavior;

/**
 * 일반적인 오리 울음 행동을 구현한다.
 */
public final class Quack implements QuackBehavior {

    /**
     * 일반적인 오리 울음소리를 출력한다.
     */
    @Override
    public void quack() {
        System.out.println("꽥꽥!");
    }
}
