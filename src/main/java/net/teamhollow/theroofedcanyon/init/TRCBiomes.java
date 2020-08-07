package net.teamhollow.theroofedcanyon.init;

import net.teamhollow.theroofedcanyon.TheRoofedCanyon;
import net.teamhollow.theroofedcanyon.world.biome.*;
import net.fabricmc.fabric.api.biomes.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biomes.v1.OverworldClimate;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class TRCBiomes {
    public static final Biome ROOFED_CANYON = register(RoofedCanyonBiome.id, new RoofedCanyonBiome());
    public static final Biome MODIFIED_ROOFED_CANYON = register(ModifiedRoofedCanyonBiome.id, new ModifiedRoofedCanyonBiome());
    public static final Biome ROOFED_CANYON_EDGE = register(RoofedCanyonEdgeBiome.id, new RoofedCanyonEdgeBiome());
    public static final Biome ROOFED_CANYON_HIGHLANDS = register(RoofedCanyonHighlandsBiome.id, new RoofedCanyonHighlandsBiome());

    public TRCBiomes() {
        // add biome to spawn
        OverworldBiomes.addContinentalBiome(ROOFED_CANYON, OverworldClimate.TEMPERATE, 0.3D);

        // add biome variants
        OverworldBiomes.addBiomeVariant(ROOFED_CANYON, MODIFIED_ROOFED_CANYON, 0.33D);
        OverworldBiomes.addEdgeBiome(ROOFED_CANYON, ROOFED_CANYON_EDGE, 1.0D);
        OverworldBiomes.addHillsBiome(ROOFED_CANYON, ROOFED_CANYON_HIGHLANDS, 1.0D);
    }

    private static Biome register(String id, Biome biome) {
        return Registry.register(Registry.BIOME, new Identifier(TheRoofedCanyon.MOD_ID, id), biome);
    }
}
