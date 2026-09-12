package com.otongheng.aiplayer.config;

public final class AIConfig {
    private AIConfig() {}
    public static boolean autoCombat = true;
    public static boolean autoSurvival = true;
    public static boolean adaptiveMovement = true;
    public static boolean humanizedTiming = true;
    public static double detectionRange = 24.0;
    public static double combatRange = 3.1;
    public static double retreatHealthPercent = 0.25;
    public static int thinkInterval = 3;
    public static void load() { /* reserved for JSON persistence */ }
}
