package net.teamhollow.theroofedcanyon.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.teamhollow.theroofedcanyon.TheRoofedCanyon;

public class GrubStewItem extends Item {
    public GrubStewItem() {
        super(new Item.Settings().food(TRCFoodComponents.GRUB_STEW).maxCount(16).group(TheRoofedCanyon.ITEM_GROUP));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        if (user instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) user;
            Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
            serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }

        if (stack.isEmpty()) {
            return new ItemStack(Items.BOWL);
        } else {
            if (user instanceof PlayerEntity && !((PlayerEntity) user).abilities.creativeMode) {
                ItemStack itemStack = new ItemStack(Items.BOWL);
                PlayerEntity playerEntity = (PlayerEntity) user;
                if (!playerEntity.inventory.insertStack(itemStack)) {
                    playerEntity.dropItem(itemStack, false);
                }
            }

            return stack;
        }
    }
}
