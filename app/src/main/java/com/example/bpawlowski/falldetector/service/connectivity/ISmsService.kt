package com.example.bpawlowski.falldetector.service.connectivity

interface ISmsService{
    fun sendMessage(number: Int, message: String)
}