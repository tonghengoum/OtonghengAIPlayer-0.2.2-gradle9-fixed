package com.otongheng.aiplayer.hud;

import com.otongheng.aiplayer.OtonghengAIPlayerClient;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public final class AIHud {
    private AIHud() {}
    public static void register() {
        HudRenderCallback.EVENT.register(AIHud::render);
    }
    private static void render(DrawContext context, float tickDelta) {
        if (OtonghengAIPlayerClient.AI == null) return;
        var ai = OtonghengAIPlayerClient.AI;
        if (!ai.isRunning()) return;
        int x = 8, y = 8;
        context.fill(x - 4, y - 4, x + 210, y + 60, 0x99090D14);
        context.drawTextWithShadow(net.minecraft.client.MinecraftClient.getInstance().textRenderer,
                Text.literal("O.Tongheng AI"), x, y, 0x80D8FF);
        context.drawTextWithShadow(net.minecraft.client.MinecraftClient.getInstance().textRenderer,
                Text.literal("State: " + ai.getState()), x, y + 14, 0xFFFFFF);
        var target = ai.getTarget();
        context.drawTextWithShadow(net.minecraft.client.MinecraftClient.getInstance().textRenderer,
                Text.literal("Target: " + (target == null ? "None" : target.getName().getString())), x, y + 28, 0xFFFFFF);
        context.drawTextWithShadow(net.minecraft.client.MinecraftClient.getInstance().textRenderer,
                Text.literal("Goal: " + ai.getGoals().getGoal() + "  " + ai.getGoals().getProgress() + "%"), x, y + 42, 0xB8C7D9);
    }
}
