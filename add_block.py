#!/usr/bin/env python3
"""
Usage:
    python add_block.py <block_name> [options]

Options:
    --display-name "My Block"   Human-readable name (default: title-cased block_name)
    --strength 1.5              Block hardness (default: 1.5)
    --resistance 6.0            Blast resistance (default: 6.0)
    --drop self                 What the block drops without silk touch: "self" or a namespaced
                                item id like "minecraft:cobblestone" (default: minecraft:cobblestone)
    --layer-min-y -64           Bottom of this block's Y layer (default: -64)
    --layer-max-y 20            Top of this block's Y layer (default: 20)
    --no-layer                  Skip worldgen layer file generation

Examples:
    python add_block.py quartz --layer-min-y 0 --layer-max-y 30
    python add_block.py marble --drop self --no-layer
"""

import argparse
import json
import os

MOD_ID = "gneiss-earth"
MOD_NAMESPACE = "gneiss-earth"
BASE = os.path.dirname(os.path.abspath(__file__))
ASSETS = os.path.join(BASE, "src/main/resources/assets", MOD_NAMESPACE)
DATA   = os.path.join(BASE, "src/main/resources/data")
MOD_DATA = os.path.join(DATA, MOD_NAMESPACE)
MINECRAFT_DATA = os.path.join(DATA, "minecraft")


def write_json(path, data, dry_run):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    content = json.dumps(data, indent=2)
    if dry_run:
        print(f"  [DRY RUN] Would write: {os.path.relpath(path, BASE)}")
    else:
        with open(path, "w") as f:
            f.write(content + "\n")
        print(f"  Created: {os.path.relpath(path, BASE)}")


def patch_json(path, updater_fn, dry_run):
    """Load a JSON file, apply updater_fn to its data, write it back."""
    with open(path) as f:
        data = json.load(f)
    updater_fn(data)
    content = json.dumps(data, indent=2)
    if dry_run:
        print(f"  [DRY RUN] Would update: {os.path.relpath(path, BASE)}")
    else:
        with open(path, "w") as f:
            f.write(content + "\n")
        print(f"  Updated: {os.path.relpath(path, BASE)}")


