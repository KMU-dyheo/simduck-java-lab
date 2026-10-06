package edu.kmu.simduck.demo;

import edu.kmu.simduck.duck.DecoyDuck;
import edu.kmu.simduck.duck.Duck;
import edu.kmu.simduck.duck.RubberDuck;

/**
 * 맞지 않는 상속 행동을 재정의하는 해결 방법을 확인한다.
 */
public final class MiniDuckSimulator {
    private MiniDuckSimulator() {
    }

    /**
     * 고무 오리와 유인 오리에서 비행과 울음 행동을 재정의한 결과를 실행한다.
     *
     * @param args 사용하지 않는 명령행 인자
     */
    public static void main(String[] args) {
        Duck rubber = new RubberDuck();
        rubber.display();
        rubber.quack();
        rubber.fly();

        Duck decoy = new DecoyDuck();
        decoy.display();
        decoy.quack();
        decoy.fly();
    }
}
