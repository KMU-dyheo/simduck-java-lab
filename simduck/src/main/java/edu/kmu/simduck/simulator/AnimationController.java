package edu.kmu.simduck.simulator;

import java.util.Random;

/**
 * 시뮬레이터의 동작 전환과 프레임 진행을 담당한다.
 */
public final class AnimationController {
    private static final long DEFAULT_FRAME_MILLIS = 180;
    private final SimulationModel model;
    private final Random random = new Random();

    public AnimationController(SimulationModel model) {
        this.model = model;
    }

    /**
     * 대기 상태로 돌아간다.
     */
    public void idle() {
        model.startAction(ActionType.IDLE);
        model.setSpeech("");
        model.setMessage("대기 중");
    }

    /**
     * 수영 동작을 시작한다.
     */
    public void swim() {
        if (!model.terrain().isWater()) {
            model.setMessage("현재 지형에서는 수영할 수 없습니다.");
            return;
        }
        model.startAction(ActionType.SWIM);
        model.setMessage("수영 중");
    }

    /**
     * 걷기 동작을 시작한다.
     */
    public void walk() {
        if (model.terrain().isWater()) {
            model.setMessage("물에서는 걷기보다 수영을 사용합니다.");
            return;
        }
        model.startAction(ActionType.WALK);
        model.setMessage("뒤뚱뒤뚱 걷는 중");
    }

    /**
     * 울음 동작을 시작한다.
     *
     * @param speech 화면에 표시할 울음소리
     */
    public void quack(String speech) {
        model.startAction(ActionType.QUACK);
        model.setSpeech(speech);
        model.setMessage("울음 행동 수행");
    }

    /**
     * 현재 지형에서 이륙을 시작한다.
     *
     * @param rocket 로켓 비행 여부
     */
    public void takeOff(boolean rocket) {
        if (model.flying()) {
            model.startAction(ActionType.FLY);
            model.setRocketFlying(rocket);
            model.setMessage(rocket ? "로켓 비행 중" : "비행 중");
            return;
        }
        model.setRocketFlying(rocket);
        model.startAction(ActionType.TAKEOFF);
        model.setMessage(rocket ? "로켓 비행을 위해 이륙 중" : "푸드득 이륙 중");
    }

    /**
     * 현재 지형을 목표로 착륙을 시작한다.
     */
    public void land() {
        if (!model.flying()) {
            model.setMessage("현재 비행 중이 아닙니다.");
            return;
        }
        model.startAction(ActionType.LANDING);
        model.setMessage("파드득 착륙 중");
    }

    /**
     * 물고기 잡기 동작을 시작한다.
     */
    public void fish() {
        if (model.terrain() != TerrainType.FISH_WATER) {
            model.setMessage("물고기가 있는 물 지형에서만 물고기를 잡을 수 있습니다.");
            return;
        }
        if (model.flying()) {
            model.setMessage("먼저 착륙해야 합니다.");
            return;
        }
        model.setFishingSuccess(random.nextBoolean());
        model.startAction(ActionType.FISHING_DIP);
        model.setMessage("물고기를 잡기 위해 머리를 물속에 넣습니다.");
    }

    /**
     * 물고기 잡기 결과를 지정해서 동작을 시작한다.
     *
     * @param success 성공 여부
     */
    public void fish(boolean success) {
        if (model.terrain() != TerrainType.FISH_WATER || model.flying()) {
            fish();
            return;
        }
        model.setFishingSuccess(success);
        model.startAction(ActionType.FISHING_DIP);
        model.setMessage(success ? "물고기 잡기 성공 과정을 시작합니다." : "물고기 잡기 실패 과정을 시작합니다.");
    }

    /**
     * 지정한 시간만큼 애니메이션 상태를 진행한다.
     *
     * @param deltaMillis 이전 갱신 이후 흐른 시간
     */
    public void update(long deltaMillis) {
        model.addFrameElapsedMillis(deltaMillis);
        model.addActionElapsedMillis(deltaMillis);

        switch (model.action()) {
            case IDLE -> model.setFrameIndex(0);
            case SWIM -> updateLoop(2, 170, 0.08);
            case WALK -> updateLoop(4, 150, 0.12);
            case QUACK -> updateQuack();
            case TAKEOFF -> updateTakeOff();
            case FLY -> updateFly(deltaMillis);
            case LANDING -> updateLanding();
            case FISHING_DIP -> updateFishingDip();
            case FISHING_SUCCESS, FISHING_FAIL -> updateFishingResult();
        }
    }

    private void updateLoop(int frameCount, long frameMillis, double speed) {
        if (model.frameElapsedMillis() >= frameMillis) {
            model.setFrameIndex((model.frameIndex() + 1) % frameCount);
            model.resetFrameElapsedMillis();
        }
        model.setX(model.x() + speed * frameMillis / 10.0);
        if (model.x() > 1040) {
            model.setX(260);
        }
    }

    private void updateQuack() {
        if (model.frameElapsedMillis() >= 200) {
            model.setFrameIndex((model.frameIndex() + 1) % 2);
            model.resetFrameElapsedMillis();
        }
        if (model.actionElapsedMillis() >= 1000) {
            idle();
        }
    }

    private void updateTakeOff() {
        if (advanceOnce(4, DEFAULT_FRAME_MILLIS)) {
            model.setFlying(true);
            model.startAction(ActionType.FLY);
            model.setY(255);
            model.setMessage(model.rocketFlying() ? "로켓 비행 중" : "비행 중");
        }
    }

    private void updateFly(long deltaMillis) {
        if (model.frameElapsedMillis() >= 150) {
            model.setFrameIndex((model.frameIndex() + 1) % 4);
            model.resetFrameElapsedMillis();
        }
        double speed = model.rocketFlying() ? 0.36 : 0.22;
        model.setX(model.x() + deltaMillis * speed);
        model.setY(255 + Math.sin(model.actionElapsedMillis() / 130.0) * 9);
        if (model.x() > 1120) {
            model.setX(130);
        }
    }

    private void updateLanding() {
        if (advanceOnce(4, DEFAULT_FRAME_MILLIS)) {
            model.setFlying(false);
            model.setRocketFlying(false);
            model.setY(surfaceY());
            idle();
        }
    }

    private void updateFishingDip() {
        if (advanceOnce(4, 220)) {
            model.startAction(model.fishingSuccess() ? ActionType.FISHING_SUCCESS : ActionType.FISHING_FAIL);
            model.setMessage(model.fishingSuccess() ? "물고기를 잡았습니다." : "물고기를 놓쳤습니다.");
        }
    }

    private void updateFishingResult() {
        if (advanceOnce(4, 230)) {
            idle();
        }
    }

    private boolean advanceOnce(int frameCount, long frameMillis) {
        if (model.frameElapsedMillis() < frameMillis) {
            return false;
        }
        model.resetFrameElapsedMillis();
        int next = model.frameIndex() + 1;
        if (next >= frameCount) {
            model.setFrameIndex(frameCount - 1);
            return true;
        }
        model.setFrameIndex(next);
        return false;
    }

    private double surfaceY() {
        return model.terrain().isWater() ? 410 : 445;
    }

    /**
     * 지형 변경 뒤 위치와 비행 상태를 초기화한다.
     */
    public void resetForTerrain() {
        model.setFlying(false);
        model.setRocketFlying(false);
        model.setX(320);
        model.setY(surfaceY());
        idle();
    }
}
