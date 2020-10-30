package com.lumintorious.tfcmedicinal.`object`.mpestle

import com.lumintorious.tfcmedicinal.`object`.mpestle.MPestleItem.Stack
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.{Container, EntityEquipmentSlot, Slot}
import net.minecraft.item.ItemStack

case class MPestleContainer(player: EntityPlayer) extends Container
{
	val stack: Stack = player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND)

	val startX = 8
	var startY = 30
	val size   = 18
	// Main Player Inventory
	for(row <- 0 until 3) {
		for(column <- 0 until 9) {
			this.addSlotToContainer(
				new Slot(
					player.inventory,
					9 + (row * 9) + column,
					startX + (column * size),
					startY + (row * size)
				))
		}
	}

	// Hotbar
	val startYHotbar = startY + size * 3 + 4
	for(column <- 0 until 9) {
		if(player.inventory.currentItem == column)
		{
			this.addSlotToContainer(
				new Slot(
					player.inventory,
					column,
					startX + (column * size),
					startYHotbar
				)
				{
					override def canTakeStack(playerIn: EntityPlayer
					) = false
				}
			)

		}
		else
		{
			this.addSlotToContainer(
				new Slot(
					player.inventory,
					column,
					startX + (column * size),
					startYHotbar
				))
		}

	}

	var inputSlot: Slot = _
	var outputSlot: Slot = _
	MPestleCapability.on(stack)
	{ cap =>
		inputSlot = this.addSlotToContainer(new Slot(cap.inventory, 0, 44, 7)
		{
			override def isItemValid(stack: Stack) = stack.getItem != MPestleItem
		})

		outputSlot = this.addSlotToContainer(new Slot(cap.inventory, 1, 116, 7)
		{
			override def isItemValid(stack: Stack) = false
		})
	}

	override def transferStackInSlot(
		playerIn: EntityPlayer,
		index: Int
	): ItemStack =
	{
//		val slot = getSlot(index)
//		val stack = getSlot(index).getStack
//		if(slot == inputSlot || slot == outputSlot)
//		{
//			val did = playerIn.inventory.addItemStackToInventory(stack)
//			if(!did)
//			{
//				slot.putStack(stack)
//			}
//			ItemStack.EMPTY
//		}
//		else
//		{
//			if(!inputSlot.getStack.isEmpty)
//			{
//				return ItemStack.EMPTY
//			}
//			inputSlot.putStack(stack.copy())
//			slot.putStack(ItemStack.EMPTY)
//			stack
//		}
		ItemStack.EMPTY
	}

	override def canInteractWith(playerIn: EntityPlayer) = true
}
