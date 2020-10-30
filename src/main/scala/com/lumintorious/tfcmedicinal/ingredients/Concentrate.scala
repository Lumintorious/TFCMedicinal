package com.lumintorious.tfcmedicinal
package ingredients

import java.util

import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.oredict.OreDictionary

object Concentrate
{
	val HERBAL    = new Concentrate("herbal")
	val LIVELY    = new Concentrate("lively")
	val ENERGETIC = new Concentrate("energetic")
	val FOCUSED   = new Concentrate("focused")
	val EARTHY    = new Concentrate("earthy")
	val PURE      = new Concentrate("pure")
	val FRAGRANT  = new Concentrate("fragrant")
}

class Concentrate(name: String) extends ItemTFCM("concentrate/" + name)
{
	OreDictionary.registerOre("concentrate" + name.capitalize, this)

	override def addInformation(
		stack: ItemStack,
		worldIn: World,
		tooltip: util.List[String],
		flagIn: ITooltipFlag
	) =
	{
		tooltip.add(s"tooltip.extract_${name}".localize())
	}
}