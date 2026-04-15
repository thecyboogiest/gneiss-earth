package com.cyboogiest.gneissearth.client.render;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.HashMap;
import java.util.Map;

public class MineralOreModelPlugin implements ModelLoadingPlugin {

    private static final Map<Block, String> ORE_OVERLAYS;
    static {
        ORE_OVERLAYS = new HashMap<>();
        ORE_OVERLAYS.put(Blocks.COAL_ORE,     "coal_ore_overlay");
        ORE_OVERLAYS.put(Blocks.IRON_ORE,     "iron_ore_overlay");
        ORE_OVERLAYS.put(Blocks.GOLD_ORE,     "gold_ore_overlay");
        ORE_OVERLAYS.put(Blocks.COPPER_ORE,   "copper_ore_overlay");
        ORE_OVERLAYS.put(Blocks.REDSTONE_ORE, "redstone_ore_overlay"); // covers lit=true and lit=false
        ORE_OVERLAYS.put(Blocks.LAPIS_ORE,    "lapis_ore_overlay");
        ORE_OVERLAYS.put(Blocks.DIAMOND_ORE,  "diamond_ore_overlay");
        ORE_OVERLAYS.put(Blocks.EMERALD_ORE,  "emerald_ore_overlay");
    }

    @Override
    public void initialize(Context ctx) {
        ctx.modifyBlockModelAfterBake().register((model, context) -> {
            String overlayName = ORE_OVERLAYS.get(context.state().getBlock());
            if (overlayName == null) return model;
            return new MineralOreBlockStateModel(overlayName, model);
        });
    }
}
