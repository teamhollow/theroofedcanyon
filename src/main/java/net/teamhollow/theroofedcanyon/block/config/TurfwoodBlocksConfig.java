package net.teamhollow.theroofedcanyon.block.config;

import net.teamhollow.theroofedcanyon.block.TurfwoodLeavesBlock;
import net.teamhollow.theroofedcanyon.block.helpers.WoodBlocksConfig;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.sapling.OakSaplingGenerator;
import net.minecraft.entity.vehicle.BoatEntity;

public class TurfwoodBlocksConfig extends WoodBlocksConfig {
    public TurfwoodBlocksConfig() {
        id = "turfwood";

        planksMaterial = Material.WOOD;
        planksMaterialColor = MaterialColor.WOOD;
        logTopMaterialColor = MaterialColor.WOOD;
        logSideMaterialColor = MaterialColor.WOOD;
        strippedLogTopMaterialColor = MaterialColor.WOOD;
        strippedLogSideMaterialColor = MaterialColor.WOOD;
        woodMaterial = Material.WOOD;
        woodMaterialColor = MaterialColor.WOOD;
        strippedWoodMaterial = Material.WOOD;
        strippedWoodMaterialColor = MaterialColor.WOOD;

        saplingGenerator = new OakSaplingGenerator();
        LEAVES = new TurfwoodLeavesBlock();
        vanillaBoatType = BoatEntity.Type.JUNGLE;
    }
}
