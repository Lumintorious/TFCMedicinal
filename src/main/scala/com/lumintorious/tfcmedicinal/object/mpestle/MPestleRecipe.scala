package com.lumintorious.tfcmedicinal.`object`.mpestle

import com.lumintorious.tfcmedicinal.ingredients.Extract
import net.dries007.tfc.objects.inventory.ingredient.IIngredient
import net.minecraft.item._

import scala.collection.mutable.ArrayBuffer

object MPestleRecipe
{
	final val EMPTY = new MPestleRecipe(IIngredient.empty(), ItemStack.EMPTY, 0)
	private[tfcmedicinal] val recipes: ArrayBuffer[MPestleRecipe] = ArrayBuffer()

	def +=(recipe: MPestleRecipe) = recipes += recipe
	def ++=(recipess: Iterable[MPestleRecipe]) = recipes ++= recipess
	def find(input: ItemStack): Option[MPestleRecipe] =
	{
		recipes.find(r => r.input.test(input))
	}


	private def isPlant(ingredient: IIngredient[ItemStack]): Boolean =
	{
		ingredient.getValidIngredients.get(0).getItem.getRegistryName.toString.contains("plants/")
	}

	locally
	{
		recipes ++= Seq(
			new MPestleRecipe(IIngredient.of("livelyExtractHolder"), new ItemStack(Extract.LIVELY), 0.3F),
			new MPestleRecipe(IIngredient.of("herbalExtractHolder"), new ItemStack(Extract.HERBAL), 0.6F),
			new MPestleRecipe(IIngredient.of("focusedExtractHolder"), new ItemStack(Extract.FOCUSED), 1.0F),
			new MPestleRecipe(IIngredient.of("energeticExtractHolder"), new ItemStack(Extract.ENERGETIC), 0.3F),
			new MPestleRecipe(IIngredient.of("earthyExtractHolder"), new ItemStack(Extract.EARTHY), 1.0F),
			new MPestleRecipe(IIngredient.of("pureExtractHolder"), new ItemStack(Extract.PURE), 0.3F),
			new MPestleRecipe(IIngredient.of("fragrantExtractHolder"), new ItemStack(Extract.FRAGRANT), 0.3F)
		)
	}
}

class MPestleRecipe(
	val input: IIngredient[ItemStack],
	outputTemplate: ItemStack,
	val chance: Float
)
{
	def output = outputTemplate.copy()
}
