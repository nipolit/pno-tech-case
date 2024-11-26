package com.politaev.pnotechcase

import com.politaev.pnotechcase.dataimport.JsonDataImport.populateDataRepositories
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PnotechcaseApplication

fun main(args: Array<String>) {
    runApplication<PnotechcaseApplication>(*args)
        .also { populateDataRepositories(it) }
}
