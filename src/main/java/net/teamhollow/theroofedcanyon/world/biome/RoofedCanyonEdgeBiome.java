package net.teamhollow.theroofedcanyon.world.biome;

import net.teamhollow.theroofedcanyon.init.TRCEntities;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public final class RoofedCanyonEdgeBiome extends Biome {
    public static final String id = "roofed_canyon_edge";

	public RoofedCanyonEdgeBiome() {
        super(
            new Biome.Settings()
                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.FOREST)
                .depth(4.0F)
                .scale(0.2F)
                .temperature(0.8F)
                .downfall(0.4F)
                .effects(
                    new BiomeEffects.Builder()
                        .waterColor(4159204)
                        .waterFogColor(329011)
                        .fogColor(12638463)
                        .moodSound(BiomeMoodSound.CAVE)
                        .build()
                )
                .parent(null)
        );
        DefaultBiomeFeatures.addDefaultUndergroundStructures(this);
        DefaultBiomeFeatures.addDungeons(this);
        DefaultBiomeFeatures.addForestFlowers(this);
        DefaultBiomeFeatures.addMineables(this);
        DefaultBiomeFeatures.addDefaultOres(this);
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addJungleGrass(this);

        this.addSpawn(SpawnGroup.CREATURE, new Biome.SpawnEntry(TRCEntities.GRUBWORM, 8, 4, 4));
    }
}
