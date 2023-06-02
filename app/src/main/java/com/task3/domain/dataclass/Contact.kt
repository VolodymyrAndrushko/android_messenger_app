package com.task3.domain.dataclass

import android.os.Parcelable
import android.widget.ImageView
import kotlinx.android.parcel.Parcelize
import java.util.UUID

// TODO Parcelize annotations from package 'kotlinx.android.parcel' are deprecated. Change package to 'kotlinx.parcelize'
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