package edu.kmu.simduck.duck;

import edu.kmu.simduck.behavior.FlyWithWings;
import edu.kmu.simduck.behavior.Quack;

/**
 * 청둥오리를 나타낸다.
 */
public final class MallardDuck extends Duck {
    public MallardDuck() {
        flyBehavior = new FlyWithWings();
        quackBehavior = new Quack();
    }

    @Override
    public void display() {
        System.out.println("청둥오리입니다.");
    }
}
