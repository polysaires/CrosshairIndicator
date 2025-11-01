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

    // temporary till cloth config integration
    private static final boolean ONLY_HIGHLIGHT_IN_FIRST_PERSON = true;

	@Override
	public void onInitializeClient() {
        HudElementRegistry.attachElementAfter(
                VanillaHudElements.CROSSHAIR,
                Identifier.of(CrosshairIndicator.MOD_ID, "crosshair-highlight"),
                CrosshairIndicatorClient::renderHighlight);
	}

    private static void renderHighlight(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;

        if (!shouldHighlight(client)) return;

        Identifier texture = Identifier.of(
                CrosshairIndicator.MOD_ID,
                "textures/gui/sprites/hud/crosshair_highlighted.png"
        );

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

    private static boolean shouldHighlight(MinecraftClient client) {
        HitResult crosshairTarget = client.crosshairTarget;
        if (crosshairTarget == null) return false;
        if (crosshairTarget.getType() != HitResult.Type.ENTITY) return false;

        if (ONLY_HIGHLIGHT_IN_FIRST_PERSON &&
                !client.options.getPerspective().isFirstPerson()) return false;

        return true;
    }
}