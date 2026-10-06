package edu.kmu.simduck.simulator;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 클래스 경로의 이미지 묶음을 읽고 필요한 프레임과 타일을 분리한다.
 */
public final class AssetManager {
    private static final int CELL = 80;
    private final Map<String, BufferedImage> imageCache = new HashMap<>();
    private final Map<String, List<BufferedImage>> sequenceCache = new HashMap<>();
    private final Map<String, BufferedImage> environmentCache = new HashMap<>();
    private final Map<TerrainType, BufferedImage> terrainCache = new HashMap<>();
    private final Map<String, byte[]> packedAssets = new HashMap<>();

    /**
     * 현재 지형에 맞는 배경 타일을 반환한다.
     *
     * @param terrain 지형 종류
     * @return 배경 타일
     */
    public BufferedImage terrain(TerrainType terrain) {
        return terrainCache.computeIfAbsent(terrain, this::cropTerrain);
    }

    /**
     * 오리 종류와 동작 이름에 맞는 프레임 목록을 반환한다.
     *
     * @param skin 오리 시각 종류
     * @param sequence 애니메이션 이름
     * @return 프레임 목록
     */
    public List<BufferedImage> duckSequence(String skin, String sequence) {
        String key = skin + "/" + sequence;
        return sequenceCache.computeIfAbsent(key,
                ignored -> transformSequence(skin, rawSequence(sequence), sequence));
    }

    /**
     * 물 효과 프레임을 반환한다.
     *
     * @param sequence 효과 이름
     * @return 효과 프레임 목록
     */
    public List<BufferedImage> effectSequence(String sequence) {
        return sequenceCache.computeIfAbsent("effects/" + sequence, ignored -> {
            if ("splash".equals(sequence)) {
                return splitFour(cropCell(image("atlas/motion-atlas.png"), 2, 1));
            }
            return List.of(trim(cropCell(image("atlas/environment-atlas.png"), 0, 1)));
        });
    }

    /**
     * 환경 장식 이미지를 반환한다.
     *
     * @param name 환경 장식 이름
     * @return 환경 장식 이미지
     */
    public BufferedImage environment(String name) {
        return environmentCache.computeIfAbsent(name, this::cropEnvironment);
    }

    private List<BufferedImage> rawSequence(String sequence) {
        return switch (sequence) {
            case "idle" -> List.of(trim(cropCell(image("atlas/duck-atlas.png"), 0, 0)));
            case "swim" -> List.of(
                    trim(cropCell(image("atlas/duck-atlas.png"), 1, 0)),
                    trim(cropCell(image("atlas/duck-atlas.png"), 2, 0)));
            case "quack" -> List.of(
                    trim(cropCell(image("atlas/duck-atlas.png"), 0, 1)),
                    trim(cropCell(image("atlas/duck-atlas.png"), 0, 0)));
            case "walk" -> splitFour(cropCell(image("atlas/duck-atlas.png"), 1, 1));
            case "fly" -> splitFour(cropCell(image("atlas/duck-atlas.png"), 2, 1));
            case "takeoff-water" -> splitFour(cropCell(image("atlas/motion-atlas.png"), 0, 0));
            case "landing-water" -> splitFour(cropCell(image("atlas/motion-atlas.png"), 1, 0));
            case "takeoff-grass" -> splitFour(cropCell(image("atlas/motion-atlas.png"), 2, 0));
            case "takeoff-sand" -> splitFour(cropCell(image("atlas/motion-atlas.png"), 0, 1));
            case "landing-grass" -> splitFour(cropCell(image("atlas/motion-atlas.png"), 1, 1));
            case "landing-sand" -> reversed(splitFour(cropCell(image("atlas/motion-atlas.png"), 0, 1)));
            case "fishing-dip" -> splitFour(cropCell(image("atlas/fishing-atlas.png"), 0, 0));
            case "fishing-fail" -> splitFour(cropCell(image("atlas/fishing-atlas.png"), 1, 0));
            case "fishing-success" -> splitFour(cropCell(image("atlas/fishing-atlas.png"), 2, 0));
            default -> throw new IllegalArgumentException("알 수 없는 애니메이션: " + sequence);
        };
    }

