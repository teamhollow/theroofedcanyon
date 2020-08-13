package net.teamhollow.theroofedcanyon;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.teamhollow.theroofedcanyon.init.*;

public class TheRoofedCanyon implements ModInitializer {
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "theroofedcanyon";
    public static final String MOD_NAME = "The Roofed Canyon";

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
        new Identifier(MOD_ID, "item_group"),
        () -> new ItemStack(TRCBlocks.TURFWOOD.LOG)
    );

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");

        new TRCParticleTypes();

        new TRCItems();
        new TRCBlocks();
        new TRCEntities();

        log(Level.INFO, "Initialized");
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

    public static Identifier texture(String path) {
        return new Identifier(MOD_ID, "textures/" + path + ".png");
    }
}
