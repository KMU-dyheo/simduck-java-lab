package edu.kmu.simduck.simulator;

import edu.kmu.simduck.behavior.FlyNoWay;
import edu.kmu.simduck.behavior.FlyRocketPowered;
import edu.kmu.simduck.behavior.MuteQuack;
import edu.kmu.simduck.behavior.Squeak;
import edu.kmu.simduck.duck.Duck;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * 전략 교체와 화면 동작을 함께 실습하는 주 실행 창이다.
 */
public final class SimDuckFrame extends JFrame {
    private final SimulationModel model = new SimulationModel();
    private final AssetManager assets = new AssetManager();
    private final AnimationController animation = new AnimationController(model);
    private final PondPanel pondPanel = new PondPanel(model, assets);
    private final JComboBox<String> duckBox = new JComboBox<>(DuckFactory.names());
    private final JComboBox<String> flyBox = new JComboBox<>(BehaviorFactory.flyNames());
    private final JComboBox<String> quackBox = new JComboBox<>(BehaviorFactory.quackNames());
    private final JComboBox<TerrainType> terrainBox = new JComboBox<>(TerrainType.values());
    private final JTextArea logArea = new JTextArea();
    private long lastTick = System.currentTimeMillis();

    public SimDuckFrame() {
        super("심덕 자바 전략 패턴 실습");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        add(pondPanel, BorderLayout.CENTER);
        add(buildControlPanel(), BorderLayout.EAST);
        setInitialDuck("MallardDuck");
        startTimer();
        pack();
        setMinimumSize(new Dimension(1180, 720));
        setLocationRelativeTo(null);
    }

    private JPanel buildControlPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(320, 640));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 8, 12, 12));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(sectionTitle("오리 선택"));
        panel.add(duckBox);
        panel.add(Box.createVerticalStrut(8));
        panel.add(sectionTitle("지형 선택"));
        terrainBox.setRenderer(new TerrainRenderer());
        panel.add(terrainBox);
        panel.add(Box.createVerticalStrut(10));

        panel.add(sectionTitle("비행 전략"));
        panel.add(flyBox);
        panel.add(sectionTitle("울음 전략"));
        panel.add(quackBox);
        JButton apply = new JButton("전략 교체 적용");
        apply.addActionListener(event -> applyStrategies());
        panel.add(apply);
        panel.add(Box.createVerticalStrut(12));

        panel.add(sectionTitle("행동 실행"));
        JPanel actions = new JPanel(new GridLayout(0, 2, 6, 6));
        addButton(actions, "모습", this::displayDuck);
        addButton(actions, "울기", this::performQuack);
        addButton(actions, "수영", animation::swim);
        addButton(actions, "걷기", animation::walk);
        addButton(actions, "날기", this::performFly);
        addButton(actions, "착륙", animation::land);
        addButton(actions, "물고기", animation::fish);
        addButton(actions, "성공 낚시", () -> animation.fish(true));
        addButton(actions, "실패 낚시", () -> animation.fish(false));
        addButton(actions, "초기화", animation::resetForTerrain);
        panel.add(actions);
        panel.add(Box.createVerticalStrut(12));

        panel.add(sectionTitle("실행 기록"));
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setPreferredSize(new Dimension(290, 180));
        panel.add(scroll);

        duckBox.addActionListener(event -> setInitialDuck((String) duckBox.getSelectedItem()));
        terrainBox.addActionListener(event -> {
            TerrainType terrain = (TerrainType) terrainBox.getSelectedItem();
            if (terrain != null) {
                model.setTerrain(terrain);
                animation.resetForTerrain();
                log("지형 변경: " + terrain.displayName());
            }
        });
        return panel;
    }

    private JLabel sectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 4, 0));
        return label;
    }

    private void addButton(JPanel panel, String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(event -> {
            action.run();
            pondPanel.repaint();
        });
        panel.add(button);
    }

    private void setInitialDuck(String name) {
        if (name == null) {
            return;
        }
        Duck duck = DuckFactory.create(name);
        model.setDuck(duck);
        model.setSkin(DuckFactory.skin(name));
        flyBox.setSelectedItem(duck.getFlyBehavior().getClass().getSimpleName());
        quackBox.setSelectedItem(duck.getQuackBehavior().getClass().getSimpleName());
        animation.resetForTerrain();
        log("오리 생성: " + name);
    }

    private void applyStrategies() {
        String flyName = (String) flyBox.getSelectedItem();
        String quackName = (String) quackBox.getSelectedItem();
        model.duck().setFlyBehavior(BehaviorFactory.fly(flyName));
        model.duck().setQuackBehavior(BehaviorFactory.quack(quackName));
        log("비행 전략 교체: " + flyName);
        log("울음 전략 교체: " + quackName);
    }

    private void displayDuck() {
        capture(model.duck()::display);
        model.setMessage("모습 출력 완료");
    }

    private void performQuack() {
        String output = capture(model.duck()::performQuack);
        animation.quack(lastLineOrSilence(output));
    }

    private void performFly() {
        capture(model.duck()::performFly);
        if (model.duck().getFlyBehavior() instanceof FlyNoWay) {
            model.setMessage("현재 비행 전략으로는 날 수 없습니다.");
            return;
        }
        animation.takeOff(model.duck().getFlyBehavior() instanceof FlyRocketPowered);
    }

    private String capture(Runnable runnable) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream original = System.out;
        try (PrintStream replacement = new PrintStream(buffer, true, StandardCharsets.UTF_8)) {
            System.setOut(replacement);
            runnable.run();
        } finally {
            System.setOut(original);
        }
        String text = buffer.toString(StandardCharsets.UTF_8).trim();
        if (!text.isBlank()) {
            log(text);
        }
        return text;
    }

    private String lastLineOrSilence(String text) {
        if (text == null || text.isBlank()) {
            return "...";
        }
        String[] lines = text.strip().split("\\R");
        return lines[lines.length - 1];
    }

    private void log(String text) {
        logArea.append(text + System.lineSeparator());
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private void startTimer() {
        Timer timer = new Timer(33, event -> {
            long now = System.currentTimeMillis();
            long delta = Math.min(100, now - lastTick);
            lastTick = now;
            animation.update(delta);
            pondPanel.repaint();
        });
        timer.start();
    }

    /**
     * 지형 열거형을 한글 이름으로 표시한다.
     */
    private static final class TerrainRenderer extends javax.swing.DefaultListCellRenderer {
        @Override
        public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                                                                int index, boolean isSelected,
                                                                boolean cellHasFocus) {
            Object shown = value instanceof TerrainType terrain ? terrain.displayName() : value;
            return super.getListCellRendererComponent(list, shown, index, isSelected, cellHasFocus);
        }
    }
}
