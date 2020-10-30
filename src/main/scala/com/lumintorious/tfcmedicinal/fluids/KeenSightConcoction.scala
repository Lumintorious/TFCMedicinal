package com.lumintorious.tfcmedicinal
package fluids

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect

object KeenSightConcoction extends MedicinalFluid("keen_sight_concoction", 0xFF0000FF)
{
	override def onDrink(player: EntityPlayer) =
	{
		player.status("sight")
		player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, Conf.keenSightDuration, 0, false, false))
	}
}