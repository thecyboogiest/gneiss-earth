package com.cyboogiest.gneissearth.block;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Map;

public class ModBlocks {
    public static final Block Amphibole = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "amphibole"))).strength(1.8f, 6.0f).requiresCorrectToolForDrops());
    public static final Block Talc = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "talc"))).strength(1.5f, 6.0f).requiresCorrectToolForDrops());
    public static final Block Mica = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "mica"))).strength(1.5f, 6.0f).requiresCorrectToolForDrops());
    public static final Block Feldspar = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "feldspar"))).strength(1.5f, 6.0f).requiresCorrectToolForDrops());
    public static final Block Kaolinite = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "kaolinite"))).strength(1.5f, 6.0f).requiresCorrectToolForDrops());
    public static final Block Olivine = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "olivine"))).strength(1.8f, 6.0f).requiresCorrectToolForDrops());
    public static final Block Gabbro = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "gabbro"))).strength(1.5f, 6.0f).requiresCorrectToolForDrops());
    //Bricks
    public static final Block Amphibole_bricks = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "amphibole_bricks"))).strength(1.8f, 6.0f).requiresCorrectToolForDrops());
    public static final Block Talc_bricks = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "talc_bricks"))).strength(1.5f, 6.0f).requiresCorrectToolForDrops());
    public static final Block Mica_bricks = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "mica_bricks"))).strength(1.5f, 6.0f).requiresCorrectToolForDrops());
    public static final Block Feldspar_bricks = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "feldspar_bricks"))).strength(1.5f, 6.0f).requiresCorrectToolForDrops());
    public static final Block Kaolinite_bricks = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "kaolinite_bricks"))).strength(1.5f, 6.0f).requiresCorrectToolForDrops());
    public static final Block Olivine_bricks = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "olivine_bricks"))).strength(1.8f, 6.0f).requiresCorrectToolForDrops());
    public static final Block Gabbro_bricks = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "gabbro_bricks"))).strength(1.5f, 6.0f).requiresCorrectToolForDrops());

    public static final Block Amphibole_brick_stairs = new StairBlock(Amphibole_bricks.defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Amphibole_bricks).setId(ResourceKey.create(Registries.BLOCK,Identifier.fromNamespaceAndPath("gneiss-earth","amphibole_brick_stairs"))));
    public static final Block Talc_brick_stairs = new StairBlock(Talc_bricks.defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Talc_bricks).setId(ResourceKey.create(Registries.BLOCK,Identifier.fromNamespaceAndPath("gneiss-earth","talc_brick_stairs"))));
    public static final Block Mica_brick_stairs = new StairBlock(Mica_bricks.defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Mica_bricks).setId(ResourceKey.create(Registries.BLOCK,Identifier.fromNamespaceAndPath("gneiss-earth","mica_brick_stairs"))));
    public static final Block Feldspar_brick_stairs = new StairBlock(Feldspar_bricks.defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Feldspar_bricks).setId(ResourceKey.create(Registries.BLOCK,Identifier.fromNamespaceAndPath("gneiss-earth","feldspar_brick_stairs"))));
    public static final Block Kaolinite_brick_stairs = new StairBlock(Kaolinite_bricks.defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Kaolinite_bricks).setId(ResourceKey.create(Registries.BLOCK,Identifier.fromNamespaceAndPath("gneiss-earth","kaolinite_brick_stairs"))));
    public static final Block Olivine_brick_stairs = new StairBlock(Olivine_bricks.defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Olivine_bricks).setId(ResourceKey.create(Registries.BLOCK,Identifier.fromNamespaceAndPath("gneiss-earth","olivine_brick_stairs"))));
    public static final Block Gabbro_brick_stairs = new StairBlock(Gabbro_bricks.defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Gabbro_bricks).setId(ResourceKey.create(Registries.BLOCK,Identifier.fromNamespaceAndPath("gneiss-earth","gabbro_brick_stairs"))));
    //Brick Slabs
    public static final Block Amphibole_brick_slab = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Amphibole_bricks).setId(ResourceKey.create(Registries.BLOCK,Identifier.fromNamespaceAndPath("gneiss-earth","amphibole_brick_slab"))));
    public static final Block Talc_brick_slab = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Talc_bricks).setId(ResourceKey.create(Registries.BLOCK,Identifier.fromNamespaceAndPath("gneiss-earth","talc_brick_slab"))));
    public static final Block Mica_brick_slab = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Mica_bricks).setId(ResourceKey.create(Registries.BLOCK,Identifier.fromNamespaceAndPath("gneiss-earth","mica_brick_slab"))));
    public static final Block Feldspar_brick_slab = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Feldspar_bricks).setId(ResourceKey.create(Registries.BLOCK,Identifier.fromNamespaceAndPath("gneiss-earth","feldspar_brick_slab"))));
    public static final Block Kaolinite_brick_slab = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Kaolinite_bricks).setId(ResourceKey.create(Registries.BLOCK,Identifier.fromNamespaceAndPath("gneiss-earth","kaolinite_brick_slab"))));
    public static final Block Olivine_brick_slab = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Olivine_bricks).setId(ResourceKey.create(Registries.BLOCK,Identifier.fromNamespaceAndPath("gneiss-earth","olivine_brick_slab"))));
    public static final Block Gabbro_brick_slab = new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Gabbro_bricks).setId(ResourceKey.create(Registries.BLOCK,Identifier.fromNamespaceAndPath("gneiss-earth","gabbro_brick_slab"))));

    public static void register(){
        Map<String, Block> blocks = Map.ofEntries(
                Map.entry("amphibole", Amphibole),
                Map.entry("talc", Talc),
                Map.entry("mica", Mica),
                Map.entry("feldspar", Feldspar),
                Map.entry("kaolinite", Kaolinite),
                Map.entry("olivine", Olivine),
                Map.entry("gabbro", Gabbro),
                Map.entry("amphibole_bricks", Amphibole_bricks),
                Map.entry("talc_bricks", Talc_bricks),
                Map.entry("mica_bricks", Mica_bricks),
                Map.entry("feldspar_bricks", Feldspar_bricks),
                Map.entry("kaolinite_bricks", Kaolinite_bricks),
                Map.entry("olivine_bricks", Olivine_bricks),
                Map.entry("gabbro_bricks", Gabbro_bricks),
                Map.entry("amphibole_brick_stairs", Amphibole_brick_stairs),
                Map.entry("talc_brick_stairs", Talc_brick_stairs),
                Map.entry("mica_brick_stairs", Mica_brick_stairs),
                Map.entry("feldspar_brick_stairs", Feldspar_brick_stairs),
                Map.entry("kaolinite_brick_stairs", Kaolinite_brick_stairs),
                Map.entry("olivine_brick_stairs", Olivine_brick_stairs),
                Map.entry("gabbro_brick_stairs", Gabbro_brick_stairs),
                Map.entry("amphibole_brick_slab", Amphibole_brick_slab),
                Map.entry("talc_brick_slab", Talc_brick_slab),
                Map.entry("mica_brick_slab", Mica_brick_slab),
                Map.entry("feldspar_brick_slab", Feldspar_brick_slab),
                Map.entry("kaolinite_brick_slab", Kaolinite_brick_slab),
                Map.entry("olivine_brick_slab", Olivine_brick_slab),
                Map.entry("gabbro_brick_slab", Gabbro_brick_slab)
        );

        for (String key : blocks.keySet()) {
            Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", key), blocks.get(key));
            Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath("gneiss-earth", key), new BlockItem(blocks.get(key), new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
                    Identifier.fromNamespaceAndPath("gneiss-earth", key))) ));
        }

        ResourceKey<CreativeModeTab> naturalBlocks = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
                Identifier.withDefaultNamespace("natural_blocks"));
        ResourceKey<CreativeModeTab> buildingBlocks = ResourceKey.create(Registries.CREATIVE_MODE_TAB,
                Identifier.withDefaultNamespace("building_blocks"));

        CreativeModeTabEvents.modifyOutputEvent(naturalBlocks).register(entries -> {
            entries.prepend(Amphibole);
            entries.prepend(Talc);
            entries.prepend(Mica);
            entries.prepend(Feldspar);
            entries.prepend(Kaolinite);
            entries.prepend(Olivine);
            entries.prepend(Gabbro);
        });
        CreativeModeTabEvents.modifyOutputEvent(buildingBlocks).register(entries -> {
            entries.prepend(Amphibole_bricks);
            entries.prepend(Amphibole_brick_stairs);
            entries.prepend(Amphibole_brick_slab);
            entries.prepend(Talc_bricks);
            entries.prepend(Talc_brick_stairs);
            entries.prepend(Talc_brick_slab);
            entries.prepend(Mica_bricks);
            entries.prepend(Mica_brick_stairs);
            entries.prepend(Mica_brick_slab);
            entries.prepend(Feldspar_bricks);
            entries.prepend(Feldspar_brick_stairs);
            entries.prepend(Feldspar_brick_slab);
            entries.prepend(Kaolinite_bricks);
            entries.prepend(Kaolinite_brick_stairs);
            entries.prepend(Kaolinite_brick_slab);
            entries.prepend(Olivine_bricks);
            entries.prepend(Olivine_brick_stairs);
            entries.prepend(Olivine_brick_slab);
            entries.prepend(Gabbro_bricks);
            entries.prepend(Gabbro_brick_stairs);
            entries.prepend(Gabbro_brick_slab);
        });
    }
}
