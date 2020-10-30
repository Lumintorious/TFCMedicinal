package com.lumintorious.tfcmedicinal
package fluids
import com.lumintorious.tfcmedicinal.effects.AcuityEffect
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.potion.PotionEffect

object AcuityConcoction extends MedicinalFluid("acuity_concoction", 0xFF0066CC)
{
	override def onDrink(player: EntityPlayer) =
	{
		player.status("acuity")
		player.addPotionEffect(new PotionEffect(AcuityEffect, Conf.acuityDuration, 0, false, false))
	}
}