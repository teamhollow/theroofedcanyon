package net.teamhollow.theroofedcanyon.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.teamhollow.theroofedcanyon.TheRoofedCanyon;
import net.teamhollow.theroofedcanyon.util.Utils;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class VilePotionItem extends Item {
    public static String id = "vile_potion";

	public VilePotionItem() {
        super(new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE).group(TheRoofedCanyon.ITEM_GROUP).maxCount(1));
    }

    public ItemStack getStackForRender() {
        return PotionUtil.setPotion(super.getStackForRender(), Potions.WATER);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity) user : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity) playerEntity, stack);
        }

        if (!world.isClient) {
            Random random = world.random;

            List<StatusEffectInstance> chosenEffects = new ArrayList<>();
            for (StatusEffect statusEffect : Utils.harmfulStatusEffects) {
                if (random.nextDouble() <= 0.4D)
                    chosenEffects.add(new StatusEffectInstance(statusEffect, Math.max(200, random.nextInt(400)), Math.max(0, random.nextInt(3))));

                if (chosenEffects.size() >= 5) break;
            };

            Iterator<StatusEffectInstance> chosenEffectsIterator = chosenEffects.iterator();
            while (chosenEffectsIterator.hasNext()) {
                StatusEffectInstance statusEffectInstance = (StatusEffectInstance)chosenEffectsIterator.next();
                if (statusEffectInstance.getEffectType().isInstant()) {
                    statusEffectInstance.getEffectType().applyInstantEffect(playerEntity, playerEntity, user, statusEffectInstance.getAmplifier(), 1.0D);
                } else {
                    user.addStatusEffect(statusEffectInstance);
                }
            }
        }

        if (playerEntity != null) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!playerEntity.abilities.creativeMode) {
                stack.decrement(1);
            }
        }

        if (playerEntity == null || !playerEntity.abilities.creativeMode) {
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (playerEntity != null) {
                playerEntity.inventory.insertStack(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return stack;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(new TranslatableText(this.getTranslationKey() + ".tooltip").formatted(Formatting.GRAY));
    }

    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
