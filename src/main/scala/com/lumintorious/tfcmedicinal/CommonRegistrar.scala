package com.lumintorious.tfcmedicinal

import com.lumintorious.tfcmedicinal.`object`.heater._
import com.lumintorious.tfcmedicinal.`object`.mpestle.MPestleItem
import com.lumintorious.tfcmedicinal.compat.tfcambiental.TFCAmbientalPlugin
import com.lumintorious.tfcmedicinal.effects._
import com.lumintorious.tfcmedicinal.fluids._
import com.lumintorious.tfcmedicinal.ingredients._
import com.lumintorious.tfcmedicinal.util.OreHelper
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe
import net.dries007.tfc.api.recipes.quern.QuernRecipe
import net.dries007.tfc.api.registries.TFCRegistries
import net.dries007.tfc.objects.fluids.FluidsTFC
import net.dries007.tfc.objects.inventory.ingredient.IIngredient
import net.dries007.tfc.types.DefaultPlants
import net.minecraft.block.Block
import net.minecraft.entity.monster.EntitySpider
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item._
import net.minecraft.potion.Potion
import net.minecraft.tileentity.TileEntity
import net.minecraft.util._
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.entity.living._
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.oredict._

@Mod.EventBusSubscriber
object CommonRegistrar
{
	def init(): Unit =
	{
		TFCAmbientalPlugin.init()
		CommonRegistrar.registerTileEntities()
		CommonRegistrar.registerFluids()
	}

	def postInit(): Unit =
	{
		CommonRegistrar.registerOreDictionaries()
		CommonRegistrar.registerCatalystRecipes()
	}

	@SubscribeEvent
	def registerItems(event: RegistryEvent.Register[Item]) =
	{
		val reg = event.getRegistry

		reg register HeaterBlock.itemBlock
		reg register MPestleItem
		reg register MedicineVial
		reg register Catalyst

		ItemTFCM.all.foreach { item => reg register item }
	}

	@SubscribeEvent
	def registerBlocks(event: RegistryEvent.Register[Block]) =
	{
		event.getRegistry.registerAll(
			HeaterBlock
		)
	}

	def registerFluids() =
	{
		Seq(
			AcuityConcoction,
			BaseConcoction,
			DeathSicknessAntidote,
			NourishingConcoction,
			RejuvenatingConcoction,
			StomachStrengthConcoction,
			LungStrengthConcoction,
			KeenSightConcoction
		).foreach(FluidRegistry.registerFluid)
	}

	@SubscribeEvent
	def registerPotions(event: RegistryEvent.Register[Potion]) =
	{
		event.getRegistry.registerAll(
			AcuityEffect,
			DeathSicknessEffect,
			StrongStomachEffect,
			StrongLungsEffect
		)
	}

	def registerTileEntities() =
	{
		TileEntity.register("vial_heater", classOf[HeaterTile])
	}

	def registerOreDictionaries() =
	{
		import DefaultPlants._
		OreHelper("pureExtractHolder").addPlants(
			OXEYE_DAISY,
			HOUSTONIA,
			PRIMROSE,
			TRILLIUM,
			SNAPDRAGON_WHITE,
			TULIP_WHITE
		)

		OreHelper("focusedExtractHolder").addPlants(
			BLUE_ORCHID,
			GRAPE_HYACINTH,
			SAPPHIRE_TOWER,
			LABRADOR_TEA
		)

		OreHelper("fragrantExtractHolder").addPlants(
			BLACK_ORCHID,
			ALLIUM,
			PULSATILLA,
			FOXGLOVE,
			PEROVSKIA,
			SNAPDRAGON_PINK,
			TULIP_PINK,
			MORNING_GLORY
		)

		OreHelper("livelyExtractHolder").addPlants(
			VRIESEA,
			GUZMANIA,
			TROPICAL_MILKWEED,
			STRELITZIA,
			WATER_CANNA,
			CANNA,
			BUTTERFLY_MILKWEED,
			SNAPDRAGON_RED,
			TULIP_RED
		)

		OreHelper("energeticExtractHolder").addPlants(
			CALENDULA,
			MEADS_MILKWEED,
			DANDELION,
			GOLDENROD,
			NASTURTIUM,
			SNAPDRAGON_YELLOW
		)

		OreHelper("earthyExtractHolder").addPlants(
			SARGASSUM,
			ROUGH_HORSETAIL,
			PORCINI
		)

		OreHelper("herbalExtractHolder").addPlants(
			MOSS,
			REINDEER_LICHEN,
			BARREL_CACTUS
		)
	}

	@SubscribeEvent
	def onEntityDrops(event: LivingDropsEvent) =
	{
		if(event.getEntityLiving.isInstanceOf[EntitySpider])
		{
			event.getEntityLiving.dropItem(Items.SPIDER_EYE, Conf.spiderEyeNumber - 1)
		}
	}

	@SubscribeEvent
	def onPlayerAttack(event: LivingDamageEvent) =
	{
		Cast(event.getSource).run[EntityDamageSource]
		{ source =>
			Cast(source.getTrueSource).run[EntityPlayer]
			{ player =>
				 if(player.isPotionActive(DeathSicknessEffect))
				 {
					 event.setCanceled(true)
				 }
			}
		}
	}

	@SubscribeEvent
	def registerBarrelRecipes(event: RegistryEvent.Register[BarrelRecipe]) =
	{
		val fermentSpiderEye = new BarrelRecipe(
			IIngredient.of(FluidsTFC.VINEGAR.get(), 250),
			IIngredient.of(Items.SPIDER_EYE),
			null,
			new ItemStack(Items.FERMENTED_SPIDER_EYE),
			72000
		).setRegistryName("fermented_spider_eye")
		event.getRegistry.register(fermentSpiderEye)
	}

	@SubscribeEvent
	def afterQuernRegistries(event: RegistryEvent.Register[QuernRecipe])
	{
		import collection.JavaConversions.iterableAsScalaIterable
		for(recipe <- TFCRegistries.QUERN)
		{
			println(recipe.getOutputs.get(0).getDisplayName)
		}
	}

	def registerCatalystRecipes() =
	{
		Catalyst.registerRecipe(NourishingConcoction, "2herbal", "2earthy", "1lively", "1pure")
		Catalyst.registerRecipe(AcuityConcoction, "4focused", "2energetic", "1lively", "1pure")
		Catalyst.registerRecipe(RejuvenatingConcoction, "3lively", "2energetic", "1earthy", "1fragrant", "1pure")
		Catalyst.registerRecipe(DeathSicknessAntidote, "2energetic", "3lively", "2earthy", "1fragrant", "1pure")
		Catalyst.registerRecipe(StomachStrengthConcoction, "3herbal", "1earthy", "1pure")
		Catalyst.registerRecipe(LungStrengthConcoction, "2fragrant", "2energetic", "1pure")
		Catalyst.registerRecipe(KeenSightConcoction, "2focused", "2fragrant", "1pure")
		Catalyst.registerRecipe(HealthBoostConcoction, "4lively", "1energetic", "3earthy", "1pure")

//		GameRegistry.addShapelessRecipe(
//			new ResourceLocation("medicine_vial"),
//			null,
//			new ItemStack(MedicineVial),
//			new OreIngredient("blockGlass")
//		)
		val inputs = Seq[Object](
			"G G",
			"G G",
			" G ",
			new Character('G'), new OreIngredient("blockGlass")
		)
		GameRegistry.addShapedRecipe(
			new ResourceLocation("medicine_vial"),
			null,
			new ItemStack(MedicineVial),
			inputs:_*
		)
	}
}
