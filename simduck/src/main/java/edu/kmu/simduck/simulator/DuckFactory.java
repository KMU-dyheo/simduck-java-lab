package edu.kmu.simduck.simulator;

import edu.kmu.simduck.duck.Duck;
import edu.kmu.simduck.duck.MallardDuck;
import edu.kmu.simduck.duck.RedheadDuck;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 현재 실습 단계에서 선택할 수 있는 오리 객체와 화면용 모습을 제공한다.
 */
public final class DuckFactory {
    private static final Map<String, String> SKINS = new LinkedHashMap<>();

    static {
        SKINS.put("MallardDuck", "mallard");
        SKINS.put("RedheadDuck", "redhead");
    }

    private DuckFactory() {
    }

    /**
     * 선택 가능한 오리 이름을 반환한다.
     *
     * @return 오리 이름 배열
     */
    public static String[] names() {
        return SKINS.keySet().toArray(String[]::new);
    }

    /**
     * 이름에 해당하는 새 오리 객체를 만든다.
     *
     * @param name 오리 이름
     * @return 새 오리 객체
     */
    public static Duck create(String name) {
        return switch (name) {
            case "RedheadDuck" -> new RedheadDuck();
            default -> new MallardDuck();
        };
    }

    /**
     * 오리 이름에 맞는 화면용 모습을 반환한다.
     *
     * @param name 오리 이름
     * @return 화면용 모습 이름
     */
    public static String skin(String name) {
        return SKINS.getOrDefault(name, "mallard");
    }
}
