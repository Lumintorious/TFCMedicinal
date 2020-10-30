package com.lumintorious.tfcmedicinal.ingredients

import com.lumintorious.tfcmedicinal.gui.MedicinalCreativeTab
import net.dries007.tfc.api.capability.size._
import net.dries007.tfc.objects.items.ItemTFC
import net.minecraft.item._

import scala.collection.mutable.ArrayBuffer

object ItemTFCM
{
	val all: ArrayBuffer[Item] = ArrayBuffer()
	val holders = Seq(Concentrate, Extract, Catalyst)
}

class ItemTFCM(id: String, shouldRegister: Boolean = true) extends ItemTFC
{
	setCreativeTab(MedicinalCreativeTab)
	setRegistryName(id)
	setTranslationKey(this.getRegistryName.toString)

	if(shouldRegister) ItemTFCM.all += this

	override def getSize(itemStack: ItemStack) = Size.TINY
	override def getWeight(itemStack: ItemStack) = Weight.VERY_LIGHT
}