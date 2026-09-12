package com.otongheng.aiplayer.ai;

import com.otongheng.aiplayer.OtonghengAIPlayerClient;
import com.otongheng.aiplayer.combat.CombatEngine;
import com.otongheng.aiplayer.config.AIConfig;
import com.otongheng.aiplayer.equipment.EquipmentManager;
import com.otongheng.aiplayer.goals.GoalManager;
import com.otongheng.aiplayer.memory.AIMemory;
import com.otongheng.aiplayer.movement.MovementController;
import com.otongheng.aiplayer.survival.SurvivalManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;

public final class AIController {
    private boolean running;
    private boolean paused;
    private AIState state = AIState.IDLE;
    private LivingEntity target;
    private int thinkCooldown;
    private long ticks;
    private final Perception perception = new Perception(AIConfig.detectionRange);
    private final MovementController movement = new MovementController();
    private final CombatEngine combat = new CombatEngine();
    private final SurvivalManager survival = new SurvivalManager();
    private final EquipmentManager equipment = new EquipmentManager();
    private final GoalManager goals = new GoalManager();
    private final AIMemory memory = new AIMemory();

    public void toggle(MinecraftClient client) {
        running = !running; paused = false;
        if (!running) { state = AIState.IDLE; movement.stop(client); }
        else { state = AIState.OBSERVE; memory.remember("AI started"); }
        OtonghengAIPlayerClient.toast(client, running ? "AI STARTED" : "AI STOPPED");
    }

    public void togglePause(MinecraftClient client) {
        if (!running) return;
        paused = !paused;
        state = paused ? AIState.PAUSED : AIState.OBSERVE;
        if (paused) movement.stop(client);
        OtonghengAIPlayerClient.toast(client, paused ? "AI PAUSED" : "AI RESUMED");
    }

    public void emergencyStop(MinecraftClient client) {
        running = false; paused = false; target = null; state = AIState.EMERGENCY;
        movement.stop(client); memory.remember("Emergency stop");
        OtonghengAIPlayerClient.toast(client, "EMERGENCY STOP");
    }

    public void tick(MinecraftClient client) {
        ticks++;
        if (!running || paused || client.player == null || client.world == null || client.currentScreen != null) {
            if (!running || paused) movement.stop(client);
            return;
        }
        ClientPlayerEntity player = client.player;
        if (survival.critical(player) && AIConfig.autoSurvival) {
            state = AIState.RECOVER;
            movement.evade(client, player, target != null ? target : player);
            return;
        }
        if (thinkCooldown-- <= 0) {
            target = perception.nearestThreat(player);
            thinkCooldown = Math.max(1, AIConfig.thinkInterval);
            state = target == null ? AIState.TRAVEL : AIState.APPROACH;
        }
        if (target == null || !target.isAlive() || target.isRemoved()) {
            state = AIState.OBSERVE; movement.stop(client); return;
        }
        double distance = player.distanceTo(target);
        if (distance <= AIConfig.combatRange) {
            state = AIState.COMBAT;
            int slot = equipment.findBestCombatSlot(player);
            if (slot >= 0) player.getInventory().setSelectedSlot(slot);
            combat.tick(client, player, target);
        } else {
            state = AIState.APPROACH;
            movement.approach(client, player, target, AIConfig.combatRange);
        }
        if (ticks % 20 == 0) goals.advance();
    }

    public boolean isRunning() { return running; }
    public boolean isPaused() { return paused; }
    public LivingEntity getTarget() { return target; }
    public AIState getState() { return state; }
    public GoalManager getGoals() { return goals; }
    public AIMemory getMemory() { return memory; }
    public EquipmentManager getEquipment() { return equipment; }
}
