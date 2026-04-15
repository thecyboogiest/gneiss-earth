package com.cyboogiest.gneissearth.client;

import com.cyboogiest.gneissearth.client.render.MineralOreModelPlugin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;

public class GneissEarthClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModelLoadingPlugin.register(new MineralOreModelPlugin());
	}
}