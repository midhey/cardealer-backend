package ru.ladamarket.models.carModels

data class Equipment(
    val equipmentId: Int,
    val name: String,
    val modelId: Int,
    val bodyId: Int,
    val transmissionId: Int,
    val engineId: Int,
    val cost: Double
)

//data class Equipment(
//    val name: String,
//    val modelId: Int,
//    val modelName: String,
//    val generation: String,
//    val country: String,
//    val wheel: String,
//    val bodyId: EntityID<Int>,
//    val bodyName: String,
//    val length: Short,
//    val width: Short,
//    val height: Short,
//    val wheelbase: Short,
//    val clearance: Short,
//    val seatsCount: Short,
//    val trunkVolume: Short,
//    val tankVolume: Short,
//    val weight: Short,
//    val maxWeight: Short,
//    val transmissionId: EntityID<Int>,
//    val transmissionType: String,
//    val drive: String,
//    val transmissionCount: Short,
//    val engineId: Short,
//    val engineType: String,
//    val cylindersCount: Short,
//    val valveCount: Short,
//    val displacement: Short,
//    val fuelType: String,
//    val power: Short,
//    val torque: Short,
//    val ecoClass: String
//)
