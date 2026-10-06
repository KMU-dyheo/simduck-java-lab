package edu.kmu.simduck.duck;

/**
 * 모든 오리가 공유하는 상태와 행동을 정의한다.
 *
 * <p>새 비행 요구사항을 가장 단순하게 상위 클래스에 추가한 단계다.</p>
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
     * 모든 오리가 공통으로 수행하는 비행 행동이다.
     */
    public void fly() {
        System.out.println("날고 있습니다.");
    }

    /**
     * 오리 종류마다 다른 모습을 출력한다.
     */
    public abstract void display();
}
