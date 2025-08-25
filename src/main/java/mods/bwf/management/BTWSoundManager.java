package mods.bwf.management;

import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.util.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public abstract class BTWSoundManager {
    private static final Set<AddonSoundRegistryEntry> soundRegistry = new HashSet<>();

    //------ Block Sounds (Generic) ------//

    public static final AddonSoundRegistryEntry BLOOD_WOOD_BREAK = new AddonSoundRegistryEntry("btw:block.blood_wood.break", 6);
    public static final AddonSoundRegistryEntry BLOOD_WOOD_STEP = new AddonSoundRegistryEntry("btw:block.blood_wood.step", 6);

    public static final AddonSoundRegistryEntry BONE_BREAK = new AddonSoundRegistryEntry("btw:block.bone.break", 5);
    public static final AddonSoundRegistryEntry BONE_STEP = new AddonSoundRegistryEntry("btw:block.bone.step", 5);

    public static final AddonSoundRegistryEntry NETHER_PLANTS_BREAK = new AddonSoundRegistryEntry("btw:block.nether_plants.break", 6);
    public static final AddonSoundRegistryEntry NETHER_PLANTS_STEP = new AddonSoundRegistryEntry("btw:block.nether_plants.step", 5);

    public static final AddonSoundRegistryEntry CLAY_BRICK_BREAK = new AddonSoundRegistryEntry("btw:block.clay_brick.break", 6);
    public static final AddonSoundRegistryEntry CLAY_BRICK_STEP = new AddonSoundRegistryEntry("btw:block.clay_brick.step", 6);

    public static final AddonSoundRegistryEntry CLAY_BREAK = new AddonSoundRegistryEntry("btw:block.clay.break", 4);
    public static final AddonSoundRegistryEntry CLAY_STEP = new AddonSoundRegistryEntry("btw:block.clay.step", 6);

    public static final AddonSoundRegistryEntry CROP_PLACE = new AddonSoundRegistryEntry("btw:block.crop.place", 6);

    public static final AddonSoundRegistryEntry DIRT_BREAK = new AddonSoundRegistryEntry("btw:block.dirt.break", 4);
    public static final AddonSoundRegistryEntry DIRT_STEP = new AddonSoundRegistryEntry("btw:block.dirt.step", 6);

    public static final AddonSoundRegistryEntry GEM_BREAK = new AddonSoundRegistryEntry("btw:block.gem.break", 4);
    public static final AddonSoundRegistryEntry GEM_PLACE = new AddonSoundRegistryEntry("btw:block.gem.place", 4);
    public static final AddonSoundRegistryEntry GEM_STEP = new AddonSoundRegistryEntry("btw:block.gem.step", 14);

    public static final AddonSoundRegistryEntry LEAVES_BREAK = new AddonSoundRegistryEntry("btw:block.leaves.break", 7);
    public static final AddonSoundRegistryEntry LEAVES_STEP = new AddonSoundRegistryEntry("btw:block.leaves.step", 5);

    public static final AddonSoundRegistryEntry METAL_BREAK = new AddonSoundRegistryEntry("btw:block.metal.break", 4);
    public static final AddonSoundRegistryEntry METAL_STEP = new AddonSoundRegistryEntry("btw:block.metal.step", 6);

    public static final AddonSoundRegistryEntry NETHER_GROTH_BREAK = new AddonSoundRegistryEntry("btw:block.nether_groth.break", 6);
    public static final AddonSoundRegistryEntry NETHER_GROTH_STEP = new AddonSoundRegistryEntry("btw:block.nether_groth.step", 6);

    public static final AddonSoundRegistryEntry NETHERRACK_BREAK = new AddonSoundRegistryEntry("btw:block.netherrack.break", 6);
    public static final AddonSoundRegistryEntry NETHERRACK_STEP = new AddonSoundRegistryEntry("btw:block.netherrack.step", 6);

    public static final AddonSoundRegistryEntry ORE_BREAK = new AddonSoundRegistryEntry("btw:block.ore.break", 4);
    public static final AddonSoundRegistryEntry ORE_STEP = new AddonSoundRegistryEntry("btw:block.ore.step", 5);

    public static final AddonSoundRegistryEntry PLANTS_BREAK = new AddonSoundRegistryEntry("btw:block.plants.break", 4);
    public static final AddonSoundRegistryEntry PLANTS_STEP = new AddonSoundRegistryEntry("btw:block.plants.step", 6);

    public static final AddonSoundRegistryEntry SAPLING_BREAK = new AddonSoundRegistryEntry("btw:block.sapling.break", 6);
    public static final AddonSoundRegistryEntry SAPLING_STEP = new AddonSoundRegistryEntry("btw:block.sapling.step", 6);

    public static final AddonSoundRegistryEntry SMALL_OBJECT_BREAK = new AddonSoundRegistryEntry("btw:block.small_object.break", 5);
    public static final AddonSoundRegistryEntry SMALL_OBJECT_STEP = new AddonSoundRegistryEntry("btw:block.small_object.step", 5);

    public static final AddonSoundRegistryEntry SOULFORGED_STEEL_BREAK = new AddonSoundRegistryEntry("btw:block.soulforged_steel.break", 4);
    public static final AddonSoundRegistryEntry SOULFORGED_STEEL_STEP = new AddonSoundRegistryEntry("btw:block.soulforged_steel.step", 6);

    public static final AddonSoundRegistryEntry SOUL_SAND_BREAK = new AddonSoundRegistryEntry("btw:block.soul_sand.break", 9);
    public static final AddonSoundRegistryEntry SOUL_SAND_STEP = new AddonSoundRegistryEntry("btw:block.soul_sand.step", 5);

    public static final AddonSoundRegistryEntry STONE_BREAK = new AddonSoundRegistryEntry("btw:block.stone.break", 5);
    public static final AddonSoundRegistryEntry STONE_PLACE = new AddonSoundRegistryEntry("btw:block.stone.place", 6);
    public static final AddonSoundRegistryEntry STONE_STEP = new AddonSoundRegistryEntry("btw:block.stone.step", 6);

    public static final AddonSoundRegistryEntry STONE_STRATA_2_BREAK = new AddonSoundRegistryEntry("btw:block.stone_strata_2.break", 4);
    public static final AddonSoundRegistryEntry STONE_STRATA_2_STEP = new AddonSoundRegistryEntry("btw:block.stone_strata_2.step", 6);

    public static final AddonSoundRegistryEntry STONE_STRATA_3_BREAK = new AddonSoundRegistryEntry("btw:block.stone_strata_3.break", 5);
    public static final AddonSoundRegistryEntry STONE_STRATA_3_STEP = new AddonSoundRegistryEntry("btw:block.stone_strata_3.step", 6);

    public static final AddonSoundRegistryEntry STONE_BRICK_BREAK = new AddonSoundRegistryEntry("btw:block.stone_brick.break", 6);
    public static final AddonSoundRegistryEntry STONE_BRICK_STEP = new AddonSoundRegistryEntry("btw:block.stone_brick.step", 5);

    public static final AddonSoundRegistryEntry VINE_BREAK = new AddonSoundRegistryEntry("btw:block.vine.break", 4);
    public static final AddonSoundRegistryEntry VINE_STEP = new AddonSoundRegistryEntry("btw:block.vine.step", 5);

    public static final AddonSoundRegistryEntry WATER_PLANTS_BREAK = new AddonSoundRegistryEntry("btw:block.water_plants.break", 6);
    public static final AddonSoundRegistryEntry WATER_PLANTS_STEP = new AddonSoundRegistryEntry("btw:block.water_plants.step", 6);

    //------ Block Sounds (Unique) ------//

    public static final AddonSoundRegistryEntry CHEST_CLOSE = new AddonSoundRegistryEntry("btw:block.chest.close", 3);
    public static final AddonSoundRegistryEntry CHEST_OPEN = new AddonSoundRegistryEntry("btw:block.chest.open");

    public static final AddonSoundRegistryEntry DOOR_CLOSE = new AddonSoundRegistryEntry("btw:block.door.close", 6);
    public static final AddonSoundRegistryEntry DOOR_OPEN = new AddonSoundRegistryEntry("btw:block.door.open", 4);

    public static final AddonSoundRegistryEntry IRON_DOOR_CLOSE = new AddonSoundRegistryEntry("btw:block.iron_door.close", 4);
    public static final AddonSoundRegistryEntry IRON_DOOR_OPEN = new AddonSoundRegistryEntry("btw:block.iron_door.open", 4);

    public static final AddonSoundRegistryEntry TRAPDOOR_CLOSE = new AddonSoundRegistryEntry("btw:block.trapdoor.close", 3);
    public static final AddonSoundRegistryEntry TRAPDOOR_OPEN = new AddonSoundRegistryEntry("btw:block.trapdoor.open", 5);

    //------ Item Sounds ------//

    public static final AddonSoundRegistryEntry CHISEL_STONE = new AddonSoundRegistryEntry("btw:item.chisel.stone", 2);
    public static final AddonSoundRegistryEntry CHISEL_WOOD = new AddonSoundRegistryEntry("btw:item.chisel.wood", 4);

    public static final AddonSoundRegistryEntry HOE_TILL = new AddonSoundRegistryEntry("btw:item.hoe.till", 4);

    //------ Entity Sounds ------//

    public static final AddonSoundRegistryEntry SQUID_DEATH = new AddonSoundRegistryEntry("btw:entity.squid.death", 3);
    public static final AddonSoundRegistryEntry SQUID_HURT = new AddonSoundRegistryEntry("btw:entity.squid.hurt", 4);
    public static final AddonSoundRegistryEntry SQUID_IDLE = new AddonSoundRegistryEntry("btw:entity.squid.idle", 5);

    public static final AddonSoundRegistryEntry VILLAGER_PRIEST_INFUSE = new AddonSoundRegistryEntry("btw:entity.villager.priest_infuse", 2);

    public static final AddonSoundRegistryEntry WITCH_DEATH = new AddonSoundRegistryEntry("btw:entity.witch.death", 3);
    public static final AddonSoundRegistryEntry WITCH_DRINK = new AddonSoundRegistryEntry("btw:entity.witch.drink", 4);
    public static final AddonSoundRegistryEntry WITCH_HURT = new AddonSoundRegistryEntry("btw:entity.witch.hurt", 3);
    public static final AddonSoundRegistryEntry WITCH_IDLE = new AddonSoundRegistryEntry("btw:entity.witch.idle", 5);
    public static final AddonSoundRegistryEntry WITCH_THROW = new AddonSoundRegistryEntry("btw:entity.witch.throw", 3);

    //------ Other Sounds ------//

    public static final AddonSoundRegistryEntry GLOOM_BITES = new AddonSoundRegistryEntry("btw:misc.gloom.bite", 2);

    public static final AddonSoundRegistryEntry SOUL_SCREAM = new AddonSoundRegistryEntry("btw:misc.soul.scream", 5);
    public static final AddonSoundRegistryEntry SOUL_SPREAD = new AddonSoundRegistryEntry("btw:misc.soul.spread", 13);
    public static final AddonSoundRegistryEntry SOUL_POSSESSION_COMPLETE = new AddonSoundRegistryEntry("btw:misc.soul.possession_complete");

    public static void registerSounds(SoundRegistry soundManager) {
        for (AddonSoundRegistryEntry entry : soundRegistry) {
            int variantCount = entry.variantCount();

            if (variantCount > 1) {
                for (int i = 0; i < variantCount; i++) {
                    String path = entry.sound().replace('.', '/');
                    ResourceLocation resourceLocation = new ResourceLocation(path + (i + 1) + ".ogg");
                    SoundEventAccessorComposite composite = new SoundEventAccessorComposite(resourceLocation, 1.0, 1.0, SoundCategory.MASTER);
                    soundManager.registerSound(composite);
                }
            }
            else {
                String path = entry.sound().replace('.', '/');
                ResourceLocation resourceLocation = new ResourceLocation(path + ".ogg");
                SoundEventAccessorComposite composite = new SoundEventAccessorComposite(resourceLocation, 1.0,1.0, SoundCategory.MASTER);
                soundManager.registerSound(composite);
            }
        }
    }

    static void addSoundToRegistry(AddonSoundRegistryEntry entry) {
        soundRegistry.add(entry);
    }
}
