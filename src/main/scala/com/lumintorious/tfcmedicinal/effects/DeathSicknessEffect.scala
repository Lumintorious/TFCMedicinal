package com.lumintorious.tfcmedicinal
package effects

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.GameType
import net.minecraftforge.event.entity.EntityEvent
import net.minecraftforge.event.entity.living.PotionEvent.{PotionAddedEvent, PotionExpiryEvent, PotionRemoveEvent}
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent

@Mod.EventBusSubscriber
object DeathSicknessEffect extends MedicinalEffect("death_sickness", true, 0xFF000000)
{
	setIconIndex(1, 0)

	def whenActive(event: EntityEvent)(f: EntityPlayer => Unit) =
	{
		event.getEntity match
		{
			case p: EntityPlayer if p.isPotionActive(this) => f
			case _ =>
		}
	}

	def whenActive(event: PlayerTickEvent)(f: EntityPlayer => Unit) =
	{
		event.player match
		{
			case p: EntityPlayer if p.isPotionActive(this) => f
			case _ =>
		}
	}

	@SubscribeEvent
	def onActivation(event: PotionAddedEvent): Unit =
	{
		if(event == null) return
		if(event.getPotionEffect == null) return
		if(event.getPotionEffect.getPotion == this)
		{
			Cast(event.getEntity).run[EntityPlayer]
			{ player =>
				if(!player.isCreative && !player.isSpectator)
				{
					player.setGameType(GameType.ADVENTURE)
				}
			}
		}
	}

	@SubscribeEvent
	def onExpiry(event: PotionExpiryEvent): Unit =
	{
		if(event == null) return
		if(event.getPotionEffect == null) return
		if(event.getPotionEffect.getPotion == this)
		{
			Cast(event.getEntity).run[EntityPlayer]
			{ player =>
				if(!player.isCreative && !player.isSpectator)
				{
					player.setGameType(GameType.SURVIVAL)
				}
			}
		}
	}

	@SubscribeEvent
	def onRemoval(event: PotionRemoveEvent): Unit =
	{
		if(event == null) return
		if(event.getPotionEffect == null) return
		if(event.getPotionEffect.getPotion == this)
		{
			Cast(event.getEntity).run[EntityPlayer]
			{ player =>
				if(!player.isCreative && !player.isSpectator)
				{
					player.setGameType(GameType.SURVIVAL)
				}
			}
		}
	}
}
