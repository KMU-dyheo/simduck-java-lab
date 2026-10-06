package edu.kmu.simduck.behavior;

/**
 * 고무 오리의 삑삑 소리를 구현한다.
 */
public final class Squeak implements QuackBehavior {
    @Override
    public void quack() {
        System.out.println("삑삑!");
    }
}
