package com.lumintorious.tfcmedicinal.compat.tfcambiental

import com.lumintorious.tfcmedicinal.ingredients.Catalyst
import net.minecraftforge.fml.common.Loader

object TFCAmbientalPlugin
{
	def init(): Unit =
	{
		if(Loader.isModLoaded("tfcambiental"))
		{
			successfulInit()
		}
	}

	def successfulInit(): Unit =
	{
		CoolingPanacea
		WarmingPanacea

		Catalyst.registerRecipe(CoolingPanacea, "3focused", "4herbal", "1fragrant", "1pure")
		Catalyst.registerRecipe(WarmingPanacea, "3lively", "3energetic", "1fragrant", "1pure")
	}
}
