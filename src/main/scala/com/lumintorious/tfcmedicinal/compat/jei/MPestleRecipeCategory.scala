package com.lumintorious.tfcmedicinal.compat.jei

import com.lumintorious.tfcmedicinal.TFCMedicinal
import mezz.jei.api.IGuiHelper
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.ingredients._
import mezz.jei.api.recipe.IRecipeCategory
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation

class MPestleRecipeCategory(val guiHelper: IGuiHelper) extends IRecipeCategory[MPestleRecipeWrapper]
{
	val location = new ResourceLocation(TFCMedicinal.MODID, "textures/gui/jei/mpestle.png")
	val bg = guiHelper.createDrawable(location, 0, 0, 109, 18)

	override def getUid = "mpestle"

	override def getTitle = "Mortar and Pestle"

	override def getModName = TFCMedicinal.MODID
	override def getBackground = bg

	override def setRecipe(recipeLayout: IRecipeLayout,
		recipeWrapper: MPestleRecipeWrapper,
		ingredients: IIngredients
	) =
	{
		val isg = recipeLayout.getItemStacks

		isg.init(0, true, 0, 0)
		isg.init(1, false, 91, 0)


		val inputs  = ingredients.getInputs(VanillaTypes.ITEM).get(0)
		val outputs = ingredients.getOutputs(VanillaTypes.ITEM).get(0)

		isg.set(0, inputs.get(0))
		isg.set(1, outputs)
	}

	override def drawExtras(minecraft: Minecraft) =
	{

	}
}