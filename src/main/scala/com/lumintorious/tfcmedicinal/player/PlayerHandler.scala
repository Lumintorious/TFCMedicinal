package com.lumintorious.tfcmedicinal
package player

import com.lumintorious.tfcmedicinal.effects._
import net.dries007.tfc.ConfigTFC
import net.dries007.tfc.api.capability.food._
import net.dries007.tfc.objects.inventory.ingredient.IIngredient
import net.dries007.tfc.world.classic.biomes.BiomesTFC
import net.minecraft.entity._
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.potion._
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.common.util.Constants.AttributeModifierOperation
import net.minecraftforge.event.entity.living._
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent

import scala.collection.mutable.ListBuffer

@Mod.EventBusSubscriber
object PlayerHandler
{
	val playersWithAcuity = new ListBuffer[String]

	def getSurfacePos(world: World, x: Int, z: Int): Int =
	{
		val blocks = 2 until 253
		for(inversePos <- blocks)
		{
			val pos = 255 - inversePos
			if(world.getBlockState(new BlockPos(x, pos, z)) != Blocks.AIR.getDefaultState)
			{
				return pos + 1
			}
		}
		180
	}

	@SubscribeEvent
	def onEat(event: LivingEntityUseItemEvent.Finish): Unit =
	{
		val random = Math.random()

		Cast(event.getEntity).run[EntityPlayer]
		{ player =>
			val stack = player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND)
			if(Conf.enableParasites)
			{
				stack.getCapability(CapabilityFood.CAPABILITY, null) match
				{
					case f: IFood if !f.getTraits.contains(FoodTrait.WOOD_GRILLED) =>
						if((IIngredient.of("categoryMeatCooked").test(stack)) && random < 0.05 && player.ticksExisted > 72000)
						{
							player.status("parasites")
							player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("hunger"), 1000, 20))
						}
						if((IIngredient.of("categoryMeat").test(stack)) && random < 0.25)
						{
							player.status("parasites")
							player.addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("hunger"), 1000, 20))
						}

					case _ =>
				}
			}
		}
	}

	@SubscribeEvent
	def onEntityBreak(event: BreakSpeed) =
	{
		if(event.getEntityPlayer.getPosition.getY < 135)
		{
			if(!event.getEntityPlayer.isPotionActive(StrongLungsEffect))
			{
				event.setNewSpeed(event.getNewSpeed * 0.9F)
			}
		}
	}

	private val mod = new AttributeModifier("bad_lungs", - 0.1, AttributeModifierOperation.MULTIPLY)
	@SubscribeEvent
	def onRun(event: PlayerTickEvent) =
	{
		val attr = event.player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
		val original = attr.getBaseValue
		if(event.player.getPosition.getY < 135)
		{
			if(!event.player.isPotionActive(StrongLungsEffect))
			{
				if(!attr.hasModifier(mod))
				{
					attr.applyModifier(mod)
				}
			}
			else
			{
				if(attr.hasModifier(mod))
				{
					attr.removeModifier(mod)
				}
			}
		}
	}

	@SubscribeEvent
	def onPlayerRespawn(event: PlayerRespawnEvent) =
	{
		val player = event.player
		if(Conf.enableNutrientsLost) loseNutrients(player)
		randomizePosition(player, Conf.randomSpawnRadius)
		applyDeathSickness(player, Conf.deathSicknessDuration)

	}

	def loseNutrients(player: EntityPlayer)
	{
		player.getFoodStats match {
			case f: FoodStatsTFC =>
				for(_ <- 0 until ((4 - Conf.nutritionAfterDeath) * (ConfigTFC.General.PLAYER.nutritionRotationHungerWindow / 10F)).toInt)
				{
					f.getNutrition.addNutrients(new FoodData(4, 4, 4, 0, 0, 0, 0, 0, 1.0F))
					f.getNutrition.addBuff(new FoodData(4, 4, 4, 0, 0, 0, 0, 0, 1.0F))
				}
				f.setFoodLevel(10)
				f.setThirst(100)
				f.setSaturation(10)
				player.setHealth(5)
			case _ =>
		}
	}

	def applyDeathSickness(player: EntityPlayer, duration: Int)
	{
		if(duration > 10)
		{
			player.quote("awful")
		}
		player.addPotionEffect(new PotionEffect(DeathSicknessEffect, duration))
	}

	def randomizePosition(player: EntityPlayer, radius: Int): Unit =
	{
		if(playersWithAcuity.contains(player.getName))
		{
			playersWithAcuity -= player.getName
		}
		else
		{
			if(Conf.randomSpawnRadius < 20) return

			player.quote("location")
			val basePos = Option(player.getBedLocation()).getOrElse(player.world.getSpawnPoint)
			var moved = true
			while(moved)
			{
				val ranX = basePos.getX + Math.random() * radius.toDouble * 2 - radius.toDouble
				val ranZ = basePos.getZ + Math.random() * radius.toDouble * 2 - radius.toDouble
				if(!Seq(BiomesTFC.OCEAN, BiomesTFC.DEEP_OCEAN).contains(player.world.getBiome(new BlockPos(ranX, 100, ranZ))))
				{
					val ranY = getSurfacePos(player.world, ranX.toInt, ranZ.toInt)
					player.setPositionAndUpdate(ranX, ranY, ranZ)
					moved = false
				}
			}
		}
	}

	@SubscribeEvent
	def onPlayerDeath(event: LivingDeathEvent) =
	{
		event.getEntityLiving match
		{
			case p: EntityPlayer =>
				if(p.isPotionActive(AcuityEffect)) playersWithAcuity += p.getName
				clearInventory(p)
			case _ =>
		}
	}

	def clearInventory(player: EntityPlayer): Unit =
	{
		if(!Conf.enableItemLoss) return
		if(player.isPotionActive(AcuityEffect))
		{
		}
		else
		{
			player.inventory.clear()
		}

	}
}