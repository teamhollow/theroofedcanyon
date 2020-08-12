package net.teamhollow.theroofedcanyon.item;

import java.util.Objects;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.teamhollow.theroofedcanyon.TheRoofedCanyon;
import net.teamhollow.theroofedcanyon.entity.grubworm.GrubwormEntity;
import net.teamhollow.theroofedcanyon.init.TRCEntities;

public class GrubwormInABowlItem extends Item {
    public static final String id = "grubworm_in_a_bowl";

    public GrubwormInABowlItem() {
        super(new Item.Settings().recipeRemainder(Items.BOWL).maxCount(16).group(TheRoofedCanyon.ITEM_GROUP));
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient) {
            context.getPlayer().playSound(SoundEvents.BLOCK_BEEHIVE_EXIT, 1.0F, 1.0F);
            return ActionResult.SUCCESS;
        } else {
            BlockPos blockPos = context.getBlockPos();
            Direction usedSide = context.getSide();
            BlockState blockState = world.getBlockState(blockPos);

            BlockPos blockPosToSpawn;
            if (blockState.getCollisionShape(world, blockPos).isEmpty()) blockPosToSpawn = blockPos;
            else blockPosToSpawn = blockPos.offset(usedSide);

            ItemStack itemStack = context.getStack();
            PlayerEntity player = context.getPlayer();
            EntityType<GrubwormEntity> entityType = this.getEntityType();
            if (entityType.spawnFromItemStack(world, itemStack, player, blockPosToSpawn, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockPos, blockPosToSpawn) && usedSide == Direction.UP) != null && player instanceof ServerPlayerEntity) {
                Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)player, itemStack);

                if (!player.abilities.creativeMode) {
                    ItemStack newItemStack = ItemUsage.method_30012(itemStack, player, Items.BOWL.getStackForRender());
                    player.setStackInHand(context.getHand(), newItemStack);
                }
            }

            return ActionResult.CONSUME;
        }
    }

    public EntityType<GrubwormEntity> getEntityType() {
        return TRCEntities.GRUBWORM;
    }
}
