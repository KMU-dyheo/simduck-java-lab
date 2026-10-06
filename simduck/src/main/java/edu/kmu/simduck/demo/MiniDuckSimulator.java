package edu.kmu.simduck.demo;

import edu.kmu.simduck.duck.DecoyDuck;
import edu.kmu.simduck.duck.Duck;
import edu.kmu.simduck.duck.MallardDuck;
import edu.kmu.simduck.duck.RubberDuck;

/**
 * 변하는 행동을 별도 객체로 분리한 결과를 콘솔에서 확인한다.
 */
public final class MiniDuckSimulator {
    private MiniDuckSimulator() {
    }

    /**
     * 서로 다른 오리가 행동 객체를 조합해서 사용하는 모습을 실행한다.
     *
     * @param args 사용하지 않는 명령행 인자
     */
    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        mallard.display();
        mallard.performFly();
        mallard.performQuack();

        Duck rubber = new RubberDuck();
        rubber.display();
        rubber.performFly();
        rubber.performQuack();

        Duck decoy = new DecoyDuck();
        decoy.display();
        decoy.performFly();
        decoy.performQuack();
    }
}
