package edu.kmu.simduck.duck;

import edu.kmu.simduck.behavior.FlyWithWings;
import edu.kmu.simduck.behavior.Quack;

/**
 * 붉은머리오리를 나타낸다.
 */
public final class RedheadDuck extends Duck {
    public RedheadDuck() {
        flyBehavior = new FlyWithWings();
        quackBehavior = new Quack();
    }

    @Override
    public void display() {
        System.out.println("붉은머리오리입니다.");
    }
}
