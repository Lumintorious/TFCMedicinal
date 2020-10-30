package com.lumintorious.tfcmedicinal.compat.jei

import java.util.{Collections, List}

import com.lumintorious.tfcmedicinal.`object`.mpestle.MPestleRecipe
import mezz.jei.api.ingredients._
import mezz.jei.api.recipe.IRecipeWrapper
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack

class MPestleRecipeWrapper(
	private val inputs: List[ItemStack],
	private val output: ItemStack,
	private val chance: Float
)
extends IRecipeWrapper
{
	def this(original: MPestleRecipe) =
	{
		this(original.input.getValidIngredients, original.output, original.chance)
	}

	override def getIngredients(ingredients: IIngredients){
		ingredients.setInputLists(VanillaTypes.ITEM, Collections.singletonList(inputs))
		ingredients.setOutput(VanillaTypes.ITEM, output)
	}

	override def drawInfo(minecraft: Minecraft, recipeWidth: Int, recipeHeight: Int, mouseX: Int, mouseY: Int){
		var text = ""
		text = (this.chance * 100).toInt + "%"
		val x = 62f
		val y = 5f
		minecraft.fontRenderer.drawString(text, x, y, 0x000000, false)
	}
}
