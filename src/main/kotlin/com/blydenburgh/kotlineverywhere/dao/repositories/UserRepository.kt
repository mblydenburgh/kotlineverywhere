package com.blydenburgh.kotlineverywhere.dao.repositories

import com.blydenburgh.kotlineverywhere.dao.entities.User
import com.blydenburgh.kotlineverywhere.dao.entities.toGender
import com.blydenburgh.kotlineverywhere.dao.exposed.Users
import com.blydenburgh.kotlineverywhere.dao.exposed.transactionEnviroment
import org.jetbrains.exposed.sql.*

object UserRepository : Repository<User> {

    override fun add(element: User): String = transactionEnviroment {
        Users.insert {
            it[id] = element.id.toInt()
            it[username] = element.username
            it[age] = element.age
            it[gender] = element.gender.toString().first()
        }.toString()
    }

    override fun getAll(): Sequence<User> = transactionEnviroment {
        Users.selectAll().toList().map {
            User(
                id = it[Users.id].toLong(),
                username = it[Users.username].toString(),
                age = it[Users.age],
                gender = it[Users.gender].toGender()
            )
        }.asSequence()
    }

    override fun filter(predicate: SqlExpressionBuilder.() -> Op<Boolean>): Sequence<User> = transactionEnviroment {
        Users.select(predicate).map {
            User(
                id = it[Users.id].toLong(),
                username = it[Users.username].toString(),
                age = it[Users.age],
                gender = it[Users.gender].toGender()
            )
        }.asSequence()
    }

    override fun remove(indexer: Long): Long = transactionEnviroment {
        Users.deleteWhere { Users.id eq indexer.toInt() }
    }.toLong()

    override fun replace(indexer: Long, element: User): Long = transactionEnviroment {
        Users.update({ Users.id eq indexer.toInt() }) {
            it[username] = element.username
            it[age] = element.age
            it[gender] = element.gender.toString().first()
        }.toLong()
    }

    override fun getElement(indexer: Long): User = transactionEnviroment {
        Users.select { Users.id eq indexer.toInt() }
            .map {
                User(
                    id = it[Users.id].toLong(),
                    username = it[Users.username].toString(),
                    age = it[Users.age],
                    gender = it[Users.gender].toGender()
                )
            }
            .first()
    }
}