package com.blydenburgh.kotlineverywhere

import com.blydenburgh.kotlineverywhere.server.Server
import org.http4k.server.Jetty
import org.http4k.server.asServer


fun main() {
    Server.start().asServer(Jetty(8080)).start()
}

