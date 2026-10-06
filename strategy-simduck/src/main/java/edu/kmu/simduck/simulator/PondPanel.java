package edu.kmu.simduck.simulator;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 지형과 오리 애니메이션을 그리는 실습 화면이다.
 */
public final class PondPanel extends JPanel {
    private static final int WIDTH = 1120;
    private static final int HEIGHT = 640;
    private final SimulationModel model;
    private final AssetManager assets;

    public PondPanel(SimulationModel model, AssetManager assets) {
        this.model = model;
        this.assets = assets;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(21, 47, 65));
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create();
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            drawTerrain(g);
            drawEnvironment(g);
            drawDuck(g);
            drawStatus(g);
        } finally {
            g.dispose();
        }
    }

    private void drawTerrain(Graphics2D g) {
        BufferedImage tile = assets.terrain(model.terrain());
        TexturePaint paint = new TexturePaint(tile, new Rectangle2D.Double(0, 0, tile.getWidth(), tile.getHeight()));
        g.setPaint(paint);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(new Color(0, 0, 0, 30));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void drawEnvironment(Graphics2D g) {
        if (model.terrain().isWater()) {
            drawCentered(g, assets.environment("island"), 910, 470, 170);
            drawCentered(g, assets.environment("rock"), 150, 175, 75);
            drawCentered(g, assets.environment("log"), 725, 160, 120);
            drawCentered(g, assets.environment("reeds"), 990, 185, 90);
        } else {
            drawCentered(g, assets.environment("rock"), 140, 500, 70);
            drawCentered(g, assets.environment("log"), 825, 515, 120);
            drawCentered(g, assets.environment("reeds"), 1020, 500, 90);
        }
    }

    private void drawDuck(Graphics2D g) {
        String sequence = sequenceName();
        List<BufferedImage> frames = assets.duckSequence(model.skin(), sequence);
        int index = Math.min(model.frameIndex(), frames.size() - 1);
        BufferedImage frame = frames.get(index);

        int targetWidth = switch (model.action()) {
            case TAKEOFF, LANDING, FISHING_DIP, FISHING_SUCCESS, FISHING_FAIL -> 270;
            case FLY -> 230;
            default -> 215;
        };
        int x = (int) model.x();
        int y = (int) model.y();

        if (model.terrain().isWater() && !model.flying() && model.action() != ActionType.FLY) {
            g.setColor(new Color(0, 40, 70, 45));
            g.fill(new Ellipse2D.Double(x - 95, y + 40, 190, 30));
        } else if (!model.terrain().isWater() && !model.flying()) {
            g.setColor(new Color(0, 0, 0, 45));
            g.fill(new Ellipse2D.Double(x - 80, y + 50, 160, 28));
        }

        if (model.action() == ActionType.SWIM) {
            BufferedImage ripple = assets.environment("ripple");
            drawCentered(g, ripple, x - 55, y + 35, 110);
        }
        if ((model.action() == ActionType.TAKEOFF || model.action() == ActionType.LANDING)
                && model.terrain().isWater()) {
            List<BufferedImage> splash = assets.effectSequence("splash");
            BufferedImage effect = splash.get(Math.min(model.frameIndex(), splash.size() - 1));
            drawCentered(g, effect, x, (int) surfaceY() + 30, 220);
        }

        drawCentered(g, frame, x, y, targetWidth);

        if (model.rocketFlying() && model.action() == ActionType.FLY) {
            drawRocketTrail(g, x - targetWidth / 2 + 10, y + 18);
        }
        if (!model.speech().isBlank()) {
            drawSpeech(g, model.speech(), x + 90, y - 90);
        }
    }

    private String sequenceName() {
        return switch (model.action()) {
            case IDLE -> "idle";
            case SWIM -> "swim";
            case WALK -> "walk";
            case QUACK -> "quack";
            case FLY -> "fly";
            case TAKEOFF -> switch (model.terrain()) {
                case WATER, FISH_WATER -> "takeoff-water";
                case GRASS -> "takeoff-grass";
                case SAND -> "takeoff-sand";
            };
            case LANDING -> switch (model.terrain()) {
                case WATER, FISH_WATER -> "landing-water";
                case GRASS -> "landing-grass";
                case SAND -> "landing-sand";
            };
            case FISHING_DIP -> "fishing-dip";
            case FISHING_SUCCESS -> "fishing-success";
            case FISHING_FAIL -> "fishing-fail";
        };
    }

    private double surfaceY() {
        return model.terrain().isWater() ? 410 : 445;
    }

    private void drawStatus(Graphics2D g) {
        g.setColor(new Color(10, 20, 30, 175));
        g.fillRoundRect(20, 18, 500, 95, 18, 18);
        g.setColor(new Color(255, 255, 255, 35));
        g.setStroke(new BasicStroke(1));
        g.drawRoundRect(20, 18, 500, 95, 18, 18);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        g.setColor(Color.WHITE);
        g.drawString(model.duck().getClass().getSimpleName(), 38, 50);
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        g.setColor(new Color(220, 235, 245));
        g.drawString(model.message(), 38, 78);
        g.drawString("지형: " + model.terrain().displayName(), 38, 101);
    }

    private void drawSpeech(Graphics2D g, String text, int x, int y) {
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 21));
        FontMetrics metrics = g.getFontMetrics();
        int width = metrics.stringWidth(text) + 28;
        g.setColor(new Color(255, 255, 255, 235));
        g.fillRoundRect(x, y, width, 42, 18, 18);
        g.setColor(new Color(30, 40, 50));
        g.drawString(text, x + 14, y + 28);
    }

    private void drawRocketTrail(Graphics2D g, int x, int y) {
        g.setColor(new Color(255, 192, 54, 190));
        int[] xs = {x, x - 80, x - 35};
        int[] ys = {y, y - 16, y + 18};
        g.fillPolygon(xs, ys, 3);
        g.setColor(new Color(255, 245, 190, 220));
        g.fillOval(x - 42, y - 5, 34, 12);
    }

    private void drawCentered(Graphics2D g, BufferedImage image, int centerX, int centerY, int targetWidth) {
        if (image == null || image.getWidth() <= 0 || image.getHeight() <= 0) {
            return;
        }
        int targetHeight = Math.max(1, targetWidth * image.getHeight() / image.getWidth());
        g.drawImage(image, centerX - targetWidth / 2, centerY - targetHeight / 2,
                targetWidth, targetHeight, null);
    }
}
