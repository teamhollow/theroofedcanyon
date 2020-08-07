package net.teamhollow.theroofedcanyon.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import net.teamhollow.theroofedcanyon.TheRoofedCanyon;
import net.teamhollow.theroofedcanyon.block.helpers.WoodBlocks;
import net.teamhollow.theroofedcanyon.world.gen.decorator.*;
import net.teamhollow.theroofedcanyon.world.gen.foliage.*;
import net.teamhollow.theroofedcanyon.world.gen.trunk.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.decorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.decorator.LeaveVineTreeDecorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;
import net.minecraft.world.gen.decorator.TrunkVineTreeDecorator;
import net.minecraft.world.gen.feature.RandomPatchFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;

public class TRCDecorators {
    public static WoodBlocks TURFWOOD = TRCBlocks.TURFWOOD;

    // configs
    public static final TreeFeatureConfig MEGA_TURFWOOD_TREE_CONFIG = new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(TURFWOOD.LOG.getDefaultState()), new SimpleBlockStateProvider(TURFWOOD.LEAVES.getDefaultState()), new TurfwoodFoliagePlacer(7, 3, 0, 0, 2), new TurfwoodTrunkPlacer(10, 2, 13), new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(TURFWOOD.LEAVES.getDefaultState())), TrunkVineTreeDecorator.field_24965, LeaveVineTreeDecorator.field_24961)).build();
    public static final RandomPatchFeatureConfig VILEPOT_PATCH_CONFIG = new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(TRCBlocks.VILEPOT_FLOWER.getDefaultState()), SimpleBlockPlacer.field_24871).tries(64).whitelist(ImmutableSet.of(Blocks.GRASS_BLOCK)).canReplace().cannotProject().build();

    public static final Decorator<NopeDecoratorConfig> TURFWOOD_TREE = register("turfwood_tree", new TurfwoodTreeDecorator(NopeDecoratorConfig.field_24891));

    public TRCDecorators() {}

    private static <T extends DecoratorConfig, G extends Decorator<T>> G register(String id, G decorator) {
        return Registry.register(Registry.DECORATOR, new Identifier(TheRoofedCanyon.MOD_ID, id), decorator);
    }
}
