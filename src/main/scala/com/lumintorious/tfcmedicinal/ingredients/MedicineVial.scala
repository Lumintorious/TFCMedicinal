package com.lumintorious.tfcmedicinal
package ingredients

import java.util

import com.lumintorious.tfcmedicinal.fluids.MedicinalFluid
import com.lumintorious.tfcmedicinal.gui.MedicinalCreativeTab
import net.dries007.tfc.objects.fluids.FluidsTFC
import net.dries007.tfc.objects.items.ceramics.ItemJug
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack
import net.minecraft.util._
import net.minecraft.world.World
import net.minecraftforge.fluids.capability.{CapabilityFluidHandler, _}
import net.minecraftforge.fluids.{FluidStack, _}
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.relauncher._

@Mod.EventBusSubscriber
object MedicineVial extends ItemJug
{
	setRegistryName("medicine_vial")
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

	override def getSubItems(tab: CreativeTabs, items: NonNullList[ItemStack]) =
	{
		if(isInCreativeTab(tab))
		{
			items.add(new ItemStack(this))
			for(fluid <- MedicinalFluid.all)
			{
				items.add(Stack(fluid))
			}
			items.add(Stack(FluidsTFC.SALT_WATER.get()))
		}
	}

	override def getMaxItemUseDuration(stack: ItemStack) = 32

	override def getItemStackDisplayName(stack: ItemStack): String =
	{
		val bucketCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)
		if(bucketCap != null)
		{
			val fluidStack = bucketCap.drain(100, false)
			if(fluidStack != null)
			{
				val fName = fluidStack.getLocalizedName
				return "item.tfcmedicinal.vial_full.name".localize(fName)
			}
		}
		"item.tfcmedicinal.vial.name".localize()
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
				}
			}
		}
	}
}