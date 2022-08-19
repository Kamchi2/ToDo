package com.example.todo

import java.io.Serializable

data class CreateDataModel(
    val task: String,
    val date: String,
    val regular: String
): Serializable
