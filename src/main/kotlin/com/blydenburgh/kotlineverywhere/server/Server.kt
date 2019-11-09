package com.blydenburgh.kotlineverywhere.server

import com.blydenburgh.kotlineverywhere.graphql.schema
import com.fasterxml.jackson.databind.ObjectMapper
import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.routes

data class GraphQLReqeuest(val query: String = "")
object Server {
    private val mapper = ObjectMapper()
    fun start(): HttpHandler = routes(
        "/graphql" bind Method.POST to { req: Request ->
            val jsonReq = mapper.readValue(req.bodyString(), GraphQLReqeuest::class.java)
            val res = schema.runCatching {
                execute(jsonReq.query)
            }
            val response = res.fold(onSuccess = { it }, onFailure = {
                """
                {
                    "errors": ${it.message}
                }
            """.trimIndent()
            })
            Response(Status.OK).body(response)
        }
    )
}