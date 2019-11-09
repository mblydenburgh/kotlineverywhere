package com.blydenburgh.kotlineverywhere.dao.repositories

import com.blydenburgh.kotlineverywhere.dao.entities.Post
import com.blydenburgh.kotlineverywhere.dao.exposed.Posts
import com.blydenburgh.kotlineverywhere.dao.exposed.transactionEnviroment
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime

object PostRepository : Repository<Post> {

    override fun add(element: Post): String = transactionEnviroment {
        Posts.insert {
            it[body] = element.body
            it[author] = element.author
            it[publishDate] = DateTime.now()
        }.toString()
    }

    override fun getAll(): Sequence<Post> = transactionEnviroment {
        Posts.selectAll().toList().map {
            Post(
                id = it[Posts.id].toLong(),
                body = it[Posts.body],
                author = it[Posts.author],
                publishDate = it[Posts.publishDate].toString()
            )
        }.asSequence()
    }

    override fun filter(predicate: SqlExpressionBuilder.() -> Op<Boolean>): Sequence<Post> = transactionEnviroment {
        Posts.select(predicate).map {
            Post(
                id = it[Posts.id].toLong(),
                body = it[Posts.body],
                author = it[Posts.author],
                publishDate = it[Posts.publishDate].toString()
            )
        }.asSequence()
    }

    override fun remove(indexer: Long): Long = transactionEnviroment {
        Posts.deleteWhere { Posts.id eq indexer.toInt() }
    }.toLong()

    override fun replace(indexer: Long, element: Post): Long = transactionEnviroment {
        Posts.update({ Posts.id eq indexer.toInt() }) {
            it[body] = element.body
            it[publishDate] = DateTime(element.publishDate)
        }.toLong()
    }

    override fun getElement(indexer: Long): Post = transactionEnviroment {
        Posts.select { Posts.id eq indexer.toInt() }.map {
            Post(
                id = it[Posts.id].toLong(),
                body = it[Posts.body],
                author = it[Posts.author],
                publishDate = it[Posts.publishDate].toString()
            )
        }.first()
    }
}
