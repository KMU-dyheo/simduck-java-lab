package edu.kmu.simduck;

import edu.kmu.simduck.duck.Duck;
import edu.kmu.simduck.simulator.AssetManager;
import edu.kmu.simduck.simulator.DuckFactory;
import edu.kmu.simduck.simulator.LabStage;
import edu.kmu.simduck.simulator.SimulatorBridge;
import edu.kmu.simduck.simulator.TerrainType;

/**
 * 각 실습 브랜치에서 핵심 코드와 시뮬레이터 연결이 깨지지 않았는지 확인한다.
 */
public final class CoreDesignVerification {
    private CoreDesignVerification() {
    }

    /**
     * 현재 단계의 기본 객체 생성과 에셋 로딩을 검증한다.
     *
     * @param args 사용하지 않는 명령행 인자
     */
    public static void main(String[] args) {
        String[] names = DuckFactory.names();
        require(names.length > 0, "선택 가능한 오리");
        Duck duck = DuckFactory.create(names[0]);
        require(duck != null, "오리 생성");
        require(!SimulatorBridge.display(duck).isBlank(), "모습 출력");
        require(!SimulatorBridge.swim(duck).isBlank(), "수영 출력");

        AssetManager assets = new AssetManager();
        for (TerrainType terrain : TerrainType.values()) {
            require(assets.terrain(terrain) != null, "지형 에셋: " + terrain.displayName());
        }
        for (String sequence : new String[]{
                "idle", "swim", "quack", "walk", "fly",
                "takeoff-water", "landing-water", "takeoff-grass", "takeoff-sand",
                "landing-grass", "landing-sand", "fishing-dip", "fishing-fail", "fishing-success"}) {
            require(!assets.duckSequence("mallard", sequence).isEmpty(), "오리 에셋: " + sequence);
        }
        require(!assets.effectSequence("splash").isEmpty(), "첨벙임 에셋");
        for (String name : new String[]{"island", "rock", "log", "reeds", "ripple"}) {
            require(assets.environment(name) != null, "환경 에셋: " + name);
        }
        System.out.println(LabStage.title() + " 검증을 통과했습니다.");
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException("검증 실패: " + message);
        }
    }
}
