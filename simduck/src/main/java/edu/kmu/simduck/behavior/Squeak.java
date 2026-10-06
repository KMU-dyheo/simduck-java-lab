package edu.kmu.simduck.behavior;

/**
 * 고무 오리의 삑삑 소리를 구현한다.
 */
public final class Squeak implements QuackBehavior {

    /**
     * 고무 오리의 울음소리를 출력한다.
     */
    @Override
    public void quack() {
        System.out.println("삑삑!");
    }
}
