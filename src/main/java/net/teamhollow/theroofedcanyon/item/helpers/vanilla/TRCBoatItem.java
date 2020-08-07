package net.teamhollow.theroofedcanyon.item.helpers.vanilla;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import net.teamhollow.theroofedcanyon.entity.vanilla.TRCBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

public class TRCBoatItem extends Item {
	private static final Predicate<Entity> RIDERS = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::collides);
	private final Supplier<EntityType<TRCBoatEntity>> boatSupplier;

	public TRCBoatItem(Supplier<EntityType<TRCBoatEntity>> boatSupplier, Item.Settings settings) {
		super(settings);

		this.boatSupplier = boatSupplier;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);
		HitResult hit = rayTrace(world, player, RayTraceContext.FluidHandling.ANY);

		if (hit.getType() != HitResult.Type.BLOCK) {
			return new TypedActionResult<>(ActionResult.PASS, stack);
		}

		Vec3d rotation = player.getRotationVec(1.0F);

		List<Entity> entities = world.getEntities(player, player.getBoundingBox().stretch(rotation.multiply(5.0D)).expand(1.0D), RIDERS);

		if (!entities.isEmpty()) {
			Vec3d playerCameraPos = player.getCameraPosVec(1.0F);

			for (Entity entity : entities) {
				Box box = entity.getBoundingBox().expand(entity.getTargetingMargin());
				if (box.contains(playerCameraPos)) {
					return new TypedActionResult<>(ActionResult.PASS, stack);
				}
			}
		}

		TRCBoatEntity boat = createBoat(world, hit.getPos().x, hit.getPos().y, hit.getPos().z);

		boat.yaw = player.yaw;

		if (!world.doesNotCollide(boat, boat.getBoundingBox().expand(-0.1D))) {
			return new TypedActionResult<>(ActionResult.FAIL, stack);
		}

		if (!world.isClient) {
			world.spawnEntity(boat);
		}

		if (!player.abilities.creativeMode) {
			stack.decrement(1);
		}

		player.incrementStat(Stats.USED.getOrCreateStat(this));

		return new TypedActionResult<>(ActionResult.SUCCESS, stack);
	}


	private TRCBoatEntity createBoat(World world, double x, double y, double z) {
		TRCBoatEntity entity = boatSupplier.get().create(world);
		if (entity != null) {
			entity.setPos(x, y, z);
			entity.updatePosition(x, y, z);
			entity.setVelocity(Vec3d.ZERO);
			entity.prevX = x;
			entity.prevY = y;
			entity.prevZ = z;
		}
		return entity;
	}
}
