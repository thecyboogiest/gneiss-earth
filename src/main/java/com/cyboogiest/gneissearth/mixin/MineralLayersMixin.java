package com.cyboogiest.gneissearth.mixin;

import com.cyboogiest.gneissearth.block.ModBlocks;
import com.cyboogiest.gneissearth.worldgen.MineralLayers;
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
                int yOffset = MineralLayers.yOffset(worldX, worldZ);

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