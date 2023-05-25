package com.task3.domain.dataclass

import java.util.UUID

data class Contact(
    val fullName: String,
    val career: String,
    val image: String? = null,
    val id: UUID = UUID.randomUUID()
) {
    override fun toString(): String {
        return "Contact: id: $id, Full name: $fullName, Career: $career, Image: $image"
    }
}