    private BufferedImage cropTerrain(TerrainType terrain) {
        BufferedImage atlas = image("atlas/terrain-atlas.jpg");
        int index = switch (terrain) {
            case WATER -> 0;
            case FISH_WATER -> 1;
            case GRASS -> 2;
            case SAND -> 3;
        };
        int x = (index % 2) * CELL;
        int y = (index / 2) * CELL;
        return copy(atlas.getSubimage(x, y, CELL, CELL));
    }

    private BufferedImage cropEnvironment(String name) {
        BufferedImage atlas = image("atlas/environment-atlas.png");
        if ("island".equals(name)) {
            return trim(cropCell(atlas, 0, 0));
        }
        if ("ripple".equals(name)) {
            return trim(cropCell(atlas, 0, 1));
        }
        BufferedImage obstacleCell = cropCell(atlas, 1, 0);
        int third = obstacleCell.getWidth() / 3;
        int index = switch (name) {
            case "rock" -> 0;
            case "log" -> 1;
            case "reeds" -> 2;
            default -> throw new IllegalArgumentException("알 수 없는 환경 에셋: " + name);
        };
        int x = index * third;
        int width = index == 2 ? obstacleCell.getWidth() - x : third;
        return trim(obstacleCell.getSubimage(x, 0, width, obstacleCell.getHeight()));
    }

    private List<BufferedImage> transformSequence(String skin, List<BufferedImage> frames, String sequence) {
        if ("mallard".equals(skin)) {
            return frames;
        }
        boolean cleanSequence = sequence.equals("idle") || sequence.equals("swim")
                || sequence.equals("quack") || sequence.equals("walk") || sequence.equals("fly");

        List<BufferedImage> transformed = new ArrayList<>(frames.size());
        for (BufferedImage frame : frames) {
            if ("redhead".equals(skin)) {
                transformed.add(transformRedhead(frame));
            } else if (cleanSequence) {
                transformed.add(transformWholeDuck(frame, skin));
            } else {
                transformed.add(frame);
            }
        }
        return List.copyOf(transformed);
    }

