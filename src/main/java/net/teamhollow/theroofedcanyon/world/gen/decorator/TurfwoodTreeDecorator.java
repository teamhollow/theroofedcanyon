package net.teamhollow.theroofedcanyon.world.gen.decorator;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.NopeDecoratorConfig;

public class TurfwoodTreeDecorator extends Decorator<NopeDecoratorConfig> {
    public TurfwoodTreeDecorator(Codec<NopeDecoratorConfig> codec) {
        super(codec);
    }

    public Stream<BlockPos> getPositions(WorldAccess worldAccess, ChunkGenerator chunkGenerator, Random random, NopeDecoratorConfig nopeDecoratorConfig, BlockPos blockPos) {
        return IntStream.range(0, 16).mapToObj((i) -> {
            int j = i / 4;
            int k = i % 4;
            int x = j * 4 + 1 + random.nextInt(3) + blockPos.getX();
            int z = k * 4 + 1 + random.nextInt(3) + blockPos.getZ();
            int y = worldAccess.getTopY(Heightmap.Type.MOTION_BLOCKING, x, z);
            return new BlockPos(x, y, z);
        });
    }
}
