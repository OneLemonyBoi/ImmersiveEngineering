/*
 * BluSunrize
 * Copyright (c) 2017
 *
 * This code is licensed under "Blu's License of Common Sense"
 * Details can be found in the license file in the root folder of this project
 */

package blusunrize.immersiveengineering.common.blocks;

import blusunrize.immersiveengineering.api.IEProperties;
import blusunrize.immersiveengineering.common.blocks.generic.MultiblockPartTileEntity;
import blusunrize.immersiveengineering.common.util.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.Property;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.apache.commons.lang3.ArrayUtils;

public abstract class IEMultiblockBlock extends IETileProviderBlock
{
	public IEMultiblockBlock(String name, Properties props, Property<?>... additionalProperties)
	{
		//TODO
		super(name, props, BlockItemIE::new, ArrayUtils.addAll(additionalProperties, IEProperties.FACING_HORIZONTAL, IEProperties.MULTIBLOCKSLAVE));
		setMobility(PushReaction.BLOCK);
	}

	@Override
	public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving)
	{
		if(state.getBlock()!=newState.getBlock())
		{
			TileEntity tileEntity = world.getTileEntity(pos);
			if(tileEntity instanceof IEBaseTileEntity)
				((IEBaseTileEntity)tileEntity).setOverrideState(state);
			if(tileEntity instanceof MultiblockPartTileEntity)
				((MultiblockPartTileEntity<?>)tileEntity).disassemble();
		}
		super.onReplaced(state, world, pos, newState, isMoving);
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
	{
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof MultiblockPartTileEntity)
			return Utils.getPickBlock(((MultiblockPartTileEntity)te).getOriginalBlock(), target, player);
		return ItemStack.EMPTY;
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		//Don't add multiblocks to the creative tab/JEI
	}
}