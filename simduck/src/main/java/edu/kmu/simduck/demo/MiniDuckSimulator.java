package edu.kmu.simduck.demo;

import edu.kmu.simduck.duck.MallardDuck;
import edu.kmu.simduck.duck.RubberDuck;

/**
 * 비행과 울음을 기능 인터페이스로 분리한 결과를 확인한다.
 */
public final class MiniDuckSimulator {
    private MiniDuckSimulator() {
    }

    /**
     * 청둥오리와 고무 오리의 서로 다른 기능 구성을 실행한다.
     *
     * @param args 사용하지 않는 명령행 인자
     */
    public static void main(String[] args) {
        MallardDuck mallard = new MallardDuck();
        mallard.display();
        mallard.fly();
        mallard.quack();

        RubberDuck rubber = new RubberDuck();
        rubber.display();
        rubber.quack();
    }
}
