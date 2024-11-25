package com.politaev.pnotechcase.trailer.repository

import com.politaev.pnotechcase.trailer.error.TrailerError
import com.politaev.pnotechcase.trailer.model.Trailer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InMemoryTrailerRepositoryTest {

    lateinit var trailerRepository: InMemoryTrailerRepository

    @BeforeEach
    fun setUp() {
        trailerRepository = InMemoryTrailerRepository()
    }

    @Test
    fun `given trailer exists, when findTrailerById, then return trailer`() {
        // Given
        val assetId = 123
        val expectedTrailer = Trailer(
            assetId = assetId,
            ownerId = 456,
            model = "test_model",
            vin = "test_vin"
        )
        trailerRepository.createTrailer(expectedTrailer)

        // When
        val result = trailerRepository.findTrailerById(assetId)

        // Then
        assertTrue { result.isRight() }
        val actualTrailer = result.getOrNull()
        assertEquals(expectedTrailer, actualTrailer)
    }

    @Test
    fun `given trailer does not exist, when findTrailerById, then return error`() {
        // Given
        val assetId = 123

        // When
        val result = trailerRepository.findTrailerById(assetId)

        // Then
        assertTrue { result.isLeft() }
        val expectedError = TrailerError.TrailerNotFound(assetId)
        val actualError = result.leftOrNull()
        assertEquals(expectedError, actualError)
    }

    @Test
    fun `given trailers with ownerId exist, when findTrailersByOwnerId, then return all trailers`() {
        // Given
        val ownerId = 123
        val expectedTrailers = setOf(
            Trailer(
                assetId = 123,
                ownerId = ownerId,
                model = "test_model",
                vin = "test_vin"
            ),
            Trailer(
                assetId = 456,
                ownerId = ownerId,
                model = "test_model",
                vin = "test_vin"
            ),
            Trailer(
                assetId = 789,
                ownerId = ownerId,
                model = "test_model",
                vin = "test_vin"
            )
        )
        expectedTrailers.forEach { trailerRepository.createTrailer(it) }

        // When
        val actualTrailers = trailerRepository.findTrailersByOwnerId(ownerId)

        // Then
        assertIterableEquals(expectedTrailers, actualTrailers)
    }

    @Test
    fun `given trailers with ownerId do not exist, when findTrailersByOwnerId, then return empty list`() {
        // Given
        val ownerId = 123

        // When
        val actualTrailers = trailerRepository.findTrailersByOwnerId(ownerId)

        // Then
        val expectedTrailers = emptySet<Trailer>()
        assertIterableEquals(expectedTrailers, actualTrailers)
    }

}