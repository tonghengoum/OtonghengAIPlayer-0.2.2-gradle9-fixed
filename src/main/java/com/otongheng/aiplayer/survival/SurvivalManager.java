package com.otongheng.aiplayer.survival;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;

public final class SurvivalManager {
    public boolean critical(ClientPlayerEntity player) {
        return player.getHealth() <= Math.max(4.0f, player.getMaxHealth() * 0.25f);
    }

    public boolean hasTotem(ClientPlayerEntity player) {
        if (player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) return true;
        for (int i = 0; i < player.getInventory().size(); i++)
            if (player.getInventory().getStack(i).isOf(Items.TOTEM_OF_UNDYING)) return true;
        return false;
    }

    public boolean shouldRetreat(ClientPlayerEntity player) {
        return critical(player) && !hasTotem(player);
    }

    public void emergencyRetreat(MinecraftClient client) {
        client.options.jumpKey.setPressed(true);
        client.options.forwardKey.setPressed(true);
        if (client.player != null) client.player.setSprinting(true);
    }
}
