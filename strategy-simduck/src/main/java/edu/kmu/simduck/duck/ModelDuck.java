package edu.kmu.simduck.duck;

import edu.kmu.simduck.behavior.FlyNoWay;
import edu.kmu.simduck.behavior.Quack;

/**
 * 실행 중 전략 교체 실습에 사용하는 모형 오리다.
 */
public final class ModelDuck extends Duck {
    public ModelDuck() {
        flyBehavior = new FlyNoWay();
        quackBehavior = new Quack();
    }

    @Override
    public void display() {
        System.out.println("모형 오리입니다.");
    }
}
