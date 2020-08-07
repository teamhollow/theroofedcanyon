package net.teamhollow.theroofedcanyon.world.biome;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class RoofedCanyonHighlandsBiome extends Biome {
    public static final String id = "roofed_canyon_highlands";

	public RoofedCanyonHighlandsBiome() {
        super(
            new Biome.Settings()
                .configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_CONFIG)
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.FOREST)
                .depth(2.5F)
                .scale(0.2F)
                .temperature(0.7F)
                .downfall(0.5F)
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
        RoofedCanyonBiome.addFeatures(this);
        this.addSpawn(SpawnGroup.MONSTER, new Biome.SpawnEntry(EntityType.SLIME, 95, 4, 4));
    }
}
