package com.otongheng.aiplayer.ui;

import com.otongheng.aiplayer.OtonghengAIPlayerClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class AIControlScreen extends Screen {
    public AIControlScreen() { super(Text.literal("O.Tongheng AI Control Center")); }

    @Override
    protected void init() {
        int w = Math.min(260, width - 40);
        int x = (width - w) / 2;
        int y = Math.max(85, height / 2 - 85);
        addDrawableChild(ButtonWidget.builder(Text.literal(OtonghengAIPlayerClient.AI.isRunning() ? "STOP AI" : "START AI"), b -> {
            OtonghengAIPlayerClient.AI.toggle(client); b.setMessage(Text.literal(OtonghengAIPlayerClient.AI.isRunning() ? "STOP AI" : "START AI"));
        }).dimensions(x, y, w, 22).build());
        addDrawableChild(ButtonWidget.builder(Text.literal(OtonghengAIPlayerClient.AI.isPaused() ? "RESUME" : "PAUSE"), b -> {
            OtonghengAIPlayerClient.AI.togglePause(client); b.setMessage(Text.literal(OtonghengAIPlayerClient.AI.isPaused() ? "RESUME" : "PAUSE"));
        }).dimensions(x, y + 28, w, 22).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("GOAL: SURVIVE"), b -> OtonghengAIPlayerClient.AI.getGoals().setGoal("SURVIVE"))
                .dimensions(x, y + 56, w, 22).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("GOAL: COMBAT"), b -> OtonghengAIPlayerClient.AI.getGoals().setGoal("COMBAT"))
                .dimensions(x, y + 84, w, 22).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("EMERGENCY STOP"), b -> OtonghengAIPlayerClient.AI.emergencyStop(client))
                .dimensions(x, y + 112, w, 22).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("CLOSE"), b -> close())
                .dimensions(x, y + 140, w, 22).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        int cx = width / 2;
        context.drawCenteredTextWithShadow(textRenderer, title, cx, 28, 0xFFFFFF);
        var ai = OtonghengAIPlayerClient.AI;
        String status = ai.isRunning() ? (ai.isPaused() ? "PAUSED" : "RUNNING") : "STOPPED";
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Status: " + status + "   State: " + ai.getState()), cx, 48, 0x80D8FF);
        var target = ai.getTarget();
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Target: " + (target == null ? "None" : target.getName().getString())), cx, 64, 0xCCCCCC);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Goal: " + ai.getGoals().getGoal() + "  " + ai.getGoals().getProgress() + "%"), cx, 78, 0xB8C7D9);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override public boolean shouldPause() { return false; }
}
