package com.lumintorious.tfcmedicinal.`object`.mpestle

import com.lumintorious.tfcmedicinal.`object`.mpestle.MPestleCapability.Cap
import com.lumintorious.tfcmedicinal.`object`.mpestle.MPestleItem.Stack
import net.minecraft.inventory.InventoryBasic
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities._
import net.minecraftforge.common.util.INBTSerializable
import net.minecraftforge.items.wrapper.InvWrapper

import scala.util.Try

object MPestleCapability
{
	@CapabilityInject(classOf[MPestleCapability])
	var Cap: Capability[MPestleCapability] = _

	def on(stack: Stack)(f: MPestleCapability => Unit): Unit =
	{
		if(stack == null) return
		stack.getCapability(Cap, null) match
		{
			case c: MPestleCapability => f(c)
			case _ =>
		}
	}
}

case class MPestleCapability(stack: Stack, nbt: NBTTagCompound)
extends InvWrapper(new InventoryBasic("", false, 2))
with ICapabilityProvider
with INBTSerializable[NBTTagCompound]
{
	this.deserializeNBT(nbt)

	def inventory = getInv

	def input:  Stack = getStackInSlot(0)
	def output: Stack = getStackInSlot(1)

	def input_= (s: Stack) = setStackInSlot(0, s)
	def output_=(s: Stack) = setStackInSlot(1, s)

	private[tfcmedicinal] var soundCd: Int = 0

	def work(): Unit =
	{
		MPestleRecipe.find(input).foreach
		{ recipe =>
			if(recipe.chance > Math.random()) putResult(recipe.output)
			input.shrink(1)
		}

	}

	def putResult(stack: Stack): Boolean =
	{
		if(stack.isEmpty) return false
		if(output.isEmpty)
		{
			output = stack
			true
		}
		else if(ItemStack.areItemStackTagsEqual(stack, output) && ItemStack.areItemsEqual(stack, output))
		{
			val count = stack.getCount
			output.setCount(output.getCount + count)
			true
		}
		else
		{
			false
		}
	}

	override def hasCapability(capability: Capability[_], facing: EnumFacing) = capability == Cap

	override def getCapability[T](capability: Capability[T], facing: EnumFacing) =
		(if(capability == Cap) this else null).asInstanceOf[T]

	override def serializeNBT() = new NBTTagCompound()
		{
			setTag("input", input.serializeNBT())
			setTag("output", output.serializeNBT())
		}

	override def deserializeNBT(nbt: NBTTagCompound) =
	{
		Option(nbt).foreach
		{ nbt =>
			Try(input = new Stack(nbt.getCompoundTag("input")))
			Try(output = new Stack(nbt.getCompoundTag("output")))
		}

	}
}
