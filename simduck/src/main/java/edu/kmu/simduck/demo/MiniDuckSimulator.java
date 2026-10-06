package edu.kmu.simduck.demo;

import edu.kmu.simduck.duck.Duck;
import edu.kmu.simduck.duck.MallardDuck;
import edu.kmu.simduck.duck.RedheadDuck;

/**
 * 전략 패턴을 적용하기 전의 기본 상속 구조를 콘솔에서 확인한다.
 */
public final class MiniDuckSimulator {
    private MiniDuckSimulator() {
    }

    /**
     * 두 오리의 공통 행동과 서로 다른 모습을 실행한다.
     *
     * @param args 사용하지 않는 명령행 인자
     */
    public static void main(String[] args) {
        Duck mallard = new MallardDuck();
        mallard.display();
        mallard.quack();
        mallard.swim();

        Duck redhead = new RedheadDuck();
        redhead.display();
        redhead.quack();
        redhead.swim();
    }
}
