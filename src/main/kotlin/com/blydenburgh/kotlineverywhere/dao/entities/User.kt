package com.blydenburgh.kotlineverywhere.dao.entities

data class User(
    val id: Long = (1000*Math.random()).toLong(),
    val username: String,
    val age: Int,
    val gender: Gender
)

enum class Gender {
    MALE, FEMALE, OTHER;

    override fun toString(): String = when (this) {
        MALE -> "M"
        FEMALE -> "F"
        else -> "O"
    }
}

fun Char.toGender() = when (this) {
    'M' -> Gender.MALE
    'F' -> Gender.FEMALE
    else -> Gender.OTHER
}