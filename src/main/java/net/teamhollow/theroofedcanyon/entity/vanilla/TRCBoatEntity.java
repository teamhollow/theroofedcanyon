package net.teamhollow.theroofedcanyon.entity.vanilla;

import net.teamhollow.theroofedcanyon.TheRoofedCanyon;
import net.teamhollow.theroofedcanyon.util.TRCBoat;
import io.netty.buffer.Unpooled;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;

public class TRCBoatEntity extends BoatEntity {
	private TRCBoat boat;

	public TRCBoatEntity(EntityType<? extends TRCBoatEntity> type, World world, TRCBoat boat) {
		super(type, world);

		this.boat = boat;
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
	}

	@Override
	public Item asItem() {
		return boat.asItem();
	}

	public Item asPlanks() {
		return boat.asPlanks();
	}

	public Identifier getBoatSkin() {
		return boat.getSkin();
	}

	@Override
	protected void writeCustomDataToTag(CompoundTag tag) {}

	@Override
	protected void readCustomDataFromTag(CompoundTag tag) {}

	private boolean isOnLand() {
		return getPaddleSoundEvent() == SoundEvents.ENTITY_BOAT_PADDLE_LAND;
    }

    @Override
    public boolean isGlowing() {
        return false;
    }

	@Override
	protected void fall(double double_1, boolean boolean_1, BlockState state, BlockPos pos) {

		float savedFallDistance = this.fallDistance;

		super.fall(double_1, false, state, pos);

		if(!this.hasVehicle() && boolean_1) {
			this.fallDistance = savedFallDistance;

			if (this.fallDistance > 3.0F) {
				if (!isOnLand()) {
					this.fallDistance = 0.0F;
					return;
				}

				this.handleFallDamage(this.fallDistance, 1.0F);
				if (!this.world.isClient && !this.removed) {
					this.remove();
					if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
						for(int i = 0; i < 3; i++) {
							this.dropItem(this.asPlanks());
						}

						for(int i = 0; i < 2; i++) {
							this.dropItem(Items.STICK);
						}
					}
				}
			}

			this.fallDistance = 0.0F;
		}
	}

	@Override
	public Packet<?> createSpawnPacket() {
		final PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());

		buf.writeVarInt(this.getEntityId());
		buf.writeUuid(this.uuid);
		buf.writeVarInt(Registry.ENTITY_TYPE.getRawId(this.getType()));
		buf.writeDouble(this.getX());
		buf.writeDouble(this.getY());
		buf.writeDouble(this.getZ());
		buf.writeByte(MathHelper.floor(this.pitch * 256.0F / 360.0F));
		buf.writeByte(MathHelper.floor(this.yaw * 256.0F / 360.0F));

		return ServerSidePacketRegistry.INSTANCE.toPacket(new Identifier(TheRoofedCanyon.MOD_ID, "spawn_boat"), buf);
	}

	@Override
	public void setBoatType(BoatEntity.Type type) {
		throw new UnsupportedOperationException("Tried to set the boat type of a " + TheRoofedCanyon.MOD_NAME + " boat");
	}

	@Override
	public BoatEntity.Type getBoatType() {
		return boat.getVanillaType();
	}
}
