package edu.kmu.simduck.simulator;

import edu.kmu.simduck.duck.Duck;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * 수업용 오리 코드와 스윙 시뮬레이터 사이를 연결한다.
 *
 * <p>실습 단계마다 Duck의 메서드 구조가 달라져도 화면 코드를 다시 작성하지 않도록
 * 리플렉션으로 현재 구현을 확인한다.</p>
 */
public final class SimulatorBridge {
    private SimulatorBridge() {
    }

    /**
     * 비행 실행 결과를 나타낸다.
     *
     * @param supported 비행 메서드 존재 여부
     * @param canFly 실제 비행 가능 여부
     * @param log 콘솔 출력
     */
    public record FlyResult(boolean supported, boolean canFly, String log) {
    }

    /**
     * 울음 실행 결과를 나타낸다.
     *
     * @param supported 울음 메서드 존재 여부
     * @param speech 화면에 표시할 울음소리
     * @param log 콘솔 출력
     */
    public record QuackResult(boolean supported, String speech, String log) {
    }

    /**
     * 현재 오리의 display 메서드를 실행한다.
     *
     * @param duck 실행할 오리
     * @return 콘솔 출력
     */
    public static String display(Duck duck) {
        return invokeText(duck, "display");
    }

    /**
     * 현재 오리의 swim 메서드를 실행한다.
     *
     * @param duck 실행할 오리
     * @return 콘솔 출력
     */
    public static String swim(Duck duck) {
        return invokeText(duck, "swim");
    }

    /**
     * 현재 단계에서 사용할 수 있는 비행 메서드를 찾아 실행한다.
     *
     * @param duck 실행할 오리
     * @return 비행 실행 결과
     */
    public static FlyResult fly(Duck duck) {
        Method method = findMethod(duck, "performFly", "fly");
        if (method == null) {
            return new FlyResult(false, false, "아직 비행 기능이 구현되지 않았습니다.");
        }

        String log = invokeText(duck, method);
        String behavior = behaviorName(duck, "flyBehavior");
        boolean canFly = !log.isBlank();

        if (behavior != null) {
            canFly = !"FlyNoWay".equals(behavior);
        }
        if (log.contains("날 수 없습니다") || log.contains("날지 못")) {
            canFly = false;
        }
        return new FlyResult(true, canFly, log);
    }

    /**
     * 현재 단계에서 사용할 수 있는 울음 메서드를 찾아 실행한다.
     *
     * @param duck 실행할 오리
     * @return 울음 실행 결과
     */
    public static QuackResult quack(Duck duck) {
        Method method = findMethod(duck, "performQuack", "quack");
        if (method == null) {
            return new QuackResult(false, "...", "울음 기능이 구현되지 않았습니다.");
        }

        String log = invokeText(duck, method);
        String speech;
        if (log.isBlank()) {
            speech = "...";
        } else if (log.contains("삑")) {
            speech = "삑삑!";
        } else {
            speech = "꽥꽥!";
        }
        return new QuackResult(true, speech, log);
    }

    /**
     * 현재 비행 구조를 화면 설명용 문자열로 반환한다.
     *
     * @param duck 확인할 오리
     * @return 비행 구조 설명
     */
    public static String describeFly(Duck duck) {
        String behavior = behaviorName(duck, "flyBehavior");
        if (behavior != null) {
            return behavior;
        }

        Method method = findMethod(duck, "fly");
        if (method == null) {
            return "비행 기능 없음";
        }
        if (implementsInterface(duck, "Flyable")) {
            return "Flyable 구현";
        }
        if (method.getDeclaringClass() == Duck.class) {
            return "Duck.fly() 상속";
        }
        return "fly() 재정의";
    }

    /**
     * 현재 울음 구조를 화면 설명용 문자열로 반환한다.
     *
     * @param duck 확인할 오리
     * @return 울음 구조 설명
     */
    public static String describeQuack(Duck duck) {
        String behavior = behaviorName(duck, "quackBehavior");
        if (behavior != null) {
            return behavior;
        }

        Method method = findMethod(duck, "quack");
        if (method == null) {
            return "울음 기능 없음";
        }
        if (implementsInterface(duck, "Quackable")) {
            return "Quackable 구현";
        }
        if (method.getDeclaringClass() == Duck.class) {
            return "Duck.quack() 상속";
        }
        return "quack() 재정의";
    }

    private static String invokeText(Duck duck, String methodName) {
        Method method = findMethod(duck, methodName);
        if (method == null) {
            return "";
        }
        return invokeText(duck, method);
    }

    private static String invokeText(Duck duck, Method method) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream original = System.out;
        try (PrintStream replacement = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(replacement);
            method.invoke(duck);
        } catch (IllegalAccessException | InvocationTargetException e) {
            Throwable cause = e instanceof InvocationTargetException && e.getCause() != null
                    ? e.getCause() : e;
            throw new IllegalStateException("오리 행동을 실행할 수 없습니다.", cause);
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8).trim();
    }

    private static Method findMethod(Duck duck, String... names) {
        for (String name : names) {
            try {
                return duck.getClass().getMethod(name);
            } catch (NoSuchMethodException ignored) {
                // 현재 실습 단계에 없는 메서드는 다음 후보를 확인한다.
            }
        }
        return null;
    }

    private static String behaviorName(Duck duck, String fieldName) {
        Field field = findField(duck.getClass(), fieldName);
        if (field == null) {
            return null;
        }
        try {
            field.setAccessible(true);
            Object behavior = field.get(duck);
            return behavior == null ? null : behavior.getClass().getSimpleName();
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private static Field findField(Class<?> type, String fieldName) {
        Class<?> current = type;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            }
        }
        return null;
    }

    private static boolean implementsInterface(Duck duck, String simpleName) {
        for (Class<?> type : duck.getClass().getInterfaces()) {
            if (type.getSimpleName().equals(simpleName)) {
                return true;
            }
        }
        return false;
    }
}
