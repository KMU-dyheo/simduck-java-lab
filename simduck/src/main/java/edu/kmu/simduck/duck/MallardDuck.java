package edu.kmu.simduck.duck;

/**
 * 청둥오리를 나타낸다.
 */
public final class MallardDuck extends Duck implements Flyable, Quackable {

    /**
     * 청둥오리의 비행 행동을 수행한다.
     */
    @Override
    public void fly() {
        System.out.println("날고 있습니다.");
    }

    /**
     * 청둥오리의 울음 행동을 수행한다.
     */
    @Override
    public void quack() {
        System.out.println("꽥꽥!");
    }

    /**
     * 청둥오리의 모습을 출력한다.
     */
    @Override
    public void display() {
        System.out.println("청둥오리입니다.");
    }
}
