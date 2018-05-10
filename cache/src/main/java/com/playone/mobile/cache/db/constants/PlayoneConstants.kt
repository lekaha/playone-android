package com.playone.mobile.cache.db.constants

import com.playone.mobile.cache.db.Db

/**
 * Defines DB queries for the Playone Table
 */
object PlayoneConstants {

    internal val QUERY_GET_ALL_PLAYONE = "SELECT * FROM " + Db.PlayoneTable.TABLE_NAME

}