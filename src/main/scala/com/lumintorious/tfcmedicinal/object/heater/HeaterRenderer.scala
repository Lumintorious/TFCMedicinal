package com.lumintorious.tfcmedicinal.`object`.heater

import net.minecraft.block.BlockHorizontal
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.ItemCameraTransforms
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.client.renderer.{GlStateManager => GL}
import net.minecraftforge.fml.relauncher._

import scala.util.Try

@SideOnly(Side.CLIENT)
object HeaterRenderer extends TileEntitySpecialRenderer[HeaterTile]
{
	override def render(
		te: HeaterTile,
		x: Double,
		y: Double,
		z: Double,
		partialTicks: Float,
		destroyStage: Int,
		alpha: Float
	): Unit =
	{
		val world = Option(te.getWorld).getOrElse(return)
		val state = Option(world.getBlockState(te.getPos)).getOrElse(return)
		var angle = Try(state.getValue(BlockHorizontal.FACING).getHorizontalAngle).getOrElse(0F)
		angle = angle match {
			case 90F => 270F
			case 270F => 90F
			case _ => angle
		}

		val renderItem = Minecraft.getMinecraft.getRenderItem
		GL.pushMatrix()
			GL.translate(x + 0.5, y + 0.68, z + 0.5)
			GL.pushMatrix()
			GL.pushAttrib()
				GL.pushMatrix()
					GL.scale(0.7, 0.7, 0.7)
					GL.rotate(angle, 0, 1, 0)
					renderItem.renderItem(te.vialStack, ItemCameraTransforms.TransformType.FIXED)
				GL.popMatrix()
			GL.popMatrix()
			GL.popAttrib()
		GL.popMatrix()
	}
}