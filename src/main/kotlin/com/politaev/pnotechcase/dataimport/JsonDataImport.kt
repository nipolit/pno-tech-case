package com.politaev.pnotechcase.dataimport

import com.fasterxml.jackson.databind.ObjectMapper
import com.politaev.pnotechcase.rental.model.Rental
import com.politaev.pnotechcase.rental.repository.InMemoryRentalRepository
import com.politaev.pnotechcase.trailer.model.Trailer
import com.politaev.pnotechcase.trailer.repository.InMemoryTrailerRepository
import org.springframework.context.ApplicationContext
import java.io.File

object JsonDataImport {
    fun populateDataRepositories(applicationContext: ApplicationContext) {
        val rentalRepository = applicationContext.getBean(InMemoryRentalRepository::class.java)
        val trailerRepository = applicationContext.getBean(InMemoryTrailerRepository::class.java)
        val objectMapper = applicationContext.getBean(ObjectMapper::class.java)
        val typeFactory = objectMapper.typeFactory

        val dataFilesDir = File("src/main/resources/data")

        val rentalsDataFile = dataFilesDir.resolve("rentals.json")
        val trailersDataFile = dataFilesDir.resolve("trailers.json")

        val rentalCollectionType = typeFactory.constructCollectionType(List::class.java, Rental::class.java)
        objectMapper.readValue<List<Rental>>(rentalsDataFile, rentalCollectionType)
            .asSequence()
            .forEach(rentalRepository::createRental)

        val trailerCollectionType = typeFactory.constructCollectionType(List::class.java, Trailer::class.java)
        objectMapper.readValue<List<Trailer>>(trailersDataFile, trailerCollectionType)
            .asSequence()
            .forEach(trailerRepository::createTrailer)
    }
}