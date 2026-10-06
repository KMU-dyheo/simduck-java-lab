package edu.kmu.simduck.demo;

import edu.kmu.simduck.behavior.FlyRocketPowered;
import edu.kmu.simduck.duck.Duck;
import edu.kmu.simduck.duck.MallardDuck;
import edu.kmu.simduck.duck.ModelDuck;

/**
 * 교재의 핵심 전략 교체 흐름을 콘솔에서 확인한다.
 */
public final class MiniDuckSimulator {
    private MiniDuckSimulator() {
    }

    /**
     * 청둥오리의 기본 행동과 모형 오리의 실행 중 전략 교체를 실행한다.
     *
     * @param args 사용하지 않는 명령행 인자
     */
    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        mallard.performQuack();
        mallard.performFly();

        Duck model = new ModelDuck();
        model.performFly();
        model.setFlyBehavior(new FlyRocketPowered());
        model.performFly();
    }
}
