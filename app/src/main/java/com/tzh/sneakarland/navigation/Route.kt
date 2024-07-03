package com.tzh.sneakarland.navigation

import com.tzh.sneakarland.model.SneakerModel
import kotlinx.serialization.Serializable


@Serializable
data object HomeRoute


@Serializable
data class DetailRoute(
    val image: Int, val name: String, val description: String, val price: String, val tModel: String
)

@Serializable
data class ArViewRoute(
    val tModel: String
)