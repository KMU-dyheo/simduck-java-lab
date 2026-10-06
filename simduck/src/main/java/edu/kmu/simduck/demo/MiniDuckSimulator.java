package edu.kmu.simduck.demo;

import edu.kmu.simduck.duck.Duck;
import edu.kmu.simduck.duck.MallardDuck;
import edu.kmu.simduck.duck.RubberDuck;

/**
 * 고무 오리가 의도하지 않은 비행 행동을 상속하는 문제를 확인한다.
 */
public final class MiniDuckSimulator {
    private MiniDuckSimulator() {
    }

    /**
     * 청둥오리와 고무 오리의 비행 행동을 비교한다.
     *
     * @param args 사용하지 않는 명령행 인자
     */
    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        mallard.display();
        mallard.fly();

        Duck rubber = new RubberDuck();
        rubber.display();
        rubber.quack();
        rubber.fly();
    }
}
