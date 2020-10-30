package com.lumintorious.tfcmedicinal.util

import net.dries007.tfc.api.registries.TFCRegistries
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.oredict.OreDictionary

case class OreHelper(name: String)
{
	def add(items: Item*)
	{
		items.foreach(item => OreDictionary.registerOre(name, item))
	}

	def addPlants(items: ResourceLocation*)
	{
		items.foreach(item => OreDictionary.registerOre(name, BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(item))))
	}
}