package net.teamhollow.theroofedcanyon.init;

import java.util.function.Supplier;

import net.teamhollow.theroofedcanyon.TheRoofedCanyon;
import net.teamhollow.theroofedcanyon.block.helpers.WoodBlocks;
import net.teamhollow.theroofedcanyon.entity.vanilla.TRCBoatEntity;
import net.teamhollow.theroofedcanyon.item.*;
import net.teamhollow.theroofedcanyon.item.helpers.vanilla.TRCBoatItem;
import net.minecraft.block.SignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TRCItems {
    public static final Item VILE_BOTTLE = register(VileBottleItem.id, new VileBottleItem());
    public static final Item VILE_POTION = register(VilePotionItem.id, new VilePotionItem());
    
    public static final Item GRUBWORM_IN_A_BOWL = register(GrubwormInABowlItem.id, new GrubwormInABowlItem());
    public static final Item GRUB_STEW = register("grub_stew", new GrubStewItem());

    public static final Item CHOMPROOT_SLICES = register("chomproot_slices", new Item(new Item.Settings().food(TRCFoodComponents.CHOMPROOT_SLICES).group(TheRoofedCanyon.ITEM_GROUP)));

    // utils
    public static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(TheRoofedCanyon.MOD_ID, id), item);
    }
    public static SignItem register(String id, SignBlock standingSign, WallSignBlock wallSign) {
        return (SignItem)register(id + "_sign", new SignItem(new Item.Settings().maxCount(16).group(TheRoofedCanyon.ITEM_GROUP), standingSign, wallSign));
    };
    public static TRCBoatItem register(WoodBlocks wood) {
		return (TRCBoatItem)register(wood.id + "_boat", new TRCBoatItem(new Supplier<EntityType<TRCBoatEntity>>(){
            @Override
            public EntityType<TRCBoatEntity> get() {
                return wood.BOAT;
            }
        }, new Item.Settings().maxCount(1).group(TheRoofedCanyon.ITEM_GROUP)));
    }
}
