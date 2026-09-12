package com.otongheng.aiplayer.movement;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public final class MovementController {
    public void approach(MinecraftClient client, ClientPlayerEntity player, LivingEntity target, double stopRange) {
        double dx = target.getX() - player.getX();
        double dz = target.getZ() - player.getZ();
        float desired = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        float delta = MathHelper.wrapDegrees(desired - player.getYaw());
        player.setYaw(player.getYaw() + MathHelper.clamp(delta, -10.0f, 10.0f));
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        boolean move = horizontal > stopRange;
        client.options.forwardKey.setPressed(move);
        client.options.leftKey.setPressed(false);
        client.options.rightKey.setPressed(false);
        client.options.backKey.setPressed(false);
        client.options.jumpKey.setPressed(false);
        player.setSprinting(move && horizontal > 5.0);
    }

    public void evade(MinecraftClient client, ClientPlayerEntity player, LivingEntity threat) {
        double dx = player.getX() - threat.getX();
        double dz = player.getZ() - threat.getZ();
        float desired = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        float delta = MathHelper.wrapDegrees(desired - player.getYaw());
        player.setYaw(player.getYaw() + MathHelper.clamp(delta, -14.0f, 14.0f));
        client.options.forwardKey.setPressed(true);
        client.options.jumpKey.setPressed(player.isOnGround());
        player.setSprinting(true);
    }

    public void stop(MinecraftClient client) {
        client.options.forwardKey.setPressed(false);
        client.options.backKey.setPressed(false);
        client.options.leftKey.setPressed(false);
        client.options.rightKey.setPressed(false);
        client.options.jumpKey.setPressed(false);
        if (client.player != null) client.player.setSprinting(false);
    }
}
