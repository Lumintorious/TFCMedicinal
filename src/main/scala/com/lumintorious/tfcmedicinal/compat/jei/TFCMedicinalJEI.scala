package com.lumintorious.tfcmedicinal
package compat.jei

import java.util.ArrayList

import com.lumintorious.tfcmedicinal.`object`.mpestle._
import com.lumintorious.tfcmedicinal.fluids.BaseConcoction
import com.lumintorious.tfcmedicinal.ingredients.MedicineVial
import mezz.jei.api._
import mezz.jei.api.recipe._
import net.minecraft.item.ItemStack

import scala.collection.JavaConversions._

@JEIPlugin
class TFCMedicinalJEI extends IModPlugin
{
	override def register(registry: IModRegistry){
		registry.addRecipeCatalyst(new ItemStack(MPestleItem), "mpestle")
		val wrappers = new ArrayList[IRecipeWrapper]
		for(recipe <- MPestleRecipe.recipes)
		{
			for(input <- recipe.input.getValidIngredients)
			{
				wrappers.add(new MPestleRecipeWrapper(List(input), recipe.output, recipe.chance))
			}
		}
		registry.addRecipes(wrappers, "mpestle")

		registry.addDescription(MedicineVial.Stack(BaseConcoction), "tooltip.base_concoction.description".localize())
	}

	override def registerCategories(registry: IRecipeCategoryRegistration){
		val helpers   : IJeiHelpers = registry.getJeiHelpers
		val guiHelpers: IGuiHelper  = helpers.getGuiHelper
		registry.addRecipeCategories(new MPestleRecipeCategory(guiHelpers))
	}
}