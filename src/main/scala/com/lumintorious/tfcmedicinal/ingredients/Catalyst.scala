package com.lumintorious.tfcmedicinal
package ingredients

import java.util

import com.lumintorious.tfcmedicinal.fluids._
import com.lumintorious.tfcmedicinal.gui.MedicinalCreativeTab
import net.dries007.tfc.objects.items.ceramics.ItemJug
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.util._
import net.minecraft.world.World
import net.minecraftforge.fluids._
import net.minecraftforge.fluids.capability._
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher._
import net.minecraftforge.oredict.OreIngredient

import scala.collection.mutable.ArrayBuffer
import scala.util.Try

object Catalyst extends ItemJug
{
	setRegistryName("catalyst")
	setTranslationKey(getRegistryName.toString)
	setCreativeTab(MedicinalCreativeTab)

	def Stack(fluid: Fluid): ItemStack =
	{
		val stack = new ItemStack(this, 1)
		stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null) match
		{
			case fh: IFluidHandlerItem => fh.fill(new FluidStack(fluid, 100), true)
			case _ =>
		}
		stack
	}

	def registerRecipe(output: Fluid, concentrates: String*) =
	{
		def getIngredient(string: String): Iterable[OreIngredient] =
		{
			try
			{
				val num = Try((string.charAt(0) + "").toInt).getOrElse(1)
				val rest = string.substring(1)
				val seq = new ArrayBuffer[OreIngredient]
				for(i <- 0 until num)
				{
					seq += new OreIngredient("concentrate" + rest.capitalize)
				}
				seq
			}
			catch
			{
				case e: Exception =>
					println("Error parsing string")
					Seq()
			}
		}
		val seq = concentrates.flatMap(getIngredient)
//		println(seq)
//		System.exit(0)
		GameRegistry.addShapelessRecipe(
			new ResourceLocation("catalyzed_" + output.getName),
			null,
			Stack(output),
			seq:_*
		)
	}

	override def getSubItems(tab: CreativeTabs, items: NonNullList[ItemStack]
	) =
	{
		if(tab == getCreativeTab)
		{
			MedicinalFluid.all.filter(_ != BaseConcoction).foreach(fluid => items.add(Stack(fluid)))
		}
	}

	override def getItemStackDisplayName(stack: ItemStack): String =
	{
		val bucketCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)
		if(bucketCap != null)
		{
			val fluidStack = bucketCap.drain(100, false)
			if(fluidStack != null)
			{
				val fName = fluidStack.getLocalizedName
				return "item.tfcmedicinal.catalyst_full.name".localize(fName)
			}
		}
		"item.tfcmedicinal.catalyst.name".localize()
	}

	@SideOnly(Side.CLIENT)
	override def addInformation(stack: ItemStack, worldIn: World,
		tooltip: util.List[String], flagIn: ITooltipFlag
	) =
	{
		super.addInformation(stack, worldIn, tooltip, flagIn)
		val cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)
		Cast(cap).run[IFluidHandlerItem]
		{ fhi =>
			Option(fhi.drain(100, false)).foreach
			{ fs =>
				Cast(fs.getFluid).run[MedicinalFluid]
				{ mf =>
					tooltip.add(s"fluid.${mf.getName}.description".localize())
					tooltip.add("tooltip.boil_catalyst.description".localize())
				}
			}
		}
	}

	override def onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand) =
	{
		new ActionResult(EnumActionResult.FAIL, player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND))
	}
}