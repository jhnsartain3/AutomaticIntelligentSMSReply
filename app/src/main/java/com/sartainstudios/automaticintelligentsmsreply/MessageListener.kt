package com.sartainstudios.automaticintelligentsmsreply

interface MessageListener {
    /**
     * Method called when message received
     * @param message Message
     */
    fun messageReceived(message: String)
}

