package edu.kmu.simduck.duck;

/**
 * 붉은머리오리를 나타낸다.
 */
public final class RedheadDuck extends Duck implements Flyable, Quackable {

    /**
     * 붉은머리오리의 비행 행동을 수행한다.
     */
    @Override
    public void fly() {
        System.out.println("날고 있습니다.");
    }

    /**
     * 붉은머리오리의 울음 행동을 수행한다.
     */
    @Override
    public void quack() {
        System.out.println("꽥꽥!");
    }

    /**
     * 붉은머리오리의 모습을 출력한다.
     */
    @Override
    public void display() {
        System.out.println("붉은머리오리입니다.");
    }
}
