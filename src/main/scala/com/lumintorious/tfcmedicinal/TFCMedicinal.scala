package com.lumintorious.tfcmedicinal

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event._
import net.minecraftforge.fml.relauncher.Side

@Mod(
	modid = TFCMedicinal.MODID,
	name = TFCMedicinal.NAME,
	version = TFCMedicinal.VERSION,
	modLanguage = "scala",
	useMetadata = true,
	dependencies = "required-after:tfc"
)
object TFCMedicinal
{
	final val MODID = "tfcmedicinal"
	final val NAME = "TFC Medicinal"
	final val VERSION = "1.0.0"

	@EventHandler
	def init(event: FMLInitializationEvent) =
	{
		CommonRegistrar.init()
		Side.CLIENT(ClientRegistrar.init())
	}

	@EventHandler
	def postInit(event: FMLPostInitializationEvent) =
	{
		CommonRegistrar.postInit()
	}
}

//The world is a hard place to live in, more than just hunger and thirst, danger lurks everywhere and you should be afraid because death is horrible.
//
//Here are some of the things you will have to look out for when surviving in TFC with Medicinal, all of which will have specific medicine to fight it:
//
//When you die, your health will not be restored to full, but to half
//When you die, your nutrition stats won't be at half, but close to 0,
//After you die, you will not spawn in your comfy bed, but randomly scattered around it in a radius of 1000 blocks (configurable)
//After you respawn, a sickness will befall you, death sickness, preventing you from mining, building or fighting for an entire Minecraft day
//The dust in mines will stress your lungs, making it harder to move and mine.
//Uncooked meat as well as cooked meat that isn't wood grilled (on an iron grill) thoroughly can give you parasites, sapping your energy and hunger.
//
//
//And now you are probably wondering why on earth would I want this addon then? Well for the challenge in avoiding or treating these ailments.
//
//Flowers and plants in the world now have active properties:
//
//Bright and red flowers have lively and healing properties
//Blue flowers have mentally aiding properties
//Yellow flowers have energising properties
//Green plants (not grass) have soothing properties
//Brown plants are rich in vitamins and minerals
//Purple and pink flowers have strong fragrances
//White flowers have powerful mixing capabilities, being used in all medicines.
//But how do you make medicine from this?
//
//Get yourself a mortar and pestle by crafting a ceramic bowl with a stick and flint.
//Collect a nice variety of flowers, go exploring the new world if you have to and bring them home.
//Crush the flowers in with the mortar and pestle by shift clicking it, putting the flower in and then right clicking it repeatedly.
//The flowers will give you extracts that when crafted in a 3x3 will give you concentrates.
//There are numerous catalysts to choose from that can be infused into a base concoction to be drunk.
//Catalysts have different crafting recipes formed from different kinds of concentrates, use JEI for that.
//Once you have the catalyst you want, you will need a Vial Heater that is crafted with some fire bricks and an alabaster filter.
//Put the heater above a burning fire pit on the wall.
//Craft yourself a glass vial with one piece of glass in the crafting table.
//Find yourself some spiders underground and take their eyes and ferment them in some vinegar for a day.
//Fill your vial with salt water from the ocean, put it in the vial heater and wait for it to boil (bubbles will form above it)
//When the solution is boiling, add your fermented spider eye and at that point, the solution will start boiling more and more.
//After one in-game hour your base concoction will be done.
//Put any catalyst into it and boil it like the last few steps to obtain a drinkable concoction for you to use