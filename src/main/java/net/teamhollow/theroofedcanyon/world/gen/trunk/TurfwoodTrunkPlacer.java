package net.teamhollow.theroofedcanyon.world.gen.trunk;

import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class TurfwoodTrunkPlacer extends GiantTrunkPlacer {
    public static final Codec<TurfwoodTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return method_28904(instance).apply(instance, TurfwoodTrunkPlacer::new);
    });

    public TurfwoodTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight) {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    protected TrunkPlacerType<?> getType() {
        return TrunkPlacerType.MEGA_JUNGLE_TRUNK_PLACER;
    }

    public List<FoliagePlacer.TreeNode> generate(ModifiableTestableWorld world, Random random, int trunkHeight, BlockPos pos, Set<BlockPos> set, BlockBox blockBox, TreeFeatureConfig treeFeatureConfig) {
        List<FoliagePlacer.TreeNode> list = Lists.newArrayList();
        list.addAll(super.generate(world, random, trunkHeight, pos, set, blockBox, treeFeatureConfig));

        for (int y = trunkHeight - 2 - random.nextInt(4); y > trunkHeight / 2; y -= 2 + random.nextInt(4)) {
            float rand = random.nextFloat() * 6.2831855F;
            int x = 0;
            int z = 0;

            for (int i = 0; i < 5; ++i) {
                x = (int) (1.5F + MathHelper.cos(rand) * (float) i);
                z = (int) (1.5F + MathHelper.sin(rand) * (float) i);
                BlockPos blockPos = pos.add(x, y - 3 + i / 2, z);
                method_27402(world, random, blockPos, set, blockBox, treeFeatureConfig);
            }

            list.add(new FoliagePlacer.TreeNode(pos.add(x, y, z), -2, false));
        }

        return list;
    }
}
