package com.example.digiit.data


class LocalUserDataProvider: UserDataProvider() {
    override fun isOnline(): Boolean {
        return false
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun getEmail(): String {
        TODO("Not yet implemented")
    }

    override fun getTickets(): List<Ticket> {
        TODO("Not yet implemented")
    }
}