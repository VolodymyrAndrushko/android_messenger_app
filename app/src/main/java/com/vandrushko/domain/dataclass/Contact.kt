package com.vandrushko.domain.dataclass

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.UUID

@Parcelize
data class Contact(
    val fullName: String,
    val career: String,
    val image: String? = null,
    val id: UUID = UUID.randomUUID()
) : Parcelable {
    override fun toString(): String {
        return "Contact: id: $id, Full name: $fullName, Career: $career, Image: $image"
    }
}