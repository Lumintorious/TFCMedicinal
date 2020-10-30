package com.lumintorious.tfcmedicinal
package fluids

import com.lumintorious.tfcmedicinal.effects.StrongLungsEffect
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.potion.PotionEffect

object LungStrengthConcoction extends MedicinalFluid("lung_strength_concoction", 0xFF004455)
{
	override def onDrink(player: EntityPlayer) =
	{
		player.status("lungs")
		player.addPotionEffect(new PotionEffect(StrongLungsEffect, Conf.strongLungsDuration, 0, false, false))
	}
}