package com.politaev.pnotechcase.rental.repository

import com.politaev.pnotechcase.rental.model.Rental
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class InMemoryRentalRepositoryTest {

    lateinit var rentalRepository: InMemoryRentalRepository

    @BeforeEach
    fun setUp() {
        rentalRepository = InMemoryRentalRepository()
    }

    @Test
    fun `given rentals with customerId exist, when findRentalsByCustomerId, then return all rentals`() {
        // Given
        val customerId = 123
        val expectedRentals = setOf(
            Rental(
                rentalId = 12,
                trailerId = 34,
                customerId = customerId,
                startTime = LocalDate.EPOCH,
                endTime = LocalDate.MAX
            ),
            Rental(
                rentalId = 56,
                trailerId = 78,
                customerId = customerId,
                startTime = LocalDate.EPOCH,
                endTime = LocalDate.MAX
            ),
            Rental(
                rentalId = 90,
                trailerId = 87,
                customerId = customerId,
                startTime = LocalDate.EPOCH,
                endTime = LocalDate.MAX
            )
        )
        expectedRentals.forEach { rentalRepository.createRental(it) }

        // When
        val actualRentals = rentalRepository.findRentalsByCustomerId(customerId)

        // Then
        assertIterableEquals(expectedRentals, actualRentals)
    }

    @Test
    fun `given rentals with customerId do not exist, when findRentalsByCustomerId, then return empty list`() {
        // Given
        val customerId = 123

        // When
        val actualRentals = rentalRepository.findRentalsByCustomerId(customerId)

        // Then
        val expectedRentals = emptySet<Rental>()
        assertIterableEquals(expectedRentals, actualRentals)
    }

}