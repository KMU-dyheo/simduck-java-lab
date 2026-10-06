package edu.kmu.simduck.simulator;

import edu.kmu.simduck.duck.Duck;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Duck 하위 클래스를 자동으로 찾아 시뮬레이터에 연결한다.
 *
 * <p>학생은 새로운 Duck을 만들 때 스윙 코드를 수정하거나 별도로 등록할 필요가 없다.</p>
 */
public final class DuckFactory {
    private static final List<String> PREFERRED_ORDER = List.of(
            "MallardDuck", "RedheadDuck", "RubberDuck", "DecoyDuck", "ModelDuck");

    private static final Map<String, String> SKINS = Map.of(
            "MallardDuck", "mallard",
            "RedheadDuck", "redhead",
            "RubberDuck", "rubber",
            "DecoyDuck", "decoy",
            "ModelDuck", "model");

    private static final Map<String, Class<? extends Duck>> DUCK_TYPES = discover();

    private DuckFactory() {
    }

    /**
     * 자동 발견된 오리 이름을 반환한다.
     *
     * @return 오리 이름 배열
     */
    public static String[] names() {
        return DUCK_TYPES.keySet().toArray(String[]::new);
    }

    /**
     * 이름에 해당하는 새 오리 객체를 만든다.
     *
     * @param name 오리 이름
     * @return 새 오리 객체
     */
    public static Duck create(String name) {
        Class<? extends Duck> type = DUCK_TYPES.get(name);
        if (type == null) {
            throw new IllegalArgumentException("알 수 없는 오리입니다: " + name);
        }

        try {
            return type.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                 | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException(name + " 객체를 만들 수 없습니다.", e);
        }
    }

    /**
     * 기존 오리에는 기존 외형을 연결하고, 새로 만든 오리에는 기본 외형을 사용한다.
     *
     * @param name 오리 이름
     * @return 화면용 외형 이름
     */
    public static String skin(String name) {
        return SKINS.getOrDefault(name, "mallard");
    }

    private static Map<String, Class<? extends Duck>> discover() {
        List<Class<? extends Duck>> discovered =
                new ArrayList<>(ClassDiscovery.concreteSubtypes(Duck.class, Duck.class.getPackageName()));

        discovered.sort(Comparator
                .comparingInt((Class<? extends Duck> type) -> orderOf(type.getSimpleName()))
                .thenComparing(Class::getSimpleName));

        Map<String, Class<? extends Duck>> result = new LinkedHashMap<>();
        for (Class<? extends Duck> type : discovered) {
            result.put(type.getSimpleName(), type);
        }
        return Map.copyOf(result);
    }

    private static int orderOf(String name) {
        int index = PREFERRED_ORDER.indexOf(name);
        return index >= 0 ? index : PREFERRED_ORDER.size();
    }
}