def main():
    parser = argparse.ArgumentParser(description="Add a new block to gneiss-earth mod")
    parser.add_argument("block_name", help="Snake_case block name (e.g. pyrite)")
    parser.add_argument("--display-name", default=None, help="Human-readable name")
    parser.add_argument("--strength", type=float, default=1.5)
    parser.add_argument("--resistance", type=float, default=6.0)
    parser.add_argument("--drop", default="minecraft:cobblestone",
                        help='"self" or a namespaced item id')
    parser.add_argument("--layer-min-y", type=int, default=-64)
    parser.add_argument("--layer-max-y", type=int, default=20)
    parser.add_argument("--no-layer", action="store_true")
    parser.add_argument("--dry-run", action="store_true")
    args = parser.parse_args()

    name = args.block_name.lower()
    display_name = args.display_name or name.replace("_", " ").title()
    drop_item = f"{MOD_NAMESPACE}:{name}" if args.drop == "self" else args.drop
    ns_name = f"{MOD_NAMESPACE}:{name}"

    print(f"\n=== Adding block '{name}' ('{display_name}') ===\n")

    # --- Blockstate ---
    write_json(
        os.path.join(ASSETS, "blockstates", f"{name}.json"),
        {"variants": {"": {"model": f"{MOD_NAMESPACE}:block/{name}"}}},
        args.dry_run,
    )

    # --- Block model ---
    write_json(
        os.path.join(ASSETS, "models/block", f"{name}.json"),
        {"parent": "minecraft:block/cube_all", "textures": {"all": f"{MOD_NAMESPACE}:block/{name}"}},
        args.dry_run,
    )

    # --- Item display model ---
    write_json(
        os.path.join(ASSETS, "items", f"{name}.json"),
        {"model": {"type": "minecraft:model", "model": f"{MOD_NAMESPACE}:block/{name}"}},
        args.dry_run,
    )

    # --- Loot table ---
    if drop_item == ns_name:
        loot = {
            "type": "minecraft:block",
            "pools": [{
                "rolls": 1.0,
                "bonus_rolls": 0.0,
                "entries": [{
                    "type": "minecraft:item",
                    "conditions": [{"condition": "minecraft:survives_explosion"}],
                    "name": ns_name,
                }],
            }],
            "random_sequence": f"{MOD_NAMESPACE}:blocks/{name}",
        }
    else:
        loot = {
            "type": "minecraft:block",
            "pools": [{
                "rolls": 1.0,
                "bonus_rolls": 0.0,
                "entries": [{
                    "type": "minecraft:alternatives",
                    "children": [
                        {
                            "type": "minecraft:item",
                            "conditions": [{
                                "condition": "minecraft:match_tool",
                                "predicate": {
                                    "predicates": {
                                        "minecraft:enchantments": [{
                                            "enchantments": "minecraft:silk_touch",
                                            "levels": {"min": 1},
                                        }]
                                    }
                                },
                            }],
                            "name": ns_name,
                        },
                        {
                            "type": "minecraft:item",
                            "conditions": [{"condition": "minecraft:survives_explosion"}],
                            "name": drop_item,
                        },
                    ],
                }],
            }],
            "random_sequence": f"{MOD_NAMESPACE}:blocks/{name}",
        }
    write_json(
        os.path.join(MOD_DATA, "loot_table/blocks", f"{name}.json"),
        loot,
        args.dry_run,
    )

    # --- Pickaxe tag ---
    pickaxe_tag = os.path.join(MINECRAFT_DATA, "tags/block/mineable/pickaxe.json")
    def add_to_pickaxe(data):
        if ns_name not in data["values"]:
            data["values"].append(ns_name)
    patch_json(pickaxe_tag, add_to_pickaxe, args.dry_run)

    # --- Lang file ---
    lang_file = os.path.join(ASSETS, "lang/en_us.json")
    block_lang_key = f"block.{MOD_NAMESPACE}.{name}"
    item_lang_key  = f"item.{MOD_NAMESPACE}.{name}"
    def add_lang(data):
        if block_lang_key not in data:
            data[block_lang_key] = display_name
        if item_lang_key not in data:
            data[item_lang_key] = display_name
    patch_json(lang_file, add_lang, args.dry_run)

    # --- Layer worldgen ---
    if not args.no_layer:
        # configured_feature stub — must exist so placed_feature reference is valid.
        # Not registered via BiomeModifications so it is never actually placed.
        write_json(
            os.path.join(MOD_DATA, "worldgen/configured_feature", f"{name}.json"),
            {
                "type": "minecraft:ore",
                "config": {
                    "size": 1,
                    "discard_chance_on_air_exposure": 0.0,
                    "targets": [{
                        "target": {
                            "predicate_type": "minecraft:tag_match",
                            "tag": "minecraft:stone_ore_replaceables",
                        },
                        "state": {"Name": ns_name},
                    }],
                },
            },
            args.dry_run,
        )

        # placed_feature documents the Y range (actual generation is in MineralLayersMixin)
        write_json(
            os.path.join(MOD_DATA, "worldgen/placed_feature", f"{name}.json"),
            {
                "feature": ns_name,
                "placement": [
                    {
                        "type": "minecraft:height_range",
                        "height": {
                            "type": "minecraft:uniform",
                            "min_inclusive": {"absolute": args.layer_min_y},
                            "max_inclusive": {"absolute": args.layer_max_y},
                        },
                    }
                ],
            },
            args.dry_run,
        )

        # carver_replaceables — caves carve through this block
        carver_tag = os.path.join(MINECRAFT_DATA, "tags/block/carver_replaceables.json")
        def add_to_carver(data):
            if ns_name not in data["values"]:
                data["values"].append(ns_name)
        patch_json(carver_tag, add_to_carver, args.dry_run)

        # stone_ore_replaceables — vanilla ores spawn inside this block
        ore_tag = os.path.join(MINECRAFT_DATA, "tags/block/stone_ore_replaceables.json")
        def add_to_ore(data):
            if ns_name not in data["values"]:
                data["values"].append(ns_name)
        patch_json(ore_tag, add_to_ore, args.dry_run)

    # --- Texture placeholder reminder ---
    texture_path = os.path.join(ASSETS, "textures/block", f"{name}.png")
    print(f"\n  Remember to add texture: {os.path.relpath(texture_path, BASE)}")

    # --- Java snippets ---
    class_name = "".join(w.capitalize() for w in name.split("_"))
    strength_str = f"{args.strength}f"
    resistance_str = f"{args.resistance}f"

    print(f"""
=== Java code to add manually ===

--- ModBlocks.java ---

// Field declaration (with other fields):
public static final Block {class_name} = new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "{name}"))).strength({strength_str}, {resistance_str}).requiresCorrectToolForDrops());

// Inside register() method:
Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath("gneiss-earth", "{name}"), {class_name});
Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath("gneiss-earth", "{name}"), new BlockItem({class_name}, new Item.Properties().setId(ResourceKey.create(Registries.ITEM,
        Identifier.fromNamespaceAndPath("gneiss-earth", "{name}")))));
""")

    if not args.no_layer:
        print(f"""--- MineralLayersMixin.java (getMineralForY) ---

// Insert this line in Y-ascending order inside getMineralForY():
if (y < {args.layer_max_y}) return ModBlocks.{class_name}.defaultBlockState(); // {args.layer_min_y} to {args.layer_max_y}
""")

    print("=== Done! ===\n")


if __name__ == "__main__":
    main()
