package edu.kmu.simduck.duck;

import edu.kmu.simduck.behavior.FlyNoWay;
import edu.kmu.simduck.behavior.Squeak;

/**
 * 고무 오리를 나타낸다.
 */
public final class RubberDuck extends Duck {

    /**
     * 고무 오리에 필요한 비행과 울음 행동 객체를 구성한다.
     */
    public RubberDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new Squeak();
    }

    /**
     * 고무 오리의 모습을 출력한다.
     */
    @Override
    public void display() {
        System.out.println("고무 오리입니다.");
    }
}
