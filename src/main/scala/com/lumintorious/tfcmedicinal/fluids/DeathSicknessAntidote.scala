package com.lumintorious.tfcmedicinal.fluids
import com.lumintorious.tfcmedicinal.EzPlayer
import com.lumintorious.tfcmedicinal.effects.DeathSicknessEffect
import net.minecraft.entity.player.EntityPlayer

object DeathSicknessAntidote extends MedicinalFluid("death_sickness_antidote", 0xFF151515)
{
	override def onDrink(player: EntityPlayer) =
	{
		player.status("antidote")
		player.removePotionEffect(DeathSicknessEffect)
	}
}