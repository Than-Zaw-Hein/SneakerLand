package com.tzh.sneakarland.model

import com.tzh.sneakarland.R
import kotlinx.serialization.Serializable


@Serializable
data class SneakerModel(
    val name: String,
    val description: String,
    val image: Int,
    val price: String = "$120",
    val tModel: String
)

val dummySneakerList = listOf(
    SneakerModel(
        "New Balance 997",
        "Stylish and comfortable sneaker featuring a mix of suede and mesh upper for durability and breathability.",
        R.drawable.new_balance_997,
        tModel = "models/new_balance_997.glb"
    ),
    SneakerModel(
        "Vans Old Skool Black",
        "Classic skate shoe with the iconic side stripe. Made with durable canvas and suede uppers.",
        R.drawable.vans_old_skool_black,
        tModel = "models/vans_old_skool_black.glb"
    ),
    SneakerModel(
        "Vans Old Skool Blue",
        "Two-tone blue and black design with the signature side stripe. Constructed with durable canvas and suede uppers.",
        R.drawable.vans_old_skool_blue,
        tModel = "models/vans_old_skool_blue.glb"
    ),
    SneakerModel(
        "Adidas X 18.1 Soccer Cleats",
        "Designed for speed and agility on the field. Lightweight, thin mesh upper, low-cut Clawcollar, and arrowhead forefoot studs.",
        R.drawable.adidas_x_18_1,
        tModel = "models/adidas_x_18_1.glb"
    ),
    SneakerModel(
        "Brown Leather High-Top Sneakers",
        "Casual style and comfort. Made from premium leather, cushioned insole, lace-up closure, and durable rubber outsole.",
        R.drawable.brown_leather_high_top,
        tModel = "models/brown_leather_high_top.glb"
    )
)