package edu.kmu.simduck.simulator;

/**
 * 시뮬레이터에서 선택할 수 있는 지형을 나타낸다.
 */
public enum TerrainType {
    WATER("물"),
    FISH_WATER("물고기가 있는 물"),
    GRASS("초원"),
    SAND("모래");

    private final String displayName;

    TerrainType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * 화면에 표시할 지형 이름을 반환한다.
     *
     * @return 한글 지형 이름
     */
    public String displayName() {
        return displayName;
    }

    /**
     * 물 지형인지 확인한다.
     *
     * @return 물 지형이면 참
     */
    public boolean isWater() {
        return this == WATER || this == FISH_WATER;
    }
}
