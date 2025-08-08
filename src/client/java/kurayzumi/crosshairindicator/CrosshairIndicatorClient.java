package kurayzumi.crosshairindicator;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult;

public class CrosshairIndicatorClient implements ClientModInitializer {

	private static final Identifier CROSSHAIR_LAYER = Identifier.of(CrosshairIndicator.MOD_ID, "hud-crosshair-layer");
	private static final Identifier RED_CROSSHAIR_TEXTURE =
			Identifier.of(CrosshairIndicator.MOD_ID, "textures/gui/sprites/hud/crosshair.png");

	@Override
	public void onInitializeClient() {
		HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerAfter(
				IdentifiedLayer.CROSSHAIR, CROSSHAIR_LAYER, CrosshairIndicatorClient::render));
	}

	private static void render(DrawContext context, RenderTickCounter tickCounter) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client == null) return;

		HitResult crosshairTarget = client.crosshairTarget;

		if (crosshairTarget == null) return;
		if (crosshairTarget.getType() != HitResult.Type.ENTITY) return;

		context.drawTexture(
				RenderLayer::getGuiTextured,
				RED_CROSSHAIR_TEXTURE,
				((context.getScaledWindowWidth() - 14) / 2) - 1,
				((context.getScaledWindowHeight() - 14) / 2) - 1,
				0, 0,
				15, 15,
				15, 15
		);
	}
}