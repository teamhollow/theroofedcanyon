package net.teamhollow.theroofedcanyon.client.network;

import java.util.UUID;

import net.teamhollow.theroofedcanyon.TheRoofedCanyon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.PacketContext;

@Environment(EnvType.CLIENT)
public class BoatSpawnNetworkHandler {
    public void register() {
        ClientSidePacketRegistry.INSTANCE.register(new Identifier(TheRoofedCanyon.MOD_ID, "spawn_boat"), BoatSpawnNetworkHandler::accept);
    }

    @SuppressWarnings("rawtypes")
    public static void accept(PacketContext context, PacketByteBuf buffer) {
        final MinecraftClient client = MinecraftClient.getInstance();

        int id = buffer.readVarInt();
        UUID uuid = buffer.readUuid();
        EntityType type = Registry.ENTITY_TYPE.get(buffer.readVarInt());
        double x = buffer.readDouble();
        double y = buffer.readDouble();
        double z = buffer.readDouble();
        byte pitch = buffer.readByte();
        byte yaw = buffer.readByte();

        Entity entity = type.create(client.world);

        if (entity == null) {
            return;
        }

        entity.setEntityId(id);
        entity.setUuid(uuid);
        entity.updatePosition(x, y, z);
        entity.updateTrackedPosition(x, y, z);
        entity.pitch = pitch * 360 / 256F;
        entity.yaw = yaw * 360 / 256F;

        if (client.isOnThread()) {
            spawn(client, entity);
        } else {
            client.execute(() -> spawn(client, entity));
        }
    }

    private static void spawn(MinecraftClient client, Entity entity) {
        final ClientWorld world = client.world;

        if (world == null) {
            return;
        }

        world.addEntity(entity.getEntityId(), entity);
    }
}
