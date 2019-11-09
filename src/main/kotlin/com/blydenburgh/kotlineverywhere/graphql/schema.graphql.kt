package com.blydenburgh.kotlineverywhere.graphql

import com.apurebase.kgraphql.KGraphQL
import com.blydenburgh.kotlineverywhere.dao.entities.*
import com.blydenburgh.kotlineverywhere.dao.exposed.Posts
import com.blydenburgh.kotlineverywhere.dao.exposed.Users
import com.blydenburgh.kotlineverywhere.dao.repositories.PostRepository
import com.blydenburgh.kotlineverywhere.dao.repositories.UserRepository
import org.joda.time.DateTime

val schema = KGraphQL.schema {
    query("allUsers") {
        resolver { ->
            UserRepository.getAll().toList()
        }
    }

    query("getUser") {
        resolver { id: Int, username: String? ->
            if (username != null) UserRepository.filter { Users.username eq username }.toList().firstOrNull()
            else UserRepository.filter { Users.id eq id }.toList().firstOrNull()
        }
    }

    mutation("createUser") {
        resolver { username: String, age: Int, gender: String ->
            val user = User(username = username, age = age, gender = gender[0].toGender())
            UserRepository.add(user)
        }
    }

    mutation("deleteUser") {
        println("in deleteUser")
        resolver { id: Long ->
            UserRepository.remove(id)
        }
    }

    mutation("updateUser") {
        println("in updateUser")
        resolver { id: Long, username: String, age: Int, gender: String ->
            val user = User(id = id, username = username, age = age, gender = gender[0].toGender())
            UserRepository.replace(id, user)
        }
    }

    //**POSTS**//
    query("allPosts") {
        println("in allPosts")
        resolver { ->
            PostRepository.getAll().toList()
        }
    }

    query("getPost") {
        println("in getPost")
        resolver { id: Int ->
            PostRepository.filter { Posts.id eq id }.toList().firstOrNull()
        }
    }

    mutation("createPost") {
        println("in createPost")
        resolver { username: String, body: String ->
            val post = Post(body = body, author = username, publishDate = DateTime.now().toString())
            println("CREATED POST $post")
            PostRepository.add(post)
        }
    }

    mutation("deletePost") {
        println("in deletePost")
        resolver { id: Long ->
            PostRepository.remove(id)
        }
    }

    mutation("updatePost") {
        println("in updatePost")
        resolver { id: Long, body: String, username: String, publishDate: String ->
            val post = Post(id = id, body = body, author = username, publishDate = publishDate)
            PostRepository.replace(id, post)
        }
    }

    type<User>()
    type<Post>()
    enum<Gender>()

    configure {
        useDefaultPrettyPrinter = true
    }
}
