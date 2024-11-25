package com.politaev.pnotechcase.trailer.error

sealed interface TrailerError {
    data class TrailerNotFound(val trailerId: Int) : TrailerError
}