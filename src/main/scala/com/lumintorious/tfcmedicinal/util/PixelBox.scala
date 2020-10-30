package com.lumintorious.tfcmedicinal.util

import net.minecraft.util.math.AxisAlignedBB

object PixelBox
{
	val inc: Double = 0.0625D
	@inline def apply(
		x1: Int,
		y1: Int,
		z1: Int,
		x2: Int,
		y2: Int,
		z2: Int
	): AxisAlignedBB =
	{
		new AxisAlignedBB(inc * x1, inc * y1, inc * z1, inc * x2, inc * y2, inc * z2)
	}
}
