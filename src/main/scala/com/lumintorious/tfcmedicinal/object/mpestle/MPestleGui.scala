package com.lumintorious.tfcmedicinal.`object`.mpestle

import com.lumintorious.tfcmedicinal.TFCMedicinal
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation

import scala.util.Try

case class MPestleGui(c: MPestleContainer) extends GuiContainer(c)
{
	private val BACKGROUND = new ResourceLocation(TFCMedicinal.MODID, "textures/gui/crate.png")

	this.xSize = 176
	this.ySize = 112

	guiLeft = 0
	guiTop = 100

	override def drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) = {
		drawDefaultBackground()
		super.drawScreen(mouseX, mouseY, partialTicks)
		this.renderHoveredToolTip(mouseX, mouseY)
	}

	override def drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) =
	{
		this.drawDefaultBackground()
		GlStateManager.color(1, 1, 1, 1)
		mc.getTextureManager.bindTexture(BACKGROUND)
		Try(drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize))
	}
}
