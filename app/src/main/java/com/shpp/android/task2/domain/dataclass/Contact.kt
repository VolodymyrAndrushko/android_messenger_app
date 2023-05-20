package com.shpp.android.task2.domain.dataclass

import java.util.UUID

data class Contact(
     val fullName: String,
     val career: String,
     val image: String? = null
) {
     companion object {
          private var currentId = 0

          fun getNextId(): Int {
               return currentId++
          }
     }

     val id: Long = UUID.randomUUID().mostSignificantBits

     override fun toString(): String {
          return "Contact: id=$id, fullName=$fullName, career=$career, image=$image"
     }
}