package com.vandrushko.domain.repository

import com.vandrushko.data.model.ContactRequest
import com.vandrushko.data.model.EditUser
import com.vandrushko.data.model.UserRequest
import com.vandrushko.data.model.UserResponse
import com.vandrushko.data.model.UsersResponse
import com.vandrushko.di.network.NetworkService
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
    suspend fun registerUser(@Body body: UserRequest): UserResponse {
        return networkService.registerUser(body)
    }

    suspend fun loginUser(@Body body: UserRequest): UserResponse {
        return networkService.loginUser(body)
    }

    suspend fun refreshToken(@Header("refreshToken") refreshToken: String?): UserResponse {
        return networkService.refreshToken(refreshToken)
    }
    suspend fun getUser(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String
    ): UserResponse{
        return networkService.getUser(userId, accessToken)
    }

    suspend fun editUser(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String,
        @Body body: EditUser
    ): UserResponse {
        return networkService.editUser(userId, accessToken, body)
    }

    suspend fun getAllUsers(@Header("Authorization") accessToken: String): UsersResponse {
        return networkService.getAllUsers(accessToken)
    }

    suspend fun addContact(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String,
        @Body contactRequest: ContactRequest
    ): UsersResponse {
        return networkService.addContact(userId, accessToken, contactRequest)
    }

    suspend fun deleteContact(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String,
        @Body contactRequest: ContactRequest,
    ): UsersResponse {
        return networkService.deleteContact(userId, accessToken, contactRequest)
    }

    suspend fun getUserContacts(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String
    ): UsersResponse {
        return networkService.getUserContacts(userId, accessToken)
    }
}