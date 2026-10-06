package edu.kmu.simduck.simulator;

import edu.kmu.simduck.behavior.FlyBehavior;
import edu.kmu.simduck.behavior.QuackBehavior;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 행동 구현 클래스를 자동으로 찾아 화면 선택값과 실제 전략 객체를 연결한다.
 */
public final class BehaviorFactory {
    private static final Map<String, Class<? extends FlyBehavior>> FLY_TYPES =
            discover(FlyBehavior.class, List.of("FlyWithWings", "FlyNoWay", "FlyRocketPowered"));

    private static final Map<String, Class<? extends QuackBehavior>> QUACK_TYPES =
            discover(QuackBehavior.class, List.of("Quack", "Squeak", "MuteQuack"));

    private BehaviorFactory() {
    }

    /**
     * 자동 발견된 비행 전략 이름을 반환한다.
     *
     * @return 비행 전략 이름 배열
     */
    public static String[] flyNames() {
        return FLY_TYPES.keySet().toArray(String[]::new);
    }

    /**
     * 자동 발견된 울음 전략 이름을 반환한다.
     *
     * @return 울음 전략 이름 배열
     */
    public static String[] quackNames() {
        return QUACK_TYPES.keySet().toArray(String[]::new);
    }

    /**
     * 이름에 맞는 비행 전략을 만든다.
     *
     * @param name 전략 이름
     * @return 비행 전략
     */
    public static FlyBehavior fly(String name) {
        return create(FLY_TYPES, name, "비행");
    }

    /**
     * 이름에 맞는 울음 전략을 만든다.
     *
     * @param name 전략 이름
     * @return 울음 전략
     */
    public static QuackBehavior quack(String name) {
        return create(QUACK_TYPES, name, "울음");
    }

    private static <T> Map<String, Class<? extends T>> discover(Class<T> baseType, List<String> preferredOrder) {
        List<Class<? extends T>> discovered =
                new ArrayList<>(ClassDiscovery.concreteSubtypes(baseType, baseType.getPackageName()));

        discovered.sort(Comparator
                .comparingInt((Class<? extends T> type) -> orderOf(type.getSimpleName(), preferredOrder))
                .thenComparing(Class::getSimpleName));

        Map<String, Class<? extends T>> result = new LinkedHashMap<>();
        for (Class<? extends T> type : discovered) {
            result.put(type.getSimpleName(), type);
        }
        return Map.copyOf(result);
    }

    private static int orderOf(String name, List<String> preferredOrder) {
        int index = preferredOrder.indexOf(name);
        return index >= 0 ? index : preferredOrder.size();
    }

    private static <T> T create(Map<String, Class<? extends T>> types, String name, String kind) {
        Class<? extends T> type = types.get(name);
        if (type == null) {
            throw new IllegalArgumentException("알 수 없는 " + kind + " 전략입니다: " + name);
        }

        try {
            return type.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                 | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException(name + " 전략 객체를 만들 수 없습니다.", e);
        }
    }
}
