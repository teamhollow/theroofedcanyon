package net.teamhollow.theroofedcanyon;

import org.apache.logging.log4j.Level;

import net.teamhollow.theroofedcanyon.block.helpers.WoodBlocks;
import net.teamhollow.theroofedcanyon.init.TRCBlocks;
import net.teamhollow.theroofedcanyon.init.TRCEntities;
import net.teamhollow.theroofedcanyon.init.TRCParticleTypes;
import net.teamhollow.theroofedcanyon.util.Utils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.BoatEntityRenderer;

public class TheRoofedCanyonClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        log(Level.INFO, "Initializing client");

        new Utils();
        TRCParticleTypes.registerFactories();
        TRCEntities.registerRenderers();

        // biome colours
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> {
            return world != null && pos != null 
                    ? BiomeColors.getGrassColor(world, pos)
                    : GrassColors.getColor(0.5D, 1.0D);
        }, TRCBlocks.TURFWOOD.LEAVES);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            return GrassColors.getColor(0.5D, 1.0D);
        }, TRCBlocks.TURFWOOD.LEAVES);

        // wood client methods
        for (WoodBlocks wood : new WoodBlocks[] { TRCBlocks.TURFWOOD }) {
            wood.clientRegistries();
            EntityRendererRegistry.INSTANCE.register(
                wood.BOAT,
                (entityRenderDispatcher, context) -> new BoatEntityRenderer(entityRenderDispatcher)
            );
        }

        // render layers
        BlockRenderLayerMap INSTANCE = BlockRenderLayerMap.INSTANCE;
        INSTANCE.putBlock(TRCBlocks.TURFWOOD.LEAVES, RenderLayer.getCutoutMipped());

        Block[] cutoutRenderLayerBlocks = { TRCBlocks.TURFWOOD.SAPLING, TRCBlocks.VILEPOT_FLOWER };
        for (Block block : cutoutRenderLayerBlocks) {
            INSTANCE.putBlock(block, RenderLayer.getCutout());
        }

        log(Level.INFO, "Initialized client");
    }

    public static void log(Level level, String message) {
        TheRoofedCanyon.log(level, message);
    }
}
