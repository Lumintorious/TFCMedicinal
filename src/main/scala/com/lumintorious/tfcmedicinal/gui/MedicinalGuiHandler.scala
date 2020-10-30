package com.lumintorious.tfcmedicinal.gui

import com.lumintorious.tfcmedicinal.`object`.mpestle._
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

object MedicinalGuiHandler extends IGuiHandler
{
	override def getServerGuiElement(
		ID: Int,
		player: EntityPlayer,
		world: World,
		x: Int,
		y: Int,
		z: Int
	): AnyRef =
	{
		if(ID == 0) return MPestleContainer(player)
		null
//		entries(ID).getServerGui(player, world, x, y, z)
	}

	override def getClientGuiElement(
		ID: Int,
		player: EntityPlayer,
		world: World,
		x: Int,
		y: Int,
		z: Int
	): AnyRef =
	{
		if(ID == 0) return MPestleGui(MPestleContainer(player))
		null
//		entries(ID).getClientGui(player, world, x, y, z)
	}
}