package edu.kmu.simduck.duck;

import edu.kmu.simduck.behavior.FlyNoWay;
import edu.kmu.simduck.behavior.Squeak;

/**
 * 고무 오리를 나타낸다.
 */
public final class RubberDuck extends Duck {
    public RubberDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new Squeak();
    }

    @Override
    public void display() {
        System.out.println("고무 오리입니다.");
    }
}
