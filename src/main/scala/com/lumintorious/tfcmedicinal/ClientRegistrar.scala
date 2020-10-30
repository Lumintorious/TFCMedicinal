package com.lumintorious.tfcmedicinal

import com.lumintorious.tfcmedicinal.`object`.heater._
import com.lumintorious.tfcmedicinal.`object`.mpestle.MPestleItem
import com.lumintorious.tfcmedicinal.gui.MedicinalGuiHandler
import com.lumintorious.tfcmedicinal.ingredients._
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.relauncher._

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Array(Side.CLIENT))
object ClientRegistrar
{
	def init(): Unit =
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(TFCMedicinal, MedicinalGuiHandler)
		ClientRegistrar.registerTESRs()
	}

	@inline def addModel(item: Item, id: String = "inventory") =
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName, id))
	}

	@SubscribeEvent
	def registerItemModels(event: ModelRegistryEvent)
	{
		addModel(MedicineVial)
		addModel(Catalyst)
		addModel(MPestleItem)
		addModel(HeaterBlock.itemBlock, "normal")

		ItemTFCM.all.foreach(item => addModel(item))
	}

	def registerTESRs()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(classOf[HeaterTile], HeaterRenderer)
	}
}
