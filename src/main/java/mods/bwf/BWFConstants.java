package mods.bwf;

import java.util.Random;

public interface BWFConstants {
    String MODID = "bwf", MODNAME = "BetterWithForge";
    Random RANDOM= new Random();
    float div180byPi = 0.01745329251994329576923690768489f;
    byte[] snakeX = new byte[]{1, 0, -1, -1, 0, 0, 1, 1};
    byte[] snakeZ = new byte[]{0, 1, 0, 0, -1, -1, 0, 0};
    int[] dyeOreIds = new int[16];
}
