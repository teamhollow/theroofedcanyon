package net.teamhollow.theroofedcanyon.init;

import net.teamhollow.theroofedcanyon.TheRoofedCanyon;
import net.teamhollow.theroofedcanyon.block.helpers.WoodBlocks;
import net.teamhollow.theroofedcanyon.entity.chomproot.*;
import net.teamhollow.theroofedcanyon.entity.grubworm.*;
import net.teamhollow.theroofedcanyon.entity.vanilla.TRCBoatEntity;
import net.teamhollow.theroofedcanyon.util.TRCBoat;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TRCEntities {
    public static final EntityType<GrubwormEntity> GRUBWORM = register(
        GrubwormEntity.id,
        GrubwormEntity.builder,
        GrubwormEntity.spawnEggColors
    );
    public static final EntityType<ChomprootEntity> CHOMPROOT = register(
        ChomprootEntity.id,
        ChomprootEntity.builder,
        ChomprootEntity.spawnEggColors
    );

    public TRCEntities() {
        registerDefaultAttributes(GRUBWORM, GrubwormEntity.createGrubwormAttributes());
        registerDefaultAttributes(CHOMPROOT, ChomprootEntity.createChomprootAttributes());
    }

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> entityType, int[] spawnEggColors) {
        EntityType<T> builtEntityType = entityType.build(id);

        if (spawnEggColors[0] != 0)
            TRCItems.register(id + "_spawn_egg", new SpawnEggItem(builtEntityType, spawnEggColors[0], spawnEggColors[1], new Item.Settings().maxCount(64).group(TheRoofedCanyon.ITEM_GROUP)));

        return Registry.register(Registry.ENTITY_TYPE, new Identifier(TheRoofedCanyon.MOD_ID, id), builtEntityType);
    }
    @SuppressWarnings("unused")
    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> entityType) {
        return register(id, entityType, new int[]{ 0, 0 });
    }

    public static EntityType<TRCBoatEntity> register(WoodBlocks wood, BoatEntity.Type vanilla) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(TheRoofedCanyon.MOD_ID, wood.id + "_boat"),
                FabricEntityTypeBuilder.<TRCBoatEntity>create(SpawnGroup.MISC, (entity, world) -> new TRCBoatEntity(entity, world, new TRCBoat(wood.BOAT_ITEM, wood.PLANKS.asItem(), new Identifier(TheRoofedCanyon.MOD_ID, "textures/entity/boat/" + wood.id + ".png"), vanilla))).dimensions(EntityDimensions.fixed(1.375F, 0.5625F)).build());
    }

    public static void registerDefaultAttributes(EntityType<? extends LivingEntity> type, DefaultAttributeContainer.Builder builder) {
        FabricDefaultAttributeRegistry.register(type, builder);
    }
    public static Identifier texture(String path) {
        return TheRoofedCanyon.texture("entity/" + path);
    }
}
