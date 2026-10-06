package edu.kmu.simduck.duck;

import edu.kmu.simduck.behavior.FlyBehavior;
import edu.kmu.simduck.behavior.QuackBehavior;

/**
 * 모든 오리가 공유하는 상태와 행동 위임을 정의한다.
 *
 * <p>변하는 비행과 울음 행동을 직접 구현하지 않고 행동 객체에 위임한다.</p>
 */
public abstract class Duck {
    protected FlyBehavior flyBehavior;
    protected QuackBehavior quackBehavior;

    /**
     * 현재 비행 행동 객체에 비행을 위임한다.
     */
    public void performFly() {
        flyBehavior.fly();
    }

    /**
     * 현재 울음 행동 객체에 울음을 위임한다.
     */
    public void performQuack() {
        quackBehavior.quack();
    }

    /**
     * 모든 오리가 공통으로 수행하는 수영 행동이다.
     */
    public void swim() {
        System.out.println("모든 오리는 물에 뜹니다.");
    }

    /**
     * 오리 종류마다 다른 모습을 출력한다.
     */
    public abstract void display();
}
