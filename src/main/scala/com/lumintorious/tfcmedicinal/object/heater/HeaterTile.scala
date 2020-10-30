package com.lumintorious.tfcmedicinal
package `object`.heater

import com.lumintorious.tfcmedicinal.fluids.BaseConcoction
import com.lumintorious.tfcmedicinal.ingredients._
import net.dries007.tfc.client.particle.TFCParticles
import net.dries007.tfc.objects.te.TEFirePit
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SPacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util._
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fluids.capability.{CapabilityFluidHandler, IFluidHandlerItem}

import scala.util.Try

class HeaterTile extends TileEntity with ITickable
{
	var vialStack: ItemStack = ItemStack.EMPTY
	private var target: ItemStack = ItemStack.EMPTY

	def isHot = temperature > 100

	def isVial(stack: ItemStack): Boolean = stack.getItem == MedicineVial
	def isBaseConcoction(stack: ItemStack): Boolean =
	{
		stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) match
		{
			case fh: IFluidHandlerItem =>
			{
				fh.drain(100, false) match
				{
					case fs: FluidStack if fs.getFluid == BaseConcoction => true
					case _ => false
				}
			}
			case _ => false
		}
	}

	def onClick(player: EntityPlayer): Unit =
	{
		if(!target.isEmpty) return
		val playerStack = player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND)
		vialStack match
		{
			case ownStack if ownStack.isEmpty && isVial(playerStack) =>
				vialStack = playerStack.copy()
				playerStack.setCount(0)
			case ownStack if playerStack.isEmpty && !ownStack.isEmpty =>
				player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ownStack.copy())
				ownStack.setCount(0)
				target = ItemStack.EMPTY
			case ownStack if isHot =>
				testNewInput(playerStack)
			case _ =>
		}
	}

	private def testNewInput(ingredient: ItemStack): Unit =
	{
		if(isBaseConcoction(vialStack) && ingredient.getItem == Catalyst)
		{
			val cap = ingredient.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)
			val fluidStack = cap.drain(100, false)
			target = MedicineVial.Stack(fluidStack.getFluid)
			toBoil = 1000
			ingredient.shrink(1)
			world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_SWIM, SoundCategory.BLOCKS, 1.0F, 1.0F)
		}else
		{
			HeaterRecipe.find(vialStack, ingredient)
			for(rec <- HeaterRecipe.find(vialStack, ingredient))
			{
				ingredient.shrink(1)
				target = rec.outputStack
				toBoil = rec.ticks
				world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_SWIM, SoundCategory.BLOCKS, 1.0F, 1.0F)
			}
		}

	}

	private def resolveTarget(): Unit =
	{
		vialStack = target
		target = ItemStack.EMPTY
	}

	private var cd: Int = 0
	private var toBoil: Int = 0
	override def update(): Unit =
	{
		cd += 1
		if(!target.isEmpty)
		{
			if(toBoil > 0)
			{
				toBoil -= 1
			}
			else
			{
				resolveTarget()
			}
		}
		spawnParticles()
	}

	private def temperature: Int =
	{
		var temp = 0
		Cast(world.getTileEntity(pos.add(0, -1, 0))).run[TEFirePit]
		{ forge =>
			temp = forge.getField(TEFirePit.FIELD_TEMPERATURE)
		}
		temp
	}

	private def spawnParticles(): Unit =
	{
		if(vialStack.isEmpty) return
		if(isHot && cd % 8 == 0)
		{
			TFCParticles.STEAM.spawn(
				world,
				pos.getX + 0.5,
				pos.getY + 0.95,
				pos.getZ + 0.5,
				0D,
				0.3D,
				0D,
				20
			)
		}
		else if(!target.isEmpty && cd % 1 == 0)
		{
			TFCParticles.STEAM.spawn(
				world,
				pos.getX + 0.5,
				pos.getY + 0.95,
				pos.getZ + 0.5,
				0D,
				0.3D,
				0D,
				20
			)
		}
	}

	override def readFromNBT(nbt: NBTTagCompound) =
	{
		Try
		{
			vialStack = new ItemStack(nbt.getCompoundTag("vialStack"))
			target = new ItemStack(nbt.getCompoundTag("target"))
			toBoil = nbt.getInteger("toBoil")
		}
		super.readFromNBT(nbt)
	}

	override def writeToNBT(nbt: NBTTagCompound) =
	{
		nbt.setTag("vialStack", vialStack.writeToNBT(new NBTTagCompound()))
		nbt.setTag("target", target.writeToNBT(new NBTTagCompound()))
		nbt.setInteger("toBoil", toBoil)
		super.writeToNBT(nbt)
	}

	override def getUpdatePacket =
	{
		val nbt = new NBTTagCompound
		try{
			this.writeToNBT(nbt)
		}
		finally{

		}
		val metadata = getBlockMetadata
		new SPacketUpdateTileEntity(this.pos, metadata, nbt)
	}

	override def onDataPacket(net: NetworkManager, pkt: SPacketUpdateTileEntity) {
		try {
			this.readFromNBT(pkt.getNbtCompound)
		}finally{

		}
	}

	override def getUpdateTag = {
		val nbt = new NBTTagCompound
		this.writeToNBT(nbt)
		nbt
	}

	override def handleUpdateTag(tag: NBTTagCompound) {
		this.readFromNBT(tag)
	}

	override def getTileData = {
		val nbt = new NBTTagCompound
		this.writeToNBT(nbt)
		nbt
	}
}
