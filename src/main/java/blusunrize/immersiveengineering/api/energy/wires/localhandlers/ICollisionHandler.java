/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package blusunrize.immersiveengineering.api.energy.wires.localhandlers;

import net.minecraft.entity.Entity;

public interface ICollisionHandler
{
	//TODO more params!
	void onCollided(Entity e);
}
