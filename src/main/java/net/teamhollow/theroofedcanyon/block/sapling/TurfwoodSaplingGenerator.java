package net.teamhollow.theroofedcanyon.block.sapling;

import java.util.Random;
import net.teamhollow.theroofedcanyon.init.TRCDecorators;
import net.minecraft.block.sapling.LargeTreeSaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class TurfwoodSaplingGenerator extends LargeTreeSaplingGenerator {
    public ConfiguredFeature<TreeFeatureConfig, ?> createTreeFeature(Random random, boolean bl) {
        return null;
    }

    public ConfiguredFeature<TreeFeatureConfig, ?> createLargeTreeFeature(Random random) {
        return Feature.TREE.configure(TRCDecorators.MEGA_TURFWOOD_TREE_CONFIG);
    }
}
