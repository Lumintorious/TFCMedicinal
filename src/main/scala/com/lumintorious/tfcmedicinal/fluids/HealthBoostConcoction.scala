package com.lumintorious.tfcmedicinal
package fluids

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect

object HealthBoostConcoction extends MedicinalFluid("health_boost_concoction", 0xFFFF0000)
{
	override def onDrink(player: EntityPlayer) =
	{
		player.status("health_boost")
		player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, Conf.healthBoostDuration, 1, false, false))
	}
}


