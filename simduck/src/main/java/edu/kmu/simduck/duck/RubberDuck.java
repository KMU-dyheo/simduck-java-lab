package edu.kmu.simduck.duck;

/**
 * 고무 오리를 나타낸다.
 *
 * <p>상속받은 비행 행동이 맞지 않아 fly 메서드를 재정의한다.</p>
 */
public final class RubberDuck extends Duck {

    /**
     * 고무 오리의 울음소리를 출력한다.
     */
    @Override
    public void quack() {
        System.out.println("삑삑!");
    }

    /**
     * 고무 오리는 날 수 없으므로 아무 비행 행동도 하지 않는다.
     */
    @Override
    public void fly() {
        // 고무 오리는 날지 않는다.
    }

    /**
     * 고무 오리의 모습을 출력한다.
     */
    @Override
    public void display() {
        System.out.println("고무 오리입니다.");
    }
}
