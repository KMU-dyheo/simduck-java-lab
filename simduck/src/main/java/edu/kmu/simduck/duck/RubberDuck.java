package edu.kmu.simduck.duck;

/**
 * 고무 오리를 나타낸다.
 *
 * <p>고무 오리는 울음 기능만 가지므로 Quackable만 구현한다.</p>
 */
public final class RubberDuck extends Duck implements Quackable {

    /**
     * 고무 오리의 울음소리를 출력한다.
     */
    @Override
    public void quack() {
        System.out.println("삑삑!");
    }

    /**
     * 고무 오리의 모습을 출력한다.
     */
    @Override
    public void display() {
        System.out.println("고무 오리입니다.");
    }
}
