package com.lumintorious.tfcmedicinal
package effects

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent

import scala.collection.JavaConversions.{asJavaCollection, iterableAsScalaIterable}

@Mod.EventBusSubscriber
object StrongStomachEffect extends MedicinalEffect("strong_stomach", false, 0x00000000)
{
	lazy val enemies: Seq[String] = Seq("effect.confusion", "effect.hunger")


	setIconIndex(2, 0)

	@SubscribeEvent
	def onTick(event: PlayerTickEvent) =
	{
		val player = event.player
		if(player.getActivePotionEffects.map(_.getPotion()).contains(this))
		{
			for(potion <- player.getActivePotionEffects.map(_.getPotion()))
			{
				if(enemies.contains(potion.getName))
				{
					player.removePotionEffect(potion)
				}
			}
		}
	}
}