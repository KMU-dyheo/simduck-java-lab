package edu.kmu.simduck.simulator;

import edu.kmu.simduck.duck.Duck;

/**
 * 화면과 애니메이션이 공유하는 실행 상태를 보관한다.
 */
public final class SimulationModel {
    private Duck duck;
    private String skin = "mallard";
    private TerrainType terrain = TerrainType.WATER;
    private ActionType action = ActionType.IDLE;
    private int frameIndex;
    private long frameElapsedMillis;
    private long actionElapsedMillis;
    private double x = 320;
    private double y = 420;
    private boolean flying;
    private boolean rocketFlying;
    private boolean fishingSuccess;
    private String speech = "";
    private String message = "대기 중";

    public Duck duck() { return duck; }
    public void setDuck(Duck duck) { this.duck = duck; }
    public String skin() { return skin; }
    public void setSkin(String skin) { this.skin = skin; }
    public TerrainType terrain() { return terrain; }
    public void setTerrain(TerrainType terrain) { this.terrain = terrain; }
    public ActionType action() { return action; }
    public int frameIndex() { return frameIndex; }
    public void setFrameIndex(int frameIndex) { this.frameIndex = frameIndex; }
    public long frameElapsedMillis() { return frameElapsedMillis; }
    public void addFrameElapsedMillis(long delta) { frameElapsedMillis += delta; }
    public void resetFrameElapsedMillis() { frameElapsedMillis = 0; }
    public long actionElapsedMillis() { return actionElapsedMillis; }
    public void addActionElapsedMillis(long delta) { actionElapsedMillis += delta; }
    public double x() { return x; }
    public void setX(double x) { this.x = x; }
    public double y() { return y; }
    public void setY(double y) { this.y = y; }
    public boolean flying() { return flying; }
    public void setFlying(boolean flying) { this.flying = flying; }
    public boolean rocketFlying() { return rocketFlying; }
    public void setRocketFlying(boolean rocketFlying) { this.rocketFlying = rocketFlying; }
    public boolean fishingSuccess() { return fishingSuccess; }
    public void setFishingSuccess(boolean fishingSuccess) { this.fishingSuccess = fishingSuccess; }
    public String speech() { return speech; }
    public void setSpeech(String speech) { this.speech = speech; }
    public String message() { return message; }
    public void setMessage(String message) { this.message = message; }

    /**
     * 새 동작을 시작하고 프레임 진행 시간을 초기화한다.
     *
     * @param action 새로 시작할 동작
     */
    public void startAction(ActionType action) {
        this.action = action;
        frameIndex = 0;
        frameElapsedMillis = 0;
        actionElapsedMillis = 0;
    }
}
