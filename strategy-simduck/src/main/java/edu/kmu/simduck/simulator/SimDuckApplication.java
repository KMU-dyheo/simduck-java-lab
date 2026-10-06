package edu.kmu.simduck.simulator;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * 스윙 기반 심덕 실습 시뮬레이터를 시작한다.
 */
public final class SimDuckApplication {
    private SimDuckApplication() {
    }

    /**
     * 화면 이벤트 스레드에서 실습 창을 생성한다.
     *
     * @param args 사용하지 않는 명령행 인자
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // 운영체제 기본 화면 모양을 적용하지 못해도 기본 화면 모양으로 실행할 수 있다.
            }
            new SimDuckFrame().setVisible(true);
        });
    }
}
