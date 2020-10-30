package com.lumintorious.tfcmedicinal.`object`.mpestle

import com.lumintorious.tfcmedicinal.TFCMedicinal
import com.lumintorious.tfcmedicinal.gui.MedicinalCreativeTab
import net.dries007.tfc.api.capability.size._
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item._
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util._
import net.minecraft.world.World
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher._

@Mod.EventBusSubscriber
object MPestleItem extends Item with IItemSize
{
	setRegistryName("mpestle")
	setTranslationKey(getRegistryName.toString)
	setCreativeTab(MedicinalCreativeTab)

	type Stack = ItemStack
	override def getSize(itemStack: ItemStack) = Size.SMALL
	override def getWeight(itemStack: ItemStack) = Weight.VERY_HEAVY

	override def getStackSize(stack: Stack) = 1

	override def canStack(stack: Stack) = false

	override def initCapabilities(stack: ItemStack, nbt: NBTTagCompound
	) = MPestleCapability(stack, nbt)

	override def onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand
	) =
	{
		val slot = if(handIn == EnumHand.MAIN_HAND) EntityEquipmentSlot.MAINHAND else EntityEquipmentSlot.OFFHAND
		val stack = playerIn.getItemStackFromSlot(slot)
		var res = new ActionResult(EnumActionResult.FAIL, stack)
		if(playerIn.isSneaking)
		{
			playerIn.openGui(TFCMedicinal, 0, worldIn, playerIn.posX.toInt, playerIn.posX.toInt, playerIn.posZ.toInt)
		}else
		{
			MPestleCapability.on(stack)
			{ cap =>
				if(cap.soundCd <= 0)
				{
					cap.soundCd = 0
					res = new ActionResult[Stack](EnumActionResult.SUCCESS, stack)
					worldIn.playSound(null, playerIn.getPosition, SoundEvents.BLOCK_STONE_STEP, SoundCategory.BLOCKS, 1F, 1F)
					cap.work()
				}else{
					cap.soundCd -= 1
				}
			}

		}
		res
	}

	override def getMaxItemUseDuration(stack: ItemStack) = 32

	@SideOnly(Side.CLIENT)
	override def isFull3D = true

	override def getItemUseAction(stack: ItemStack) = EnumAction.DRINK

	@SubscribeEvent
	def register(event: RegistryEvent.Register[Item])
	{
		event.getRegistry.register(this)
	}
}