package net.kurayzumi.crosshairindicator;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;

public class CrosshairIndicatorClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        HudElementRegistry.attachElementAfter(
                VanillaHudElements.CROSSHAIR,
                Identifier.of(CrosshairIndicator.MOD_ID, "hud-crosshair-layer"),
                CrosshairIndicatorClient::render);
	}

    private static void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;

        HitResult crosshairTarget = client.crosshairTarget;

        if (crosshairTarget == null) return;
        if (crosshairTarget.getType() != HitResult.Type.ENTITY) return;

        Identifier texture = Identifier.of(CrosshairIndicator.MOD_ID,
                "textures/gui/sprites/hud/crosshair_red.png");

        context.drawTexture(
                RenderPipelines.GUI_TEXTURED,
                texture,
                ((context.getScaledWindowWidth() - 15) / 2),
                ((context.getScaledWindowHeight() - 15) / 2),
                0, 0,
                15, 15,
                15, 15
        );
    }
}