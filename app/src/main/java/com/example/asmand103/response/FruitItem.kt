package com.example.asmand103.response

import java.io.Serializable

data class FruitItem(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val type :String = "",
    val description: String,
    val id_distributor: IdDistributor,
    val images: List<String>,
    val name: String,
    val price: Int,
    val quatity: Int,
    val status: Int,
    val updatedAt: String
) :Serializable