package com.cyboogiest.gneissearth.client.render;

import net.fabricmc.fabric.api.client.model.loading.v1.wrapper.WrapperBlockStateModel;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.client.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import org.jspecify.annotations.Nullable;

import java.util.function.Predicate;

public class MineralOreBlockStateModel extends WrapperBlockStateModel {

    // Must match MineralLayersMixin.getMineralForY
    private static final int[] Y_THRESHOLDS = { 10, 12, 18, 25, 39, 45, 60 };
    private static final Identifier[] MINERAL_IDS = {
        Identifier.fromNamespaceAndPath("gneiss-earth", "block/amphibole"),
        Identifier.fromNamespaceAndPath("gneiss-earth", "block/olivine"),
        Identifier.fromNamespaceAndPath("gneiss-earth", "block/gabbro"),
        Identifier.fromNamespaceAndPath("gneiss-earth", "block/mica"),
        Identifier.fromNamespaceAndPath("gneiss-earth", "block/feldspar"),
        Identifier.fromNamespaceAndPath("gneiss-earth", "block/talc"),
        Identifier.fromNamespaceAndPath("gneiss-earth", "block/kaolinite"),
    };
    private static final Identifier BLOCK_ATLAS =
            Identifier.fromNamespaceAndPath("minecraft", "blocks");

    private final Identifier overlayId;

    // Lazy-cached after atlas stitching
    private Material.Baked[] mineralMaterials;
    private Material.Baked overlayMaterial;

    public MineralOreBlockStateModel(String overlayName, BlockStateModel original) {
        super(original);
        this.overlayId = Identifier.fromNamespaceAndPath("gneiss-earth", "block/" + overlayName);
    }

    @Override
    public void emitQuads(QuadEmitter emitter, BlockAndTintGetter level, BlockPos pos,
                          BlockState state, RandomSource random, Predicate<@Nullable Direction> cullTest) {
        Material.Baked background = getMineralMaterial(pos.getY());
        if (background == null) {
            // Above all mineral layers — fall back to vanilla rendering
            super.emitQuads(emitter, level, pos, state, random, cullTest);
            return;
        }

        Material.Baked overlay = getOverlayMaterial();

        for (Direction face : Direction.values()) {
            // Background — solid mineral texture
            emitter.square(face, 0f, 0f, 1f, 1f, 0f);
            emitter.materialBake(background, MutableQuadView.BAKE_LOCK_UV);
            emitter.color(-1, -1, -1, -1);
            emitter.emit();

            // Overlay — ore texture on cutout layer so transparent pixels show the background
            emitter.square(face, 0f, 0f, 1f, 1f, 0f);
            emitter.chunkLayer(ChunkSectionLayer.CUTOUT); // must be before materialBake
            emitter.materialBake(overlay, MutableQuadView.BAKE_LOCK_UV);
            emitter.color(-1, -1, -1, -1);
            emitter.emit();
        }
    }

    private Material.Baked getMineralMaterial(int y) {
        if (mineralMaterials == null) {
            var atlas = Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(BLOCK_ATLAS);
            mineralMaterials = new Material.Baked[MINERAL_IDS.length];
            for (int i = 0; i < MINERAL_IDS.length; i++) {
                mineralMaterials[i] = new Material.Baked(atlas.getSprite(MINERAL_IDS[i]), false);
            }
        }
        for (int i = 0; i < Y_THRESHOLDS.length; i++) {
            if (y < Y_THRESHOLDS[i]) return mineralMaterials[i];
        }
        return null;
    }

    private Material.Baked getOverlayMaterial() {
        if (overlayMaterial == null) {
            var atlas = Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(BLOCK_ATLAS);
            overlayMaterial = new Material.Baked(atlas.getSprite(overlayId), false);
        }
        return overlayMaterial;
    }
}
