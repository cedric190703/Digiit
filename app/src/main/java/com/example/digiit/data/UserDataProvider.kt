package com.example.digiit.data


abstract class UserDataProvider {
    abstract fun isOnline(): Boolean
    abstract fun getName(): String
    abstract fun getEmail(): String
    abstract fun getTickets(): List<Ticket>
}
