package com.lumintorious.tfcmedicinal
package compat.tfcambiental

import com.lumintorious.ambiental.effects.TempEffect
import com.lumintorious.tfcmedicinal.fluids.MedicinalFluid
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.potion.PotionEffect

object WarmingPanacea extends MedicinalFluid("warming_panacea", 0xFFFFCC00)
{
	override def onDrink(player: EntityPlayer) =
	{
		player.status("warm")
		player.addPotionEffect(new PotionEffect(TempEffect.WARM, Conf.temperaturePanaceaDuration, 0, false, false))
	}
}