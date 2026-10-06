package edu.kmu.simduck.simulator;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 * 시뮬레이터가 실습 코드를 자동으로 찾기 위한 클래스 탐색 도구다.
 *
 * <p>학생이 스윙 코드를 수정하지 않아도 Duck 하위 클래스와 행동 구현을
 * 자동으로 발견하기 위해 사용한다.</p>
 */
final class ClassDiscovery {
    private ClassDiscovery() {
    }

    /**
     * 지정한 패키지에서 기준 타입의 구체 구현 클래스를 찾는다.
     *
     * @param baseType 기준 타입
     * @param packageName 탐색할 패키지
     * @param <T> 기준 타입
     * @return public 기본 생성자를 가진 구체 구현 클래스 목록
     */
    static <T> List<Class<? extends T>> concreteSubtypes(Class<T> baseType, String packageName) {
        List<Class<? extends T>> result = new ArrayList<>();
        for (String className : classNames(packageName)) {
            try {
                Class<?> candidate = Class.forName(className, false, baseType.getClassLoader());
                if (candidate == baseType
                        || !baseType.isAssignableFrom(candidate)
                        || candidate.isInterface()
                        || Modifier.isAbstract(candidate.getModifiers())
                        || !Modifier.isPublic(candidate.getModifiers())) {
                    continue;
                }

                candidate.getConstructor();
                result.add(candidate.asSubclass(baseType));
            } catch (ClassNotFoundException | NoSuchMethodException ignored) {
                // public 기본 생성자가 없는 클래스는 자동 등록 대상에서 제외한다.
            }
        }
        return List.copyOf(result);
    }

    private static Set<String> classNames(String packageName) {
        String packagePath = packageName.replace('.', '/');
        Set<String> names = new LinkedHashSet<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = ClassDiscovery.class.getClassLoader();
        }

        try {
            Enumeration<URL> resources = loader.getResources(packagePath);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                if ("file".equals(url.getProtocol())) {
                    collectFileClasses(url, packageName, names);
                } else if ("jar".equals(url.getProtocol())) {
                    collectJarClasses(url, packagePath, names);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("실습 클래스를 탐색할 수 없습니다.", e);
        }
        return names;
    }

    private static void collectFileClasses(URL url, String packageName, Set<String> names) {
        try {
            Path directory = Path.of(url.toURI());
            if (!Files.isDirectory(directory)) {
                return;
            }
            try (Stream<Path> stream = Files.list(directory)) {
                stream.filter(Files::isRegularFile)
                        .map(path -> path.getFileName().toString())
                        .filter(name -> name.endsWith(".class"))
                        .filter(name -> !name.contains("$"))
                        .map(name -> name.substring(0, name.length() - ".class".length()))
                        .map(name -> packageName + "." + name)
                        .forEach(names::add);
            }
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException("파일 시스템의 실습 클래스를 탐색할 수 없습니다.", e);
        }
    }

    private static void collectJarClasses(URL url, String packagePath, Set<String> names) {
        try {
            JarURLConnection connection = (JarURLConnection) url.openConnection();
            try (JarFile jar = connection.getJarFile()) {
                Enumeration<JarEntry> entries = jar.entries();
                String prefix = packagePath + "/";
                while (entries.hasMoreElements()) {
                    String name = entries.nextElement().getName();
                    if (!name.startsWith(prefix) || !name.endsWith(".class") || name.contains("$")) {
                        continue;
                    }

                    String remainder = name.substring(prefix.length());
                    if (remainder.contains("/")) {
                        continue;
                    }

                    names.add(name.substring(0, name.length() - ".class".length()).replace('/', '.'));
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("JAR의 실습 클래스를 탐색할 수 없습니다.", e);
        }
    }
}