    private BufferedImage transformRedhead(BufferedImage source) {
        BufferedImage result = copy(source);
        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                int argb = result.getRGB(x, y);
                int alpha = (argb >>> 24) & 0xff;
                if (alpha < 12) {
                    continue;
                }
                int red = (argb >>> 16) & 0xff;
                int green = (argb >>> 8) & 0xff;
                int blue = argb & 0xff;
                if (green > red * 1.10 && green > blue * 1.12 && green > 65) {
                    int light = (red + green + blue) / 3;
                    result.setRGB(x, y, new Color(
                            clamp((int) (light * 1.20) + 45),
                            clamp((int) (light * 0.55) + 20),
                            clamp((int) (light * 0.35) + 12),
                            alpha).getRGB());
                }
            }
        }
        return result;
    }

    private BufferedImage transformWholeDuck(BufferedImage source, String skin) {
        BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                int argb = source.getRGB(x, y);
                int alpha = (argb >>> 24) & 0xff;
                if (alpha < 12) {
                    continue;
                }
                int red = (argb >>> 16) & 0xff;
                int green = (argb >>> 8) & 0xff;
                int blue = argb & 0xff;
                int light = clamp((int) (red * 0.21 + green * 0.72 + blue * 0.07));
                Color color = switch (skin) {
                    case "rubber" -> new Color(clamp(190 + light / 4), clamp(145 + light / 5), 28, alpha);
                    case "decoy" -> new Color(clamp(70 + light * 2 / 3), clamp(45 + light / 2), clamp(28 + light / 3), alpha);
                    case "model" -> new Color(clamp(55 + light * 2 / 3), clamp(70 + light * 3 / 4), clamp(95 + light * 4 / 5), alpha);
                    default -> new Color(red, green, blue, alpha);
                };
                result.setRGB(x, y, color.getRGB());
            }
        }
        return result;
    }

    private BufferedImage cropCell(BufferedImage atlas, int column, int row) {
        return copy(atlas.getSubimage(column * CELL, row * CELL, CELL, CELL));
    }

    private List<BufferedImage> splitFour(BufferedImage sheet) {
        int halfWidth = sheet.getWidth() / 2;
        int halfHeight = sheet.getHeight() / 2;
        List<BufferedImage> frames = new ArrayList<>(4);
        frames.add(trim(sheet.getSubimage(0, 0, halfWidth, halfHeight)));
        frames.add(trim(sheet.getSubimage(halfWidth, 0, sheet.getWidth() - halfWidth, halfHeight)));
        frames.add(trim(sheet.getSubimage(0, halfHeight, halfWidth, sheet.getHeight() - halfHeight)));
        frames.add(trim(sheet.getSubimage(halfWidth, halfHeight, sheet.getWidth() - halfWidth,
                sheet.getHeight() - halfHeight)));
        return List.copyOf(frames);
    }

    private BufferedImage trim(BufferedImage source) {
        int minX = source.getWidth();
        int minY = source.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                if (((source.getRGB(x, y) >>> 24) & 0xff) > 8) {
                    minX = Math.min(minX, x);
                    minY = Math.min(minY, y);
                    maxX = Math.max(maxX, x);
                    maxY = Math.max(maxY, y);
                }
            }
        }
        if (maxX < minX || maxY < minY) {
            return source;
        }
        int padding = 2;
        minX = Math.max(0, minX - padding);
        minY = Math.max(0, minY - padding);
        maxX = Math.min(source.getWidth() - 1, maxX + padding);
        maxY = Math.min(source.getHeight() - 1, maxY + padding);
        return copy(source.getSubimage(minX, minY, maxX - minX + 1, maxY - minY + 1));
    }

    private List<BufferedImage> reversed(List<BufferedImage> source) {
        List<BufferedImage> copy = new ArrayList<>(source);
        Collections.reverse(copy);
        return List.copyOf(copy);
    }

    private BufferedImage copy(BufferedImage source) {
        BufferedImage copy = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = copy.createGraphics();
        try {
            graphics.drawImage(source, 0, 0, null);
        } finally {
            graphics.dispose();
        }
        return copy;
    }

    private BufferedImage loadImage(String path) {
        byte[] bytes = packedAsset(path);
        try (InputStream stream = new ByteArrayInputStream(bytes)) {
            return ImageIO.read(stream);
        } catch (IOException e) {
            throw new IllegalStateException("에셋을 읽을 수 없습니다: " + path, e);
        }
    }

    private BufferedImage image(String path) {
        return imageCache.computeIfAbsent(path, this::loadImage);
    }

    private byte[] packedAsset(String path) {
        if (packedAssets.isEmpty()) {
            loadPackedAssets();
        }
        String name = path.startsWith("atlas/") ? path.substring("atlas/".length()) : path;
        byte[] bytes = packedAssets.get(name);
        if (bytes == null) {
            throw new IllegalArgumentException("에셋을 찾을 수 없습니다: " + path);
        }
        return bytes;
    }

    private void loadPackedAssets() {
        try (InputStream stream = AssetManager.class.getResourceAsStream("/assets/assets-atlas.zip")) {
            if (stream == null) {
                throw new IllegalStateException("압축 에셋 묶음을 찾을 수 없습니다.");
            }

            try (ZipInputStream zip = new ZipInputStream(stream)) {
                ZipEntry entry;
                while ((entry = zip.getNextEntry()) != null) {
                    if (entry.isDirectory()) {
                        continue;
                    }
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    zip.transferTo(buffer);
                    packedAssets.put(entry.getName(), buffer.toByteArray());
                }
            }

            if (packedAssets.isEmpty()) {
                throw new IllegalStateException("압축 에셋 묶음 안에 이미지가 없습니다.");
            }
        } catch (IOException e) {
            throw new IllegalStateException("압축 에셋 묶음을 읽을 수 없습니다.", e);
        }
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
