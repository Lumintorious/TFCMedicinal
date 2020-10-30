package com.lumintorious.tfcmedicinal.`object`.heater

import com.lumintorious.tfcmedicinal.gui.MedicinalCreativeTab
import com.lumintorious.tfcmedicinal.util.PixelBox
import net.dries007.tfc.api.capability.size._
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC
import net.minecraft.block.BlockHorizontal.FACING
import net.minecraft.block._
import net.minecraft.block.material.Material
import net.minecraft.block.state._
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item._
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{EnumFacing, _}
import net.minecraft.world.IBlockAccess
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber
object HeaterBlock extends Block(Material.ROCK) with IItemSize with ITileEntityProvider
{
	setRegistryName("vial_heater")
	setTranslationKey(getRegistryName.toString)
	setCreativeTab(MedicinalCreativeTab)

	private val boundingBoxes = Map(
		EnumFacing.SOUTH -> PixelBox(3, 2, 3, 13, 14, 16),
		EnumFacing.NORTH -> PixelBox(3, 2, 0, 13, 14, 13),
		EnumFacing.EAST -> PixelBox(3, 2, 3, 16, 14, 13),
		EnumFacing.WEST -> PixelBox(0, 2, 3, 13, 14, 13)
	)

	val itemBlock = new ItemBlockTFC(this).setRegistryName(getRegistryName)

	override def getStateFromMeta(meta: Int) =
		this
			.getDefaultState
			.withProperty(FACING, EnumFacing.byHorizontalIndex(meta))

	override def getMetaFromState(state: IBlockState) =
		state
			.getValue(FACING)
			.getHorizontalIndex

	override def getLightOpacity(state: IBlockState, world: IBlockAccess, pos: BlockPos
	) = 0

	override def createBlockState() = new BlockStateContainer(this, FACING)

	import net.minecraft.entity.EntityLivingBase
	import net.minecraft.util.EnumFacing
	import net.minecraft.util.math.BlockPos
	import net.minecraft.world.World

	override def getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos) = {
		boundingBoxes(state.getValue(FACING))
	}

	override def getStateForPlacement(
		worldIn: World,
		pos: BlockPos,
		facing: EnumFacing,
		hitX: Float,
		hitY: Float,
		hitZ: Float,
		meta: Int,
		placer: EntityLivingBase
	) = {
		var newFacing = facing
		if (facing.getAxis != EnumFacing.Axis.Y) {
			newFacing = placer.getHorizontalFacing
		}
		getDefaultState.withProperty(FACING, newFacing)
	}

	override def canPlaceBlockOnSide(worldIn: World, pos: BlockPos, side: EnumFacing
	) = side.getAxis != EnumFacing.Axis.Y

	override def onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState,
		playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float
	) =
	{
		worldIn.getTileEntity(pos) match
		{
			case te: HeaterTile => te.onClick(playerIn)
			case _ =>
		}
		true
	}

	override def getSize(itemStack: ItemStack) = Size.NORMAL
	override def getWeight(itemStack: ItemStack) = Weight.HEAVY
	override def isOpaqueCube(state: IBlockState) = false
	override def isFullCube(state: IBlockState) = false
	override def isFullBlock(state: IBlockState) = false
	override def isBlockNormalCube(state: IBlockState) = false
	override def isNormalCube(state: IBlockState) = false
	override def isSideSolid(
		base_state: IBlockState,
		world: IBlockAccess,
		pos: BlockPos,
		side: EnumFacing
	) = false

	override def hasTileEntity = true
	override def createNewTileEntity(worldIn: World, meta: Int) = new HeaterTile()
}
