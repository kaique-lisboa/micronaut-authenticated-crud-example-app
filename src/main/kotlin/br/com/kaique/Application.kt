package br.com.kaique

import io.micronaut.runtime.Micronaut.*
import org.h2.tools.Server

fun main(args: Array<String>) {
    // Initializes h2-console webserver on port 8082
    Server.createWebServer().start()
    build()
            .args(*args)
            .packages("br.com.kaique")
            .start()
}

