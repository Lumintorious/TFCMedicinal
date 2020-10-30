package com.lumintorious.tfcmedicinal
package fluids
import net.dries007.tfc.api.capability.food.FoodStatsTFC
import net.minecraft.entity.player.EntityPlayer

object NourishingConcoction extends MedicinalFluid("nourishing_concoction", 0xFF998800)
{
	override def onDrink(player: EntityPlayer) =
	{
		player.getFoodStats match
		{
			case stats: FoodStatsTFC =>
				player.status("nourished")
				stats.getNutrition.reset()
			case _ =>
		}
	}
}
