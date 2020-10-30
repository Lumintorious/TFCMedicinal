package com.lumintorious.tfcmedicinal
package compat.tfcambiental

import com.lumintorious.ambiental.effects.{TempEffect}
import com.lumintorious.tfcmedicinal.fluids.MedicinalFluid
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.potion.PotionEffect

object CoolingPanacea extends MedicinalFluid("cooling_panacea", 0xFF00CCFF)
{
	override def onDrink(player: EntityPlayer) =
	{
		player.status("cool")
		player.addPotionEffect(new PotionEffect(TempEffect.COOL, Conf.temperaturePanaceaDuration, 0, false, false))
	}
}