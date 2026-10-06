package edu.kmu.simduck.duck;

import edu.kmu.simduck.behavior.FlyWithWings;
import edu.kmu.simduck.behavior.Quack;

/**
 * 붉은머리오리를 나타낸다.
 */
public final class RedheadDuck extends Duck {

    /**
     * 붉은머리오리에 필요한 비행과 울음 행동 객체를 구성한다.
     */
    public RedheadDuck() {
        flyBehavior = new FlyWithWings();
        quackBehavior = new Quack();
    }

    /**
     * 붉은머리오리의 모습을 출력한다.
     */
    @Override
    public void display() {
        System.out.println("붉은머리오리입니다.");
    }
}
