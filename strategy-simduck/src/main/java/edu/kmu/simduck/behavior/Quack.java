package edu.kmu.simduck.behavior;

/**
 * 일반적인 오리 울음 행동을 구현한다.
 */
public final class Quack implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("꽥꽥!");
    }
}
