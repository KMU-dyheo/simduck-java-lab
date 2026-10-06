package edu.kmu.simduck.duck;

import edu.kmu.simduck.behavior.FlyNoWay;
import edu.kmu.simduck.behavior.MuteQuack;

/**
 * 유인용 오리를 나타낸다.
 */
public final class DecoyDuck extends Duck {
    public DecoyDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new MuteQuack();
    }

    @Override
    public void display() {
        System.out.println("유인용 오리입니다.");
    }
}
