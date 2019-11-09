package com.blydenburgh.kotlineverywhere.dao.entities

data class Post (
    val id: Long = (1000*Math.random()).toLong(),
    val body: String,
    val author: String,
    val publishDate: String
)

