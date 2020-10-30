package com.lumintorious.tfcmedicinal.effects

import com.lumintorious.tfcmedicinal.Cast
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.MobEffects
import net.minecraft.potion.PotionEffect
import net.minecraftforge.common.util.Constants.AttributeModifierOperation
import net.minecraftforge.event.entity.living.PotionEvent._
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber
object StrongLungsEffect extends MedicinalEffect("strong_lungs", false, 0x00000000)
{

	setIconIndex(3, 0)

	@SubscribeEvent
	def onActivation(event: PotionAddedEvent): Unit =
	{
		if(event == null) return
		if(event.getPotionEffect == null) return
		if(event.getPotionEffect.getPotion == this)
		{
			Cast(event.getEntity).run[EntityPlayer]
			{ player =>
				player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, event.getPotionEffect.getDuration, 0, false, false))
				val attr = player.getEntityAttribute(EntityLivingBase.SWIM_SPEED)
				if(!attr.hasModifier(mod))
				{
					attr.applyModifier(mod)
				}
			}
		}
	}

	private val mod = new AttributeModifier("strong_lungs", 2.5, AttributeModifierOperation.MULTIPLY)
	@SubscribeEvent
	def onExpiry(event: PotionExpiryEvent): Unit =
	{
		if(event == null) return
		if(event.getPotionEffect == null) return
		if(event.getPotionEffect.getPotion == this)
		{
			Cast(event.getEntity).run[EntityPlayer]
			{ player =>
				player.removePotionEffect(MobEffects.WATER_BREATHING)
				val attr = player.getEntityAttribute(EntityLivingBase.SWIM_SPEED)
				if(attr.hasModifier(mod))
				{
					attr.removeModifier(mod)
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
				player.removePotionEffect(MobEffects.WATER_BREATHING)
				val attr = player.getEntityAttribute(EntityLivingBase.SWIM_SPEED)
				if(attr.hasModifier(mod))
				{
					attr.removeModifier(mod)
				}
			}
		}
	}
}