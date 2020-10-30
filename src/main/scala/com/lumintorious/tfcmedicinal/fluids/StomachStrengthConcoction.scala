package com.lumintorious.tfcmedicinal
package fluids

import com.lumintorious.tfcmedicinal.effects.StrongStomachEffect
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.potion.PotionEffect

object StomachStrengthConcoction extends MedicinalFluid("stomach_strength_concoction", 0xFF997700)
{
	override def onDrink(player: EntityPlayer) =
	{
		player.status("stomach")
		player.addPotionEffect(new PotionEffect(StrongStomachEffect, TFCMedicinalConfig.General.strongStomachDuration, 0, false, false))
	}
}