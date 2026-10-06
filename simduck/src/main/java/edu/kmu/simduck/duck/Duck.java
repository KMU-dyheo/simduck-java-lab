package edu.kmu.simduck.duck;

/**
 * 모든 오리가 공유하는 기본 상태와 공통 행동을 정의한다.
 *
 * <p>비행과 울음은 모든 오리에 공통이지 않으므로 상위 클래스에서 제거한 단계다.</p>
 */
public abstract class Duck {

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
