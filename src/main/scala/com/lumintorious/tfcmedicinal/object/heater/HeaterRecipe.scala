package com.lumintorious.tfcmedicinal
package `object`.heater

import com.lumintorious.tfcmedicinal.fluids._
import com.lumintorious.tfcmedicinal.ingredients._
import net.dries007.tfc.objects.fluids.FluidsTFC
import net.dries007.tfc.objects.inventory.ingredient.IIngredient
import net.minecraft.init.Items
import net.minecraft.item._
import net.minecraftforge.fluids._
import net.minecraftforge.fluids.capability._

import scala.collection.mutable.ArrayBuffer
import scala.util.Try

object HeaterRecipe
{
	private val recipes: ArrayBuffer[HeaterRecipe] = ArrayBuffer()

	def +=(recipe: HeaterRecipe) = recipes += recipe
	def find(input: Fluid, ingredient: ItemStack): Option[HeaterRecipe] =
	{
		recipes.find(recipe => recipe.input.test(new FluidStack(input, 100)) && recipe.ingredient.test(ingredient))
	}

	def find(input: ItemStack, ingredient: ItemStack): Option[HeaterRecipe] =
	{
		input.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) match
		{
			case handler: IFluidHandlerItem => Try(find(handler.drain(100, false).getFluid, ingredient)).getOrElse(None)
			case _ => None
		}
	}

	locally
	{
		this += new HeaterRecipe(FluidsTFC.SALT_WATER.get(), Items.FERMENTED_SPIDER_EYE, BaseConcoction)
	}
}

class HeaterRecipe
(
	val input: IIngredient[FluidStack],
	val ingredient: IIngredient[ItemStack],
	val output: Fluid,
	val ticks: Int
)
{
	def this(input: Fluid, ingredient: Item, output: Fluid, ticks: Int = 1000) =
	{
		this(IIngredient.of(input, 100), IIngredient.of(ingredient), output, ticks)
	}
	def outputStack: ItemStack = MedicineVial.Stack(output)
}
