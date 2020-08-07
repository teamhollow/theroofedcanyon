package net.teamhollow.theroofedcanyon.block;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.teamhollow.theroofedcanyon.entity.grumworm.GrubwormEntity;
import net.teamhollow.theroofedcanyon.init.TRCBlocks;
import net.teamhollow.theroofedcanyon.init.TRCEntities;
import net.teamhollow.theroofedcanyon.init.TRCProperties;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import net.minecraft.world.explosion.Explosion;

public class TurfwoodLeavesBlock extends GrassBlock {
    public static final BooleanProperty INFESTED = TRCProperties.INFESTED;

    public TurfwoodLeavesBlock() {
        super(AbstractBlock.Settings.of(Material.SOLID_ORGANIC).ticksRandomly().strength(0.6F).sounds(BlockSoundGroup.WOOD));
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(INFESTED, false).with(SNOWY, false));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (isInfested(state)) {
            List<BlockPos> availablePositions = Arrays.asList(new BlockPos[]{ pos.north(), pos.east(), pos.south(), pos.west() });
            Collections.shuffle(availablePositions);
            for (BlockPos blockPos : availablePositions) {
                BlockState blockState = world.getBlockState(blockPos);
                if (world.random.nextDouble() <= 0.25D && blockState.getBlock() instanceof TurfwoodLeavesBlock && !isInfested(world.getBlockState(blockPos))) {
                    world.setBlockState(pos, state.with(TRCProperties.INFESTED, false));
                    world.setBlockState(blockPos, blockState.with(TRCProperties.INFESTED, true));

                    pos = blockPos;
                };
            }
        };

        if (!canSurvive(state, world, pos) || world.getBlockState(pos.up()).getBlock() == TRCBlocks.TURFWOOD.LEAVES) {
            world.setBlockState(pos, TRCBlocks.TURFWOOD.LOG.getDefaultState());
        } else {
            if (world.getLightLevel(pos.up()) >= 9) {
                BlockState blockState = this.getDefaultState();

                for (int i = 0; i < 4; ++i) {
                    BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if (world.getBlockState(blockPos).isOf(TRCBlocks.TURFWOOD.LOG) && canSpread(blockState, world, blockPos)) {
                        world.setBlockState(blockPos, (BlockState) blockState.with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)));
                    }
                }
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int woodPosDown = getLowestConnectedWoodBlock(world, pos);
        if (woodPosDown > 0 && random.nextDouble() <= 0.225D)
            VilepotFlowerBlock.spawnVileParticles(world, pos.down(woodPosDown), state, random, 1);
    }
    private int getLowestConnectedWoodBlock(World world, BlockPos pos) {
        int woodPosDown = 0;

        for (int i = 0; i < 5; i++)
            if (world.getBlockState(pos.down(i + 1)).getBlock() == TRCBlocks.TURFWOOD.WOOD)
                woodPosDown++;
            else
                break;

        return woodPosDown;
    }

    private static boolean canSurvive(BlockState state, WorldView worldView, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = worldView.getBlockState(blockPos);
        if (blockState.getFluidState().getLevel() == 8) {
            return false;
        } else {
            int i = ChunkLightProvider.getRealisticOpacity(worldView, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(worldView, blockPos));
            return i < worldView.getMaxLightLevel();
        }
    }
    private static boolean canSpread(BlockState state, WorldView worldView, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return canSurvive(state, worldView, pos) && !worldView.getFluidState(blockPos).isIn(FluidTags.WATER);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(INFESTED);
        super.appendProperties(builder);
    }

	public static boolean isInfested(BlockState state) {
		return state.get(INFESTED);
    }

    private void spawnSilverfish(World world, BlockPos pos) {
        GrubwormEntity grubwormEntity = (GrubwormEntity) TRCEntities.GRUBWORM.create(world);
        grubwormEntity.refreshPositionAndAngles((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
        world.spawnEntity(grubwormEntity);
        grubwormEntity.playSpawnEffects();
    }

    public void onStacksDropped(BlockState state, World world, BlockPos pos, ItemStack stack) {
        super.onStacksDropped(state, world, pos, stack);
        if (!world.isClient && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            this.spawnSilverfish(world, pos);
        }
    }

    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        if (!world.isClient) {
            this.spawnSilverfish(world, pos);
        }
    }
}
