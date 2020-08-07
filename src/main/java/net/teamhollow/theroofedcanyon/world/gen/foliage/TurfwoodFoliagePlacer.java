package net.teamhollow.theroofedcanyon.world.gen.foliage;

import java.util.Random;
import java.util.Set;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class TurfwoodFoliagePlacer extends FoliagePlacer {
    public static final Codec<TurfwoodFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> {
        return method_28846(instance).and(Codec.INT.fieldOf("height").forGetter((turfwoodFoliagePlacer) -> {
            return turfwoodFoliagePlacer.height;
        })).apply(instance, TurfwoodFoliagePlacer::new);
    });
    protected final int height;

    public TurfwoodFoliagePlacer(int radius, int randomRadius, int offset, int randomOffset, int height) {
        super(radius, randomRadius, offset, randomOffset);
        this.height = height;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return FoliagePlacerType.JUNGLE_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, Set<BlockPos> leaves, int iterations, BlockBox blockBox) {
        int j = treeNode.isGiantTrunk()
            ? foliageHeight
            : 1 + random.nextInt(2)
        ;

        for (int i = iterations; i >= iterations - j; --i) {
            this.generate(world, random, config, treeNode.getCenter(), radius + treeNode.getFoliageRadius() + 1 - i, leaves, i, treeNode.isGiantTrunk(), blockBox);
        }

    }

    @Override
    public int getHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return this.height;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, boolean bl) {
        if (baseHeight + dy >= 7) {
            return true;
        } else {
            return baseHeight * baseHeight + dy * dy > dz * dz;
        }
    }
}
