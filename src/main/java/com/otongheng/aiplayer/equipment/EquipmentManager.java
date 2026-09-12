package com.otongheng.aiplayer.equipment;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public final class EquipmentManager {
    public int findTotem(ClientPlayerEntity player) {
        for (int i = 0; i < player.getInventory().size(); i++) {
            if (player.getInventory().getStack(i).isOf(Items.TOTEM_OF_UNDYING)) return i;
        }
        return -1;
    }

    public int findBestCombatSlot(ClientPlayerEntity player) {
        int best = player.getInventory().getSelectedSlot();
        int bestScore = score(player.getInventory().getStack(best));
        for (int i = 0; i < 9; i++) {
            int score = score(player.getInventory().getStack(i));
            if (score > bestScore) { best = i; bestScore = score; }
        }
        return best;
    }

    private int score(ItemStack stack) {
        if (stack.isEmpty()) return 0;
        if (stack.isOf(Items.MACE)) return 100;
        if (stack.isOf(Items.NETHERITE_SWORD)) return 90;
        if (stack.isOf(Items.DIAMOND_SWORD)) return 80;
        if (stack.isOf(Items.NETHERITE_AXE)) return 75;
        if (stack.isOf(Items.DIAMOND_AXE)) return 65;
        return 10;
    }

    public int durabilityPercent(ItemStack stack) {
        if (!stack.isDamageable()) return 100;
        int max = stack.getMaxDamage();
        if (max <= 0) return 100;
        return Math.max(0, (max - stack.getDamage()) * 100 / max);
    }
}
