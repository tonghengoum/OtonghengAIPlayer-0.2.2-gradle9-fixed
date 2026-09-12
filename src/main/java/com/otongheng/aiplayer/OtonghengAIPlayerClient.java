package com.otongheng.aiplayer;

import com.otongheng.aiplayer.ai.AIController;
import com.otongheng.aiplayer.config.AIConfig;
import com.otongheng.aiplayer.ui.AIControlScreen;
import com.otongheng.aiplayer.hud.AIHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class OtonghengAIPlayerClient implements ClientModInitializer {
    public static final String MOD_ID = "otongheng-ai-player";
    public static AIController AI;
    private static KeyBinding startStop;
    private static KeyBinding pause;
    private static KeyBinding emergency;
    private static KeyBinding gui;

    @Override
    public void onInitializeClient() {
        AIConfig.load();
        AI = new AIController();
        AIHud.register();

        startStop = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.otongheng.ai.start_stop", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6, "O.Tongheng AI"));
        pause = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.otongheng.ai.pause", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F7, "O.Tongheng AI"));
        emergency = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.otongheng.ai.emergency", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F8, "O.Tongheng AI"));
        gui = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.otongheng.ai.gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "O.Tongheng AI"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (startStop.wasPressed()) AI.toggle(client);
            while (pause.wasPressed()) AI.togglePause(client);
            while (emergency.wasPressed()) AI.emergencyStop(client);
            while (gui.wasPressed()) client.setScreen(new AIControlScreen());
            AI.tick(client);
        });
    }

    public static void toast(MinecraftClient client, String message) {
        if (client.player != null) client.player.sendMessage(Text.literal("§8[§bO.Tongheng AI§8] §f" + message), true);
    }
}
