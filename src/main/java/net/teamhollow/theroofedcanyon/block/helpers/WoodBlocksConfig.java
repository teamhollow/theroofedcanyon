package net.teamhollow.theroofedcanyon.block.helpers;

import net.teamhollow.theroofedcanyon.entity.vanilla.TRCBoatEntity;
import net.teamhollow.theroofedcanyon.item.helpers.vanilla.TRCBoatItem;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;

public class WoodBlocksConfig {
	public String id;
	public Material planksMaterial;
	public MaterialColor planksMaterialColor;
	public MaterialColor logTopMaterialColor;
	public MaterialColor logSideMaterialColor;
	public MaterialColor strippedLogTopMaterialColor;
	public MaterialColor strippedLogSideMaterialColor;
	public Material woodMaterial;
	public MaterialColor woodMaterialColor;
	public Material strippedWoodMaterial;
    public MaterialColor strippedWoodMaterialColor;

	public SaplingGenerator saplingGenerator;
    public int leavesColor = 0;

    public Block LOG;
    public Block STRIPPED_LOG;
    public Block WOOD;
    public Block STRIPPED_WOOD;
    public Block PLANKS;
    public Block LEAVES;
    public Block SLAB;
    public Block PRESSURE_PLATE;
    public Block FENCE;
    public Block FENCE_GATE;
    public Block SAPLING;
    public Block TRAPDOOR;
    public Block BUTTON;
    public Block STAIRS;
    public Block DOOR;
    public Block SIGN;
    public Block WALL_SIGN;

	public boolean isFlammable = true;
	public boolean canBeUsedAsFuel = true;
	public int baseBurnChance = 5;
    public int baseSpreadChance = 20;

	public BoatEntity.Type vanillaBoatType;
	public EntityType<TRCBoatEntity> BOAT;
	public TRCBoatItem BOAT_ITEM;

    public WoodBlocksConfig() {}
}
