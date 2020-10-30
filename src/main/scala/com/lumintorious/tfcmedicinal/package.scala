package com.lumintorious

import net.dries007.tfc.objects.fluids.properties.FluidWrapper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item._
import net.minecraft.util.text._
import net.minecraftforge.fluids.Fluid
import net.minecraftforge.fml.relauncher.Side

package object tfcmedicinal
{
	// Java implementation cause reflection bull****
	def Conf = TFCMedicinalConfig.General

	implicit def unwrapLiquid(wrapper: FluidWrapper): Fluid = wrapper.get()

	implicit class MetableItem(val item: Item)
	{
		@inline def withMeta(meta: Int): ItemStack =
		{
			new ItemStack(item, 1, meta)
		}
	}

	implicit class LocalString(val x: String) extends AnyVal
	{
		@inline def localize(args: Object*): String =
		{
			new TextComponentTranslation(x, args: _*).getFormattedText
		}
	}

	case class Cast(val x: Any)
	{
		@inline def map[T](fn: T => Any): Cast =
		{
			try
			{
				x match
				{
					case t: T => Cast(fn(t))
					case _ => Cast(null)
				}
			}
			catch
			{
				case e: ClassCastException => Cast(null)
			}
		}

		@inline def run[T](f: T => Unit): Unit =
		{
			try
			{
				x match
				{
					case t: T => f(t)
					case _ => ()
				}
			}
			catch
			{
				case e: ClassCastException => ()
			}
		}
	}

	implicit class EzPlayer(self: EntityPlayer)
	{
		def debug(message: String) =
		{
			self.sendMessage(new TextComponentString(message))
		}

		def quote(message: String) =
		{
			self.sendMessage(new TextComponentTranslation(s"quote.${message}"))
		}

		def status(message: String) =
		{
			self.sendStatusMessage(new TextComponentTranslation(s"quote.${message}"), true)
		}
	}

	implicit class SideAction(side: Side)
	{
		def apply(f: => Unit): Unit =
		{
			try
			{
				f
			}
			catch
			{
				case e: NoClassDefFoundError =>
			}
		}
	}
}