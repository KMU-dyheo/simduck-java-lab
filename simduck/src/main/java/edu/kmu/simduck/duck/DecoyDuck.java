package edu.kmu.simduck.duck;

/**
 * 사냥용 유인 오리를 나타낸다.
 *
 * <p>유인 오리는 날지도 울지도 않으므로 비행과 울음 인터페이스를 구현하지 않는다.</p>
 */
public final class DecoyDuck extends Duck {

    /**
     * 유인 오리의 모습을 출력한다.
     */
    @Override
    public void display() {
        System.out.println("유인 오리입니다.");
    }
}
