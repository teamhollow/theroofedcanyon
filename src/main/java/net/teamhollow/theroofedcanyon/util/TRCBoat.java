package net.teamhollow.theroofedcanyon.util;

import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class TRCBoat {
	private Item item;
	private Item planks;
	private Identifier skin;
	private BoatEntity.Type vanilla;

	public TRCBoat(Item item, Item planks, Identifier skin) {
		this(item, planks, skin, BoatEntity.Type.OAK);
	}

	public TRCBoat(Item item, Item planks, Identifier skin, BoatEntity.Type vanilla) {
		this.item = item;
		this.planks = planks;
		this.skin = skin;
		this.vanilla = vanilla;
	}

	public Item asItem() {
		return item;
	}

	public Item asPlanks() {
		return planks;
	}

	public Identifier getSkin() {
		return skin;
	}

	public BoatEntity.Type getVanillaType() {
		return vanilla;
	}
}
