package com.example.digiit.data


class RemoteUserDataProvider: UserDataProvider() {
    override fun isOnline(): Boolean {
        return true
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