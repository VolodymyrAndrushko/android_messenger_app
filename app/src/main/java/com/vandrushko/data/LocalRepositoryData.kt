package com.vandrushko.data

import com.vandrushko.data.model.Contact


class LocalRepositoryData {
    fun getLocalContactsList(): List<Contact> = listOf(
        Contact("ddsfsdf@dfgh.f", "1"),
        Contact("ddsfsdf@dfgh.f", "2"),
        Contact("ddsfsdf@dfgh.f", "3"),
        Contact("ddsfsdf@dfgh.f", "4"),
        Contact("ddsfsdf@dfgh.f", "5"),
        Contact("ddsfsdf@dfgh.f", "6"),
        Contact("ddsfsdf@dfgh.f", "7"),
    )
}