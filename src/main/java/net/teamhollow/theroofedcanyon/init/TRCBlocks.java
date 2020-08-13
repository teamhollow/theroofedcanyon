package net.teamhollow.theroofedcanyon.init;

import net.teamhollow.theroofedcanyon.TheRoofedCanyon;
import net.teamhollow.theroofedcanyon.block.*;
import net.teamhollow.theroofedcanyon.block.config.*;
import net.teamhollow.theroofedcanyon.block.helpers.WoodBlocks;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.*;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.ServerWorldAccess;

public class TRCBlocks {
    public static final WoodBlocks TURFWOOD = new WoodBlocks(new TurfwoodBlocksConfig());

    public static final Block VILEPOT_FLOWER = register(VilepotFlowerBlock.id, new VilepotFlowerBlock());

    public TRCBlocks() {
        DispenserBlock.registerBehavior(Items.GLASS_BOTTLE.asItem(), new FallibleItemDispenserBehavior() {
            private final ItemDispenserBehavior glassBottleDispenserBehaviour = new ItemDispenserBehavior();

            private ItemStack method_22141(BlockPointer blockPointer, ItemStack emptyBottleStack,
                    ItemStack filledBottleStack) {
                emptyBottleStack.decrement(1);
                if (emptyBottleStack.isEmpty()) {
                    return filledBottleStack.copy();
                } else {
                    if (((DispenserBlockEntity) blockPointer.getBlockEntity())
                            .addToFirstFreeSlot(filledBottleStack.copy()) < 0) {
                        this.glassBottleDispenserBehaviour.dispense(blockPointer, filledBottleStack.copy());
                    }

                    return emptyBottleStack;
                }
            }

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                this.setSuccess(false);
                ServerWorldAccess worldAccess = pointer.getWorld();
                BlockPos blockPos = pointer.getBlockPos()
                        .offset((Direction) pointer.getBlockState().get(DispenserBlock.FACING));
                BlockState blockState = worldAccess.getBlockState(blockPos);
                if (blockState.getBlock() == TRCBlocks.VILEPOT_FLOWER && VilepotFlowerBlock.hasVile(blockState)) {
                    ((VilepotFlowerBlock) blockState.getBlock()).modifyVile((ServerWorld)worldAccess, blockState,
                            blockPos, -1);
                    this.setSuccess(true);
                    return this.method_22141(pointer, stack, new ItemStack(TRCItems.VILE_BOTTLE));
                } else {
                    return super.dispenseSilently(pointer, stack);
                }
            }
        });
    }

    public static Block register(String id, Block block, boolean registerItem) {
        Identifier identifier = new Identifier(TheRoofedCanyon.MOD_ID, id);

        Block registeredBlock = Registry.register(Registry.BLOCK, identifier, block);
        if (registerItem) {
            int maxCount = 64;
            if (block instanceof SignBlock) maxCount = 16;

            Registry.register(Registry.ITEM, identifier, new BlockItem(registeredBlock, new Item.Settings().maxCount(maxCount).group(TheRoofedCanyon.ITEM_GROUP)));
        }

        return registeredBlock;
    }
    public static Block register(String id, Block block) {
        return register(id, block, true);
    }

    public static PillarBlock createLogBlock(MaterialColor topMaterialColor, MaterialColor sideMaterialColor) {
        return new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, (blockState) -> blockState.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMaterialColor : sideMaterialColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
    }
    public static LeavesBlock createLeavesBlock() {
        return new LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning(TRCBlocks::canSpawnOnLeaves).suffocates(TRCBlocks::never).blockVision(TRCBlocks::never));
   }

    public static boolean canSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }
    public static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }
    public static boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return false;
    }
}
