package com.lumintorious.tfcmedicinal.gui

import com.lumintorious.tfcmedicinal.fluids.RejuvenatingConcoction
import com.lumintorious.tfcmedicinal.ingredients.MedicineVial
import net.minecraft.creativetab.CreativeTabs

object MedicinalCreativeTab extends CreativeTabs("tfcmedicinal")
{
	override def createIcon() = MedicineVial.Stack(RejuvenatingConcoction)
}
