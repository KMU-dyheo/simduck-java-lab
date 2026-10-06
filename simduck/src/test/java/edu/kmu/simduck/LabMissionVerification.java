package edu.kmu.simduck;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 전략 패턴 실습의 단계별 목표가 코드 구조와 실행 결과에 반영되었는지 검사한다.
 *
 * <p>단계마다 아직 존재하지 않는 클래스를 직접 참조하지 않도록 리플렉션을 사용한다.</p>
 */
public final class LabMissionVerification {
    private static final String DUCK = "edu.kmu.simduck.duck.";
    private static final String BEHAVIOR = "edu.kmu.simduck.behavior.";
    private static final String SIMULATOR = "edu.kmu.simduck.simulator.";

    private LabMissionVerification() {
    }

    /**
     * 전달받은 단계 번호에 맞는 검증을 실행한다.
     *
     * @param args 첫 번째 인자는 00부터 06까지의 단계 번호다.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("검증 단계가 필요합니다. 예: 03");
        }

        String stage = args[0];
        try {
            switch (stage) {
                case "00" -> verifyStart();
                case "01" -> verifyFly();
                case "02" -> verifyRubber();
                case "03" -> verifyOverride();
                case "04" -> verifyInterface();
                case "05" -> verifyStrategy();
                case "06" -> verifyComplete();
                default -> throw new IllegalArgumentException("알 수 없는 검증 단계입니다: " + stage);
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
            System.err.println("단계 힌트: " + hint(stage));
            throw e;
        }

        System.out.println("실습 " + stage + "단계 목표 검증을 통과했습니다.");
    }

    private static void verifyStart() {
        requireDeclaredMethod(DUCK + "Duck", "quack");
        requireDeclaredMethod(DUCK + "Duck", "swim");
        requireAbstractDeclaredMethod(DUCK + "Duck", "display");
        requireNoPublicMethod(DUCK + "Duck", "fly");
        requireNoPublicMethod(DUCK + "Duck", "performFly");
        requireClass(DUCK + "MallardDuck");
        requireClass(DUCK + "RedheadDuck");
        requireNoClass(DUCK + "RubberDuck");
        requireNoClass(DUCK + "DecoyDuck");
        requireNoClass(BEHAVIOR + "FlyBehavior");
    }

    private static void verifyFly() {
        requireDeclaredMethod(DUCK + "Duck", "fly");
        requireMethodDeclaredBy(DUCK + "MallardDuck", "fly", DUCK + "Duck");
        requireMethodDeclaredBy(DUCK + "RedheadDuck", "fly", DUCK + "Duck");
        requireContains(invokeNew(DUCK + "MallardDuck", "fly"), "날고 있습니다",
                "MallardDuck은 Duck.fly()로 비행해야 합니다.");
        requireNoClass(DUCK + "RubberDuck");
    }

    private static void verifyRubber() {
        requireClass(DUCK + "RubberDuck");
        requireSuperclass(DUCK + "RubberDuck", DUCK + "Duck");
        requireMethodDeclaredBy(DUCK + "RubberDuck", "fly", DUCK + "Duck");
        requireMethodDeclaredBy(DUCK + "RubberDuck", "quack", DUCK + "RubberDuck");
        requireContains(invokeNew(DUCK + "RubberDuck", "fly"), "날고 있습니다",
                "이 단계에서는 RubberDuck이 잘못 날아가야 문제를 확인할 수 있습니다.");
        requireContains(invokeNew(DUCK + "RubberDuck", "quack"), "삑삑",
                "RubberDuck의 울음은 삑삑이어야 합니다.");
        requireFactoryContains("RubberDuck");
    }

    private static void verifyOverride() {
        requireMethodDeclaredBy(DUCK + "RubberDuck", "fly", DUCK + "RubberDuck");
        requireBlank(invokeNew(DUCK + "RubberDuck", "fly"),
                "RubberDuck.fly()는 비행을 막기 위해 아무 동작도 하지 않아야 합니다.");

        requireClass(DUCK + "DecoyDuck");
        requireMethodDeclaredBy(DUCK + "DecoyDuck", "fly", DUCK + "DecoyDuck");
        requireMethodDeclaredBy(DUCK + "DecoyDuck", "quack", DUCK + "DecoyDuck");
        requireBlank(invokeNew(DUCK + "DecoyDuck", "fly"),
                "DecoyDuck.fly()는 아무 동작도 하지 않아야 합니다.");
        requireBlank(invokeNew(DUCK + "DecoyDuck", "quack"),
                "DecoyDuck.quack()은 아무 소리도 내지 않아야 합니다.");
        requireFactoryContains("DecoyDuck");
    }

    private static void verifyInterface() {
        requireNoPublicMethod(DUCK + "Duck", "fly");
        requireNoPublicMethod(DUCK + "Duck", "quack");

        requireInterface(DUCK + "Flyable");
        requireInterface(DUCK + "Quackable");
        requireDeclaredMethod(DUCK + "Flyable", "fly");
        requireDeclaredMethod(DUCK + "Quackable", "quack");

        requireImplements(DUCK + "MallardDuck", DUCK + "Flyable");
        requireImplements(DUCK + "MallardDuck", DUCK + "Quackable");
        requireImplements(DUCK + "RedheadDuck", DUCK + "Flyable");
        requireImplements(DUCK + "RedheadDuck", DUCK + "Quackable");
        requireImplements(DUCK + "RubberDuck", DUCK + "Quackable");
        requireNotImplements(DUCK + "RubberDuck", DUCK + "Flyable");
        requireNotImplements(DUCK + "DecoyDuck", DUCK + "Flyable");
        requireNotImplements(DUCK + "DecoyDuck", DUCK + "Quackable");

        requireMethodDeclaredBy(DUCK + "MallardDuck", "fly", DUCK + "MallardDuck");
        requireMethodDeclaredBy(DUCK + "RedheadDuck", "fly", DUCK + "RedheadDuck");
        requireNoPublicMethod(DUCK + "RubberDuck", "fly");
    }

    private static void verifyStrategy() {
        requireNoClass(DUCK + "Flyable");
        requireNoClass(DUCK + "Quackable");

        requireInterface(BEHAVIOR + "FlyBehavior");
        requireInterface(BEHAVIOR + "QuackBehavior");
        requireImplements(BEHAVIOR + "FlyWithWings", BEHAVIOR + "FlyBehavior");
        requireImplements(BEHAVIOR + "FlyNoWay", BEHAVIOR + "FlyBehavior");
        requireImplements(BEHAVIOR + "Quack", BEHAVIOR + "QuackBehavior");
        requireImplements(BEHAVIOR + "Squeak", BEHAVIOR + "QuackBehavior");
        requireImplements(BEHAVIOR + "MuteQuack", BEHAVIOR + "QuackBehavior");

        requireFieldType(DUCK + "Duck", "flyBehavior", BEHAVIOR + "FlyBehavior");
        requireFieldType(DUCK + "Duck", "quackBehavior", BEHAVIOR + "QuackBehavior");
        requireDeclaredMethod(DUCK + "Duck", "performFly");
        requireDeclaredMethod(DUCK + "Duck", "performQuack");
        requireNoPublicMethod(DUCK + "Duck", "setFlyBehavior");
        requireNoPublicMethod(DUCK + "Duck", "setQuackBehavior");

        requireBehavior(DUCK + "MallardDuck", "flyBehavior", "FlyWithWings");
        requireBehavior(DUCK + "MallardDuck", "quackBehavior", "Quack");
        requireBehavior(DUCK + "RedheadDuck", "flyBehavior", "FlyWithWings");
        requireBehavior(DUCK + "RubberDuck", "flyBehavior", "FlyNoWay");
        requireBehavior(DUCK + "RubberDuck", "quackBehavior", "Squeak");
        requireBehavior(DUCK + "DecoyDuck", "flyBehavior", "FlyNoWay");
        requireBehavior(DUCK + "DecoyDuck", "quackBehavior", "MuteQuack");

        requireContains(invokeNew(DUCK + "MallardDuck", "performFly"), "날고 있습니다",
                "MallardDuck은 FlyWithWings에 비행을 위임해야 합니다.");
        requireContains(invokeNew(DUCK + "RubberDuck", "performFly"), "날 수 없",
                "RubberDuck은 FlyNoWay에 비행을 위임해야 합니다.");

        requireNoClass(DUCK + "ModelDuck");
        requireNoClass(BEHAVIOR + "FlyRocketPowered");
    }

    private static void verifyComplete() {
        requireInterface(BEHAVIOR + "FlyBehavior");
        requireInterface(BEHAVIOR + "QuackBehavior");
        requireDeclaredMethod(DUCK + "Duck", "performFly");
        requireDeclaredMethod(DUCK + "Duck", "performQuack");
        requirePublicMethod(DUCK + "Duck", "setFlyBehavior", type(BEHAVIOR + "FlyBehavior"));
        requirePublicMethod(DUCK + "Duck", "setQuackBehavior", type(BEHAVIOR + "QuackBehavior"));

        requireClass(DUCK + "ModelDuck");
        requireClass(BEHAVIOR + "FlyRocketPowered");
        requireImplements(BEHAVIOR + "FlyRocketPowered", BEHAVIOR + "FlyBehavior");
        requireBehavior(DUCK + "ModelDuck", "flyBehavior", "FlyNoWay");

        Object model = newInstance(DUCK + "ModelDuck");
        requireContains(invoke(model, "performFly"), "날 수 없",
                "ModelDuck의 초기 비행 전략은 FlyNoWay여야 합니다.");

        Object rocket = newInstance(BEHAVIOR + "FlyRocketPowered");
        invokeVoid(model, "setFlyBehavior", new Class<?>[]{type(BEHAVIOR + "FlyBehavior")}, new Object[]{rocket});
        requireContains(invoke(model, "performFly"), "로켓",
                "setFlyBehavior()로 FlyRocketPowered를 적용한 뒤 로켓 비행이 실행되어야 합니다.");

        requireFactoryContains("ModelDuck");
        requireBehaviorFactoryContainsRocket();
    }

    private static String hint(String stage) {
        return switch (stage) {
            case "00" -> "아직 fly(), RubberDuck, 행동 객체를 추가하지 않습니다.";
            case "01" -> "fly()는 Duck에 한 번만 추가하고 MallardDuck과 RedheadDuck은 그대로 상속받게 합니다.";
            case "02" -> "RubberDuck을 추가하되 fly()는 재정의하지 않습니다. 고무 오리가 실제로 날아가는 문제를 먼저 경험해야 합니다.";
            case "03" -> "RubberDuck과 DecoyDuck에서 맞지 않는 fly(), quack()을 직접 재정의해 행동을 막습니다.";
            case "04" -> "Duck에서 fly(), quack()을 제거하고 필요한 오리만 Flyable, Quackable을 구현하게 합니다.";
            case "05" -> "Flyable, Quackable 대신 FlyBehavior, QuackBehavior 객체로 행동을 분리하고 Duck이 위임하게 합니다.";
            case "06" -> "ModelDuck의 FlyNoWay를 setFlyBehavior()로 FlyRocketPowered로 교체할 수 있어야 합니다.";
            default -> "Lab.md와 현재 단계 문서를 확인합니다.";
        };
    }

    private static void requireFactoryContains(String name) {
        Object value = invokeStatic(SIMULATOR + "DuckFactory", "names");
        require(value instanceof String[], "DuckFactory.names()는 문자열 배열을 반환해야 합니다.");
        require(Arrays.asList((String[]) value).contains(name),
                "DuckFactory에서 " + name + "을 선택할 수 있어야 합니다.");
    }

    private static void requireBehaviorFactoryContainsRocket() {
        Object value = invokeStatic(SIMULATOR + "BehaviorFactory", "flyNames");
        require(value instanceof String[], "BehaviorFactory.flyNames()는 문자열 배열을 반환해야 합니다.");
        require(Arrays.asList((String[]) value).contains("FlyRocketPowered"),
                "BehaviorFactory에 FlyRocketPowered가 등록되어야 합니다.");
    }

    private static void requireBehavior(String className, String fieldName, String expectedSimpleName) {
        Object instance = newInstance(className);
        Field field = findField(instance.getClass(), fieldName);
        require(field != null, simple(className) + "에서 " + fieldName + " 필드를 찾을 수 없습니다.");
        try {
            field.setAccessible(true);
            Object behavior = field.get(instance);
            require(behavior != null, simple(className) + "의 " + fieldName + "가 설정되어야 합니다.");
            require(behavior.getClass().getSimpleName().equals(expectedSimpleName),
                    simple(className) + "의 " + fieldName + "는 " + expectedSimpleName + "여야 합니다.");
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("행동 필드를 읽을 수 없습니다.", e);
        }
    }

    private static void requireFieldType(String className, String fieldName, String typeName) {
        Field field = findField(type(className), fieldName);
        require(field != null, simple(className) + "에 " + fieldName + " 필드가 필요합니다.");
        require(field.getType().getName().equals(typeName),
                fieldName + "의 타입은 " + simple(typeName) + "여야 합니다.");
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

    private static void requireSuperclass(String className, String superclassName) {
        require(type(className).getSuperclass().getName().equals(superclassName),
                simple(className) + "은 " + simple(superclassName) + "을 상속해야 합니다.");
    }

    private static void requireInterface(String className) {
        requireClass(className);
        require(type(className).isInterface(), simple(className) + "은 인터페이스여야 합니다.");
    }

    private static void requireImplements(String className, String interfaceName) {
        require(Arrays.asList(type(className).getInterfaces()).contains(type(interfaceName)),
                simple(className) + "은 " + simple(interfaceName) + "을 구현해야 합니다.");
    }

    private static void requireNotImplements(String className, String interfaceName) {
        require(!Arrays.asList(type(className).getInterfaces()).contains(type(interfaceName)),
                simple(className) + "은 " + simple(interfaceName) + "을 구현하면 안 됩니다.");
    }

    private static void requireMethodDeclaredBy(String className, String methodName, String declaringClassName) {
        Method method = publicMethod(type(className), methodName);
        require(method != null, simple(className) + "에서 " + methodName + "()을 찾을 수 없습니다.");
        require(method.getDeclaringClass().getName().equals(declaringClassName),
                simple(className) + "." + methodName + "()의 구현 위치는 "
                        + simple(declaringClassName) + "이어야 합니다.");
    }

    private static void requireDeclaredMethod(String className, String methodName) {
        require(declaredMethod(type(className), methodName) != null,
                simple(className) + "에 " + methodName + "()이 직접 선언되어야 합니다.");
    }

    private static void requireAbstractDeclaredMethod(String className, String methodName) {
        Method method = declaredMethod(type(className), methodName);
        require(method != null && Modifier.isAbstract(method.getModifiers()),
                simple(className) + "." + methodName + "()은 추상 메서드여야 합니다.");
    }

    private static void requirePublicMethod(String className, String methodName, Class<?>... parameterTypes) {
        require(publicMethod(type(className), methodName, parameterTypes) != null,
                simple(className) + "에 public " + methodName + "()이 필요합니다.");
    }

    private static void requireNoPublicMethod(String className, String methodName) {
        if (!classExists(className)) {
            return;
        }
        require(publicMethod(type(className), methodName) == null,
                simple(className) + "에는 아직 " + methodName + "()이 있으면 안 됩니다.");
    }

    private static Method declaredMethod(Class<?> type, String name, Class<?>... parameterTypes) {
        try {
            return type.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private static Method publicMethod(Class<?> type, String name, Class<?>... parameterTypes) {
        try {
            return type.getMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private static void requireClass(String className) {
        require(classExists(className), simple(className) + " 클래스가 필요합니다.");
    }

    private static void requireNoClass(String className) {
        require(!classExists(className), simple(className) + "은 아직 존재하면 안 됩니다.");
    }

    private static boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private static Class<?> type(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(simple(className) + " 클래스를 찾을 수 없습니다.", e);
        }
    }

    private static Object newInstance(String className) {
        try {
            return type(className).getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(simple(className) + " 객체를 만들 수 없습니다.", e);
        }
    }

    private static Object invokeStatic(String className, String methodName) {
        try {
            Method method = type(className).getMethod(methodName);
            return method.invoke(null);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(simple(className) + "." + methodName + "()을 실행할 수 없습니다.", e);
        }
    }

    private static String invokeNew(String className, String methodName) {
        return invoke(newInstance(className), methodName);
    }

    private static String invoke(Object target, String methodName) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream original = System.out;
        try (PrintStream replacement = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(replacement);
            target.getClass().getMethod(methodName).invoke(target);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(simple(target.getClass().getName()) + "."
                    + methodName + "()을 실행할 수 없습니다.", e);
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8).trim();
    }

    private static void invokeVoid(Object target, String methodName, Class<?>[] parameterTypes, Object[] values) {
        try {
            target.getClass().getMethod(methodName, parameterTypes).invoke(target, values);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(methodName + "()을 실행할 수 없습니다.", e);
        }
    }

    private static void requireContains(String actual, String expected, String message) {
        require(actual.contains(expected), message + " 실제 출력: " + actual);
    }

    private static void requireBlank(String actual, String message) {
        require(actual.isBlank(), message + " 실제 출력: " + actual);
    }

    private static String simple(String className) {
        int index = className.lastIndexOf('.');
        return index < 0 ? className : className.substring(index + 1);
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException("미션 검증 실패: " + message);
        }
    }
}
