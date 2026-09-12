package com.otongheng.aiplayer.ai;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;

import java.util.Comparator;

public final class Perception {
    private final double range;
    public Perception(double range) { this.range = range; }

    public LivingEntity nearestThreat(ClientPlayerEntity player) {
        return player.getWorld().getEntitiesByClass(HostileEntity.class,
                player.getBoundingBox().expand(range),
                e -> e.isAlive() && !e.isRemoved())
                .stream().min(Comparator.comparingDouble(player::squaredDistanceTo)).orElse(null);
    }

    public boolean dangerClose(ClientPlayerEntity player, double distance) {
        return player.getWorld().getEntitiesByClass(HostileEntity.class,
                player.getBoundingBox().expand(distance),
                e -> e.isAlive() && !e.isRemoved()).stream().findAny().isPresent();
    }
}
