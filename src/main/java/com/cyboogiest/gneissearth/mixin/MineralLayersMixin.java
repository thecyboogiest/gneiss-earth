package com.cyboogiest.gneissearth.mixin;

import com.cyboogiest.gneissearth.block.ModBlocks;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NoiseBasedChunkGenerator.class)
public class MineralLayersMixin {

    // One noise period every ~333 blocks; ±35 block Y shift at the layer boundaries.
    @Unique private static final double NOISE_SCALE     = 0.03;
    @Unique private static final double NOISE_AMPLITUDE = 35.0;

    @Inject(method = "buildSurface", at = @At("RETURN"), require = 1)
    private void applyMineralLayers(CallbackInfo ci,
                                     @Local(argsOnly = true, name = "protoChunk") ChunkAccess chunk) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        int minX = chunk.getPos().getMinBlockX();
        int minZ = chunk.getPos().getMinBlockZ();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = minX + x;
                int worldZ = minZ + z;
                // Sample noise once per column and apply as a Y shift to all boundaries
                int yOffset = (int) (layerNoise(worldX, worldZ) * NOISE_AMPLITUDE);

                for (int y = chunk.getMinY(); y < chunk.getMaxY(); y++) {
                    pos.set(worldX, y, worldZ);
                    BlockState current = chunk.getBlockState(pos);
                    if (current.is(BlockTags.STONE_ORE_REPLACEABLES)) {
                        BlockState mineral = getMineralForY(y - yOffset);
                        if (mineral != null) {
                            chunk.setBlockState(pos, mineral, 0);
                        }
                    }
                }
            }
        }
    }

    /**
     * Smooth value noise in -1..1, one period every 1/NOISE_SCALE blocks.
     * Uses bilinear interpolation with smoothstep on a hashed integer grid.
     */
    @Unique
    private static double layerNoise(int worldX, int worldZ) {
        double fx = worldX * NOISE_SCALE;
        double fz = worldZ * NOISE_SCALE;
        int ix = (int) Math.floor(fx);
        int iz = (int) Math.floor(fz);
        double rx = fx - ix;
        double rz = fz - iz;
        // Smoothstep — avoids visible grid lines at cell boundaries
        rx = rx * rx * (3.0 - 2.0 * rx);
        rz = rz * rz * (3.0 - 2.0 * rz);
        double v00 = hash2(ix,     iz);
        double v10 = hash2(ix + 1, iz);
        double v01 = hash2(ix,     iz + 1);
        double v11 = hash2(ix + 1, iz + 1);
        double top    = v00 + rx * (v10 - v00);
        double bottom = v01 + rx * (v11 - v01);
        return (top + rz * (bottom - top)) * 2.0 - 1.0; // remap 0..1 → -1..1
    }

    /** Avalanche hash for a 2D integer grid point, returns 0..1. */
    @Unique
    private static double hash2(int x, int z) {
        long n = (long) x * 1619L + (long) z * 31337L + 0x4B5F2A1C3D8E6FL;
        n ^= n >>> 17;
        n *= 0x45d9f3b37197344dL;
        n ^= n >>> 16;
        return (n & 0x7FFFFFFFFFFFFFFFL) / (double) 0x7FFFFFFFFFFFFFFFL;
    }

    @Unique
    private static BlockState getMineralForY(int y) {
        if (y < 10)  return ModBlocks.Amphibole.defaultBlockState(); // -64 to  10
        if (y < 12)  return ModBlocks.Olivine.defaultBlockState();   //  10 to  12
        if (y < 18)  return ModBlocks.Gabbro.defaultBlockState();    //  12 to  18
        if (y < 25)  return ModBlocks.Mica.defaultBlockState();      //  18 to  25
        if (y < 39)  return ModBlocks.Feldspar.defaultBlockState();  //  25 to  39
        if (y < 45)  return ModBlocks.Talc.defaultBlockState();      //  39 to  45
        if (y < 60)  return ModBlocks.Kaolinite.defaultBlockState(); //  45 to  60
        return null;
    }
}
