package edu.kmu.simduck.duck;

import edu.kmu.simduck.behavior.FlyBehavior;
import edu.kmu.simduck.behavior.QuackBehavior;

/**
 * 모든 오리가 공유하는 상태와 행동 위임을 정의한다.
 */
public abstract class Duck {
    protected FlyBehavior flyBehavior;
    protected QuackBehavior quackBehavior;

    /**
     * 오리의 모습을 설명한다.
     */
    public abstract void display();

    /**
     * 현재 비행 전략에 비행 행동을 위임한다.
     */
    public void performFly() {
        flyBehavior.fly();
    }

    /**
     * 현재 울음 전략에 울음 행동을 위임한다.
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
     * 실행 중에 비행 전략을 교체한다.
     *
     * @param flyBehavior 새로 사용할 비행 전략
     */
    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    /**
     * 실행 중에 울음 전략을 교체한다.
     *
     * @param quackBehavior 새로 사용할 울음 전략
     */
    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }

    /**
     * 현재 비행 전략을 반환한다.
     *
     * @return 현재 비행 전략
     */
    public FlyBehavior getFlyBehavior() {
        return flyBehavior;
    }

    /**
     * 현재 울음 전략을 반환한다.
     *
     * @return 현재 울음 전략
     */
    public QuackBehavior getQuackBehavior() {
        return quackBehavior;
    }
}
