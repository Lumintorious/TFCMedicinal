package com.lumintorious.tfcmedicinal
package fluids

import net.dries007.tfc.objects.fluids.FluidsTFC
import net.dries007.tfc.objects.fluids.properties.DrinkableProperty
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids._

import scala.collection.mutable.ArrayBuffer


abstract class MedicinalFluid(name: String, color: Int, shouldRegister: Boolean = true)
extends Fluid(name, MedicinalFluid.STILL, MedicinalFluid.FLOW, color)
{
	fluid =>
	val wrapper = FluidsTFC.getWrapper(this).`with`(DrinkableProperty.DRINKABLE, new DrinkableProperty {
		override def onDrink(entityPlayer: EntityPlayer) =
			try
			{
				fluid.onDrink(entityPlayer)
			}
			catch
			{
				case e: Throwable => ()
			}
	})

	def onDrink(player: EntityPlayer): Unit = ()

	def spawnStack(amount: Int = 100): FluidStack = new FluidStack(this, amount)

	FluidRegistry.registerFluid(this)
	if(shouldRegister)
		MedicinalFluid.all += this
}

object MedicinalFluid
{
	private val STILL = new ResourceLocation("tfc", "blocks/fluid_still")
	private val FLOW  = new ResourceLocation("tfc", "blocks/fluid_flow")

	val all: ArrayBuffer[Fluid] = ArrayBuffer()
}