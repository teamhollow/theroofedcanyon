package net.teamhollow.theroofedcanyon.block;

import java.util.Random;

import net.teamhollow.theroofedcanyon.init.TRCItems;
import net.teamhollow.theroofedcanyon.init.TRCParticleTypes;
import net.teamhollow.theroofedcanyon.init.TRCProperties;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class VilepotFlowerBlock extends Block {
    public static String id = "vilepot_flower";

    public static final IntProperty VILE_LEVEL;
    public static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D);

    public VilepotFlowerBlock() {
        super(
            AbstractBlock.Settings
                .of(Material.ORGANIC_PRODUCT, MaterialColor.GRASS)
                .strength(0.8F)
                .lightLevel((state) -> {
                    return 3;
                })
                .nonOpaque()
                .sounds(BlockSoundGroup.SLIME)
        );
        this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(VILE_LEVEL, 0)));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return getVile(state);
    }

    // public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, BlockEntity blockEntity, ItemStack stack) {
    //     super.afterBreak(world, player, pos, state, blockEntity, stack);
    //     if (!world.isClient) {
    //         if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
    //             world.updateComparators(pos, this);
    //             this.angerNearbyCarniblooms(world, pos);
    //         }
    //     }
    // }

    // @SuppressWarnings("rawtypes")
    // private void angerNearbyCarniblooms(World world, BlockPos pos) {
    //     List<CarnibloomEntity> list = world.getNonSpectatingEntities(CarnibloomEntity.class, (new Box(pos)).expand(8.0D, 6.0D, 8.0D));
    //     if (!list.isEmpty()) {
    //         List<PlayerEntity> list2 = world.getNonSpectatingEntities(PlayerEntity.class,
    //                 (new Box(pos)).expand(8.0D, 6.0D, 8.0D));
    //         int i = list2.size();
    //         Iterator var6 = list.iterator();

    //         while (var6.hasNext()) {
    //             CarnibloomEntity carnibloomEntity = (CarnibloomEntity)var6.next();
    //             if (carnibloomEntity.getTarget() == null) {
    //                 carnibloomEntity.setTarget((LivingEntity)list2.get(world.random.nextInt(i)));
    //             }
    //         }
    //     }
    // }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (getVile(state) > 0 && itemStack.getItem() == Items.GLASS_BOTTLE) {
            itemStack.decrement(1);
            world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
            if (itemStack.isEmpty()) {
                player.setStackInHand(hand, new ItemStack(TRCItems.VILE_BOTTLE));
            } else if (!player.inventory.insertStack(new ItemStack(TRCItems.VILE_BOTTLE))) {
                player.dropItem(new ItemStack(TRCItems.VILE_BOTTLE), false);
            }

            this.modifyVile(world, state, pos, -1);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }

        return ActionResult.success(world.isClient);
    }

    public void modifyVile(World world, BlockState state, BlockPos pos, int modifier) {
        world.setBlockState(pos, (BlockState)state.with(VILE_LEVEL, getVile(state) + modifier), 3);
        world.updateComparators(pos, this);
    }
    public static int getVile(BlockState state) {
        return (int) state.get(VILE_LEVEL);
    }

    public static boolean hasVile(BlockState state) {
        return getVile(state) > 0;
    }
    public boolean isOozing(BlockState state) {
        return getVile(state) >= 4;
    }
    public boolean isMature(BlockState state) {
        return getVile(state) == 5;
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (isOozing(state))
            VilepotFlowerBlock.spawnVileParticles(world, pos, state, random, 3);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.modifyVile(world, state, pos, +1);
        VilepotFlowerBlock.spawnVileParticles(world.getWorld(), pos.up(), state, random, 20);
    }
    public boolean hasRandomTicks(BlockState state) {
        return !isMature(state);
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && hasVile(state) && !world.isClient())  {
            LivingEntity livingEntity = (LivingEntity)entity;
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, getVile(state) * 20 , 1));
            livingEntity.damage(DamageSource.CACTUS, 0.25F);
        }

        super.onEntityCollision(state, world, pos, entity);
    }

    @Environment(EnvType.CLIENT)
	public static void spawnVileParticles(World world, BlockPos pos, BlockState state, Random random, int count) {
        for (int i = 0; i < count; ++i) {
            if (state.getFluidState().isEmpty() && world.random.nextFloat() >= 0.3F) {
                VoxelShape voxelShape = state.getCollisionShape(world, pos);
                double d = voxelShape.getMax(Direction.Axis.Y);
                if (d >= 1.0D && !state.isIn(BlockTags.IMPERMEABLE)) {
                    double e = voxelShape.getMin(Direction.Axis.Y);
                    if (e > 0.0D) {
                        VilepotFlowerBlock.addVileParticle(world, pos, voxelShape, (double)pos.getY() + e - 0.05D);
                    } else {
                        BlockPos blockPos = pos.down();
                        BlockState blockState = world.getBlockState(blockPos);
                        VoxelShape voxelShape2 = blockState.getCollisionShape(world, blockPos);
                        double f = voxelShape2.getMax(Direction.Axis.Y);
                        if ((f < 1.0D || !blockState.isFullCube(world, blockPos)) && blockState.getFluidState().isEmpty()) {
                            VilepotFlowerBlock.addVileParticle(world, pos, voxelShape, (double)pos.getY() - 0.05D);
                        }
                    }
                }
            }
        }
    }

    @Environment(EnvType.CLIENT)
    private static void addVileParticle(World world, BlockPos pos, VoxelShape shape, double height) {
        VilepotFlowerBlock.addVileParticle(world, (double)pos.getX() + shape.getMin(Direction.Axis.X),
                (double)pos.getX() + shape.getMax(Direction.Axis.X),
                (double)pos.getZ() + shape.getMin(Direction.Axis.Z),
                (double)pos.getZ() + shape.getMax(Direction.Axis.Z), height);
    }
    @Environment(EnvType.CLIENT)
    private static void addVileParticle(World world, double minX, double maxX, double minZ, double maxZ, double height) {
        world.addParticle(TRCParticleTypes.DRIPPING_VILE, MathHelper.lerp(world.random.nextDouble(), minX, maxX), height, MathHelper.lerp(world.random.nextDouble(), minZ, maxZ), 0.0D, 0.0D, 0.0D);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(VILE_LEVEL);
    }

    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        int vileLevel = getVile(state);
        if (!player.isCreative() && vileLevel > 0) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, vileLevel * 30, 1));
        }

        super.onBreak(world, pos, state, player);
    }

    static {
        VILE_LEVEL = TRCProperties.VILE_LEVEL;
    }
}
