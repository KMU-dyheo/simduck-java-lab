package edu.kmu.simduck.duck;

import edu.kmu.simduck.behavior.FlyNoWay;
import edu.kmu.simduck.behavior.MuteQuack;

/**
 * 사냥용 유인 오리를 나타낸다.
 */
public final class DecoyDuck extends Duck {

    /**
     * 유인 오리에 필요한 비행과 울음 행동 객체를 구성한다.
     */
    public DecoyDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new MuteQuack();
    }

    /**
     * 유인 오리의 모습을 출력한다.
     */
    @Override
    public void display() {
        System.out.println("유인 오리입니다.");
    }
}
