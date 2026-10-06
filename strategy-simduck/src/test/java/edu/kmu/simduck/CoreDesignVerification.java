package edu.kmu.simduck;

import edu.kmu.simduck.behavior.FlyNoWay;
import edu.kmu.simduck.behavior.FlyRocketPowered;
import edu.kmu.simduck.behavior.FlyWithWings;
import edu.kmu.simduck.behavior.MuteQuack;
import edu.kmu.simduck.behavior.Quack;
import edu.kmu.simduck.behavior.Squeak;
import edu.kmu.simduck.duck.DecoyDuck;
import edu.kmu.simduck.duck.MallardDuck;
import edu.kmu.simduck.duck.ModelDuck;
import edu.kmu.simduck.duck.RubberDuck;
import edu.kmu.simduck.simulator.AssetManager;
import edu.kmu.simduck.simulator.TerrainType;

/**
 * 교재의 기본 전략 연결과 실행 중 전략 교체를 간단히 검증한다.
 */
public final class CoreDesignVerification {
    private CoreDesignVerification() {
    }

    public static void main(String[] args) {
        require(new MallardDuck().getFlyBehavior() instanceof FlyWithWings, "청둥오리 비행 전략");
        require(new MallardDuck().getQuackBehavior() instanceof Quack, "청둥오리 울음 전략");
        require(new RubberDuck().getFlyBehavior() instanceof FlyNoWay, "고무 오리 비행 전략");
        require(new RubberDuck().getQuackBehavior() instanceof Squeak, "고무 오리 울음 전략");
        require(new DecoyDuck().getQuackBehavior() instanceof MuteQuack, "유인용 오리 울음 전략");

        ModelDuck model = new ModelDuck();
        require(model.getFlyBehavior() instanceof FlyNoWay, "모형 오리 초기 비행 전략");
        model.setFlyBehavior(new FlyRocketPowered());
        require(model.getFlyBehavior() instanceof FlyRocketPowered, "모형 오리 실행 중 전략 교체");
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
        System.out.println("핵심 설계와 에셋 검증을 통과했습니다.");
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException("검증 실패: " + message);
        }
    }
}
