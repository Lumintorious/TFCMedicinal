package com.lumintorious.tfcmedicinal
package fluids

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect

object RejuvenatingConcoction extends MedicinalFluid("rejuvenating_concoction", 0xFFFF6688)
{
	override def onDrink(player: EntityPlayer) =
	{
		player.status("rejuvenated")
		player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1000, 0, false, false))
	}
}