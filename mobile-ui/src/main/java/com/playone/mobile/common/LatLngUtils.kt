package com.playone.mobile.common

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.SphericalUtil

object LatLngUtils {

    fun createBoundsWithRectangle(pointLL1: LatLng, searchRadiusMeters: Double) =
        LatLngBounds.Builder().
            include(SphericalUtil.computeOffset(pointLL1, searchRadiusMeters, 0.0)).
            include(SphericalUtil.computeOffset(pointLL1, searchRadiusMeters, 90.0)).
            include(SphericalUtil.computeOffset(pointLL1, searchRadiusMeters, 180.0)).
            include(SphericalUtil.computeOffset(pointLL1, searchRadiusMeters, 270.0))
            .build()

    fun createBoundsWithMinDiagonal(
        pointLL1: LatLng,
        pointLL2: LatLng
    ): LatLngBounds {
        val builder = LatLngBounds.Builder()
        builder.include(pointLL1)
        builder.include(pointLL2)

        val tmpBounds = builder.build()
        /** Add 2 points 1000m northEast and southWest of the center.
         * They increase the bounds only, if they are not already larger
         * than this.
         * 1000m on the diagonal translates into about 709m to each direction.  */
        val center = tmpBounds.center
        val northEast = move(center, 709.0, 709.0)
        val southWest = move(center, -709.0, -709.0)
        builder.include(southWest)
        builder.include(northEast)
        return builder.build()
    }

    private const val EARTHRADIUS = 6366198.0
    /**
     * Create a new LatLng which lies toNorth meters north and toEast meters
     * east of startLL
     */
    private fun move(startLL: LatLng, toNorth: Double, toEast: Double): LatLng {
        val lonDiff = meterToLongitude(toEast, startLL.latitude)
        val latDiff = meterToLatitude(toNorth)
        return LatLng(startLL.latitude + latDiff, startLL.longitude + lonDiff)
    }

    private fun meterToLongitude(meterToEast: Double, latitude: Double): Double {
        val latArc = Math.toRadians(latitude)
        val radius = Math.cos(latArc) * EARTHRADIUS
        val rad = meterToEast / radius
        return Math.toDegrees(rad)
    }

    private fun meterToLatitude(meterToNorth: Double): Double {
        val rad = meterToNorth / EARTHRADIUS
        return Math.toDegrees(rad)
    }
}