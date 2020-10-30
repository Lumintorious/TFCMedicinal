package com.lumintorious.tfcmedicinal.effects

import java.util.ArrayList

import com.lumintorious.tfcmedicinal.TFCMedicinal
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.util.text.TextComponentTranslation
import net.minecraftforge.fml.relauncher._

import scala.collection.mutable.ArrayBuffer

object MedicinalEffect
{
	val all: ArrayBuffer[MedicinalEffect] = ArrayBuffer()
}

abstract class MedicinalEffect(name: String, isBad: Boolean, color: Int) extends Potion(isBad, 0x00000000)
{
	setRegistryName(name)
	setPotionName("effect.tfcmedicinal." + name + ".name")

	override def getCurativeItems = new ArrayList[ItemStack]

	import net.minecraft.util.ResourceLocation

	private val POTION_ICONS = new ResourceLocation(TFCMedicinal.MODID, "textures/gui/potions.png")

	@SideOnly(Side.CLIENT)
	override def getStatusIconIndex =
	{
		Minecraft.getMinecraft.renderEngine.bindTexture(POTION_ICONS)
		super.getStatusIconIndex
	}

	def addTooltip(stack: ItemStack, list: java.util.List[String]) =
	{
		list.add(new TextComponentTranslation(this.getName).getFormattedText)
	}

	MedicinalEffect.all += this
}