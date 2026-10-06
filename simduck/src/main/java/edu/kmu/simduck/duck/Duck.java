package edu.kmu.simduck.duck;

/**
 * 모든 오리가 공유하는 가장 기본적인 상태와 행동을 정의한다.
 *
 * <p>전략 패턴을 적용하기 전의 출발점이다.</p>
 */
public abstract class Duck {

    /**
     * 모든 오리가 공통으로 내는 기본 울음소리다.
     */
    public void quack() {
        System.out.println("꽥꽥!");
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
