package com.shpp.android.task2.domain.dataclass

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

     val id: Int = getNextId()
     override fun toString(): String {
          return "Contact: id: $id, Full name: $fullName, Career: $career, Image: $image"
     }
}