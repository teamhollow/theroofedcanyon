package net.teamhollow.theroofedcanyon.block.helpers;

import com.mojang.datafixers.types.Type;

import static net.teamhollow.theroofedcanyon.init.TRCBlocks.*;
import static net.teamhollow.theroofedcanyon.init.TRCEntities.*;
import static net.teamhollow.theroofedcanyon.init.TRCItems.*;

import net.teamhollow.theroofedcanyon.TheRoofedCanyon;
import net.teamhollow.theroofedcanyon.block.helpers.vanilla.*;
import net.teamhollow.theroofedcanyon.entity.vanilla.TRCBoatEntity;
import net.teamhollow.theroofedcanyon.init.TRCBlocks;
import net.teamhollow.theroofedcanyon.init.TRCItems;
import net.teamhollow.theroofedcanyon.item.helpers.vanilla.TRCBoatItem;
import net.teamhollow.theroofedcanyon.registry.SpriteIdentifierRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SignItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class WoodBlocks {
    public WoodBlocksConfig config;
    public String id;

    public Block LOG;
    public Block STRIPPED_LOG;
    public Block WOOD;
    public Block STRIPPED_WOOD;
    public Block PLANKS;
    public Block LEAVES;
    public Block SLAB;
    public Block PRESSURE_PLATE;
    public Block FENCE;
    public Block FENCE_GATE;
    public Block SAPLING;
    public Block TRAPDOOR;
    public Block BUTTON;
    public Block STAIRS;
    public Block DOOR;
    public Block SIGN;
    public Block WALL_SIGN;

    public SignItem SIGN_ITEM;
    public SignType signType;

    public EntityType<TRCBoatEntity> BOAT;
    public TRCBoatItem BOAT_ITEM;

    public WoodBlocks(WoodBlocksConfig config) {
        this.config = config;
        this.id = config.id;

        // declare defaults
        LOG = createLogBlock(config.logTopMaterialColor, config.logSideMaterialColor);
        STRIPPED_LOG = createLogBlock(config.strippedLogTopMaterialColor, config.strippedLogSideMaterialColor);
        WOOD = new PillarBlock(AbstractBlock.Settings.of(config.woodMaterial, config.woodMaterialColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
        STRIPPED_WOOD = new PillarBlock(AbstractBlock.Settings.of(config.strippedWoodMaterial, config.strippedWoodMaterialColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
        PLANKS = new Block(AbstractBlock.Settings.of(config.planksMaterial, config.planksMaterialColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
        LEAVES = createLeavesBlock();
        SLAB = new SlabBlock(AbstractBlock.Settings.of(config.planksMaterial, config.planksMaterialColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
        PRESSURE_PLATE = new PublicPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(config.planksMaterial, config.planksMaterialColor).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
        FENCE = new FenceBlock(AbstractBlock.Settings.of(config.woodMaterial, config.planksMaterialColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
        FENCE_GATE = new FenceGateBlock(AbstractBlock.Settings.of(config.woodMaterial, config.planksMaterialColor).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
        SAPLING = new PublicSaplingBlock(config.saplingGenerator, AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
        TRAPDOOR = new PublicTrapdoorBlock(AbstractBlock.Settings.of(config.woodMaterial, config.woodMaterialColor).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning(TRCBlocks::never));
        BUTTON = new PublicWoodButtonBlock(AbstractBlock.Settings.of(Material.SUPPORTED).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));
        STAIRS = new PublicStairsBlock(PLANKS.getDefaultState(), AbstractBlock.Settings.copy(PLANKS));
        DOOR = new PublicDoorBlock(AbstractBlock.Settings.of(config.woodMaterial, config.planksMaterialColor).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

        Identifier signTexture = new Identifier(TheRoofedCanyon.MOD_ID, "entity/signs/" + id);
        SIGN = new TRCSignBlock(signTexture, AbstractBlock.Settings.of(Material.WOOD).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD));
        WALL_SIGN = new TRCWallSignBlock(signTexture, AbstractBlock.Settings.of(Material.WOOD).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD).dropsLike(SIGN));
        Type<?> signBlockEntityType = Util.method_29187(TypeReferences.BLOCK_ENTITY, id + "_sign");
        Registry.register(Registry.BLOCK_ENTITY_TYPE, id + "_sign", BlockEntityType.Builder.create(SignBlockEntity::new, SIGN, WALL_SIGN).build(signBlockEntityType));

        // check config for default modifications
        if (config.LOG != null) LOG = config.LOG;
        if (config.STRIPPED_LOG != null) STRIPPED_LOG = config.STRIPPED_LOG;
        if (config.WOOD != null) WOOD = config.WOOD;
        if (config.STRIPPED_WOOD != null) STRIPPED_WOOD = config.STRIPPED_WOOD;
        if (config.PLANKS != null) PLANKS = config.PLANKS;
        if (config.LEAVES != null) LEAVES = config.LEAVES;
        if (config.SLAB != null) SLAB = config.SLAB;
        if (config.PRESSURE_PLATE != null) PRESSURE_PLATE = config.PRESSURE_PLATE;
        if (config.FENCE != null) FENCE = config.FENCE;
        if (config.FENCE_GATE != null) FENCE_GATE = config.FENCE_GATE;
        if (config.SAPLING != null) SAPLING = config.SAPLING;
        if (config.TRAPDOOR != null) TRAPDOOR = config.TRAPDOOR;
        if (config.BUTTON != null) BUTTON = config.BUTTON;
        if (config.STAIRS != null) STAIRS = config.STAIRS;
        if (config.DOOR != null) DOOR = config.DOOR;
        if (config.SIGN != null) SIGN = config.SIGN;
        if (config.WALL_SIGN != null) WALL_SIGN = config.WALL_SIGN;
        if (config.BOAT != null) BOAT = config.BOAT;
        if (config.BOAT_ITEM != null) BOAT_ITEM = config.BOAT_ITEM;

        // register blocks/items
        LOG = register(config.id + "_log", LOG);
        STRIPPED_LOG = register("stripped_" + config.id + "_log", STRIPPED_LOG);

        WOOD = register(config.id + "_wood", WOOD);
        STRIPPED_WOOD = register("stripped_" + config.id + "_wood", STRIPPED_WOOD);

        PLANKS = register(config.id + "_planks", PLANKS);
        LEAVES = register(config.id + "_leaves", LEAVES);
        SLAB = register(config.id + "_slab", SLAB);
        PRESSURE_PLATE = register(config.id + "_pressure_plate", PRESSURE_PLATE);
        FENCE = register(config.id + "_fence", FENCE);
        FENCE_GATE = register(config.id + "_fence_gate", FENCE_GATE);
        SAPLING = register(config.id + "_sapling", SAPLING);
        TRAPDOOR = register(config.id + "_trapdoor", TRAPDOOR);
        BUTTON = register(config.id + "_button", BUTTON);
        STAIRS = register(config.id + "_stairs", STAIRS);
        DOOR = register(config.id + "_door", DOOR);

        SIGN = register(config.id + "_sign", SIGN, false);
        WALL_SIGN = register(config.id + "_wall_sign", WALL_SIGN, false);
        SIGN_ITEM = TRCItems.register(config.id, (SignBlock)SIGN, (WallSignBlock)WALL_SIGN);

        BOAT = register(this, config.vanillaBoatType);
        BOAT_ITEM = register(this);

        // block properties
        if (config.isFlammable) {
            int baseBurnChance = config.baseBurnChance;
            int largeBurnChance = baseBurnChance * 6;

            int baseSpreadChance = config.baseSpreadChance;
            int smallSpreadChance = baseSpreadChance / 4;
            int largeSpreadChance = baseSpreadChance * 3;

            FlammableBlockRegistry INSTANCE = FlammableBlockRegistry.getDefaultInstance();
            INSTANCE.add(PLANKS, baseBurnChance, baseSpreadChance);
            INSTANCE.add(SLAB, baseBurnChance, baseSpreadChance);
            INSTANCE.add(FENCE_GATE, baseBurnChance, baseSpreadChance);
            INSTANCE.add(FENCE, baseBurnChance, baseSpreadChance);
            INSTANCE.add(STAIRS, baseBurnChance, baseSpreadChance);
            INSTANCE.add(LOG, baseBurnChance, smallSpreadChance);
            INSTANCE.add(STRIPPED_LOG, baseBurnChance, smallSpreadChance);
            INSTANCE.add(STRIPPED_WOOD, baseBurnChance, smallSpreadChance);
            INSTANCE.add(WOOD, baseBurnChance, smallSpreadChance);
            INSTANCE.add(LEAVES, largeBurnChance, largeSpreadChance);
        }
        if (config.canBeUsedAsFuel) {
            int fenceFuelTime = 300;

            FuelRegistry INSTANCE = FuelRegistry.INSTANCE;
            INSTANCE.add(FENCE, fenceFuelTime);
            INSTANCE.add(FENCE_GATE, fenceFuelTime);
        }

        // stripping functionality
        this.addStrippingFunctionality(LOG, STRIPPED_LOG);
        this.addStrippingFunctionality(WOOD, STRIPPED_WOOD);
    }

    @Environment(EnvType.CLIENT)
    public void clientRegistries() {
        // render layers
        BlockRenderLayerMap INSTANCE = BlockRenderLayerMap.INSTANCE;
        INSTANCE.putBlock(LEAVES, RenderLayer.getCutoutMipped());
        INSTANCE.putBlocks(RenderLayer.getCutout(), SAPLING, TRAPDOOR, DOOR);

        // leaf colours
        int leavesColor = config.leavesColor;
        if (leavesColor != 0) {
            ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
                return leavesColor;
            }, LEAVES);
            ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
                return leavesColor;
            }, LEAVES);
        }

        // signs
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, new Identifier(TheRoofedCanyon.MOD_ID, "entity/signs/" + id + ".png")));
    }

    public void addStrippingFunctionality(Block toStrip, Block result) {
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if (player.getStackInHand(hand).getItem().isIn(FabricToolTags.AXES) && world.getBlockState(hit.getBlockPos()).getBlock() == toStrip) {
                BlockPos blockPos = hit.getBlockPos();
                BlockState blockState = world.getBlockState(blockPos);

                world.playSound(player, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!world.isClient) {
                    world.setBlockState(blockPos,
                            result.getDefaultState().with(PillarBlock.AXIS, blockState.get(PillarBlock.AXIS)),
                            11);
                    if (!player.isCreative()) {
                        ItemStack stack = player.getStackInHand(hand);
                        stack.damage(1, player, ((p) -> p.sendToolBreakStatus(hand)));
                    }
                }

                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        });
    }
}
