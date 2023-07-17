package com.vandrushko.domain.repository

import com.vandrushko.data.model.ContactRequest
import com.vandrushko.data.model.EditUser
import com.vandrushko.data.model.UserRequest
import com.vandrushko.data.model.UserResponse
import com.vandrushko.data.model.UsersResponse
import com.vandrushko.domain.network.NetworkService
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    private val networkService: NetworkService
){
    suspend fun registerUser(body: UserRequest): UserResponse {
        return networkService.registerUser(body)
    }

    suspend fun loginUser(body: UserRequest): UserResponse {
        return networkService.loginUser(body)
    }

    suspend fun refreshToken(refreshToken: String?): UserResponse {
        return networkService.refreshToken(refreshToken)
    }
    suspend fun getUser(
        userId: String,
        accessToken: String
    ): UserResponse{
        return networkService.getUser(userId, "Bearer $accessToken")
    }

    suspend fun editUser(
        userId: String,
        accessToken: String,
        body: EditUser
    ): UserResponse {
        return networkService.editUser(userId, "Bearer $accessToken", body)
    }

    suspend fun getAllUsers(accessToken: String): UsersResponse {
        return networkService.getAllUsers(accessToken)
    }

    suspend fun addContact(
        userId: String,
        accessToken: String,
        contactRequest: ContactRequest
    ): UsersResponse {
        return networkService.addContact(userId, "Bearer $accessToken", contactRequest)
    }

    suspend fun deleteContact(
        userId: String,
        accessToken: String,
        contactId: String,
    ): UsersResponse {
        return networkService.deleteContact(userId, "Bearer $accessToken", contactId)
    }

    suspend fun getUserContacts(
        userId: String,
        accessToken: String
    ): UsersResponse {
        return networkService.getUserContacts(userId, "Bearer $accessToken")
    }
}