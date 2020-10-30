package com.lumintorious.tfcmedicinal
package ingredients

import java.util

import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.oredict.OreDictionary

object Extract
{
	val HERBAL    = new Extract("herbal")
	val LIVELY    = new Extract("lively")
	val ENERGETIC = new Extract("energetic")
	val FOCUSED   = new Extract("focused")
	val EARTHY    = new Extract("earthy")
	val PURE      = new Extract("pure")
	val FRAGRANT  = new Extract("fragrant")
}

class Extract(name: String) extends ItemTFCM("extract/" + name)
{
	OreDictionary.registerOre("extract" + name.capitalize, this)

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