package edu.kmu.simduck.simulator;

import edu.kmu.simduck.behavior.FlyBehavior;
import edu.kmu.simduck.behavior.FlyNoWay;
import edu.kmu.simduck.behavior.FlyRocketPowered;
import edu.kmu.simduck.behavior.FlyWithWings;
import edu.kmu.simduck.behavior.MuteQuack;
import edu.kmu.simduck.behavior.Quack;
import edu.kmu.simduck.behavior.QuackBehavior;
import edu.kmu.simduck.behavior.Squeak;

/**
 * 화면에서 선택한 전략 이름을 실제 전략 객체로 바꾼다.
 */
public final class BehaviorFactory {
    private BehaviorFactory() {
    }

    /**
     * 선택 가능한 비행 전략 이름을 반환한다.
     *
     * @return 비행 전략 이름 배열
     */
    public static String[] flyNames() {
        return new String[]{"FlyWithWings", "FlyNoWay", "FlyRocketPowered"};
    }

    /**
     * 선택 가능한 울음 전략 이름을 반환한다.
     *
     * @return 울음 전략 이름 배열
     */
    public static String[] quackNames() {
        return new String[]{"Quack", "Squeak", "MuteQuack"};
    }

    /**
     * 이름에 맞는 비행 전략을 만든다.
     *
     * @param name 전략 이름
     * @return 비행 전략
     */
    public static FlyBehavior fly(String name) {
        return switch (name) {
            case "FlyNoWay" -> new FlyNoWay();
            case "FlyRocketPowered" -> new FlyRocketPowered();
            default -> new FlyWithWings();
        };
    }

    /**
     * 이름에 맞는 울음 전략을 만든다.
     *
     * @param name 전략 이름
     * @return 울음 전략
     */
    public static QuackBehavior quack(String name) {
        return switch (name) {
            case "Squeak" -> new Squeak();
            case "MuteQuack" -> new MuteQuack();
            default -> new Quack();
        };
    }
}
