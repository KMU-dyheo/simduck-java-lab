package edu.kmu.simduck.demo;

import edu.kmu.simduck.duck.Duck;
import edu.kmu.simduck.duck.MallardDuck;
import edu.kmu.simduck.duck.RedheadDuck;

/**
 * Duck에 비행 행동을 추가했을 때 상속되는 모습을 확인한다.
 */
public final class MiniDuckSimulator {
    private MiniDuckSimulator() {
    }

    /**
     * 두 오리가 같은 fly 메서드를 상속받는 것을 실행한다.
     *
     * @param args 사용하지 않는 명령행 인자
     */
    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        mallard.display();
        mallard.fly();

        Duck redhead = new RedheadDuck();
        redhead.display();
        redhead.fly();
    }
}
