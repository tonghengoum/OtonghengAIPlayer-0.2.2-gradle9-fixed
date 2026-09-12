package com.otongheng.aiplayer.goals;

public final class GoalManager {
    private String goal = "SURVIVE";
    private int progress;
    public String getGoal() { return goal; }
    public int getProgress() { return progress; }
    public void setGoal(String goal) { this.goal = goal == null || goal.isBlank() ? "SURVIVE" : goal.toUpperCase(); progress = 0; }
    public void advance() { progress = Math.min(100, progress + 1); }
}
