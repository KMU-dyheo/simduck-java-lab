package edu.kmu.simduck.duck;

import edu.kmu.simduck.behavior.FlyWithWings;
import edu.kmu.simduck.behavior.Quack;

/**
 * 청둥오리를 나타낸다.
 */
public final class MallardDuck extends Duck {

    /**
     * 청둥오리에 필요한 비행과 울음 행동 객체를 구성한다.
     */
    public MallardDuck() {
        flyBehavior = new FlyWithWings();
        quackBehavior = new Quack();
    }

    /**
     * 청둥오리의 모습을 출력한다.
     */
    @Override
    public void display() {
        System.out.println("청둥오리입니다.");
    }
}
