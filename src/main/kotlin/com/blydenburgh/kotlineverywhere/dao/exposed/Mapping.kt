package com.blydenburgh.kotlineverywhere.dao.exposed

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table() {
    val id = integer("id").uniqueIndex().primaryKey()
    val username = varchar("username", 255)
    val age = integer("age")
    val gender = char("gender")
}

object Posts : Table() {
    val id = integer("id").primaryKey()
    val body = varchar("body", 255)
    val author = varchar("author", 255).references(Users.username)
    val publishDate = date("publishDate")
}

fun <T> transactionEnviroment(closure: () -> T): T {
    Database.connect(url = "jdbc:mysql://localhost:3306/kotlineverywhere", user = "root", password = "UX303lb#", driver = "com.mysql.cj.jdbc.Driver")
    return transaction {
        SchemaUtils.create(Users,Posts)
        closure()
    }
}