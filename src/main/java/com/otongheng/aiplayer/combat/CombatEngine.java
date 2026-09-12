package com.otongheng.aiplayer.combat;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;

public final class CombatEngine {
    private int cooldown;

    public void tick(MinecraftClient client, ClientPlayerEntity player, LivingEntity target) {
        if (cooldown > 0) cooldown--;
        if (target == null || !target.isAlive() || client.interactionManager == null) return;
        double distance = player.distanceTo(target);
        if (distance > 3.25 || cooldown > 0) return;

        if (player.getInventory().getSelectedStack().isEmpty()) {
            for (int i = 0; i < 9; i++) {
                if (player.getInventory().getStack(i).isOf(Items.MACE) ||
                    player.getInventory().getStack(i).isOf(Items.NETHERITE_SWORD) ||
                    player.getInventory().getStack(i).isOf(Items.DIAMOND_SWORD)) {
                    player.getInventory().setSelectedSlot(i);
                    break;
                }
            }
        }

        float cooldownProgress = player.getAttackCooldownProgress(0.0f);
        if (cooldownProgress >= 0.92f) {
            client.interactionManager.attackEntity(player, target);
            player.swingHand(net.minecraft.util.Hand.MAIN_HAND);
            cooldown = 2;
        }
    }
}
