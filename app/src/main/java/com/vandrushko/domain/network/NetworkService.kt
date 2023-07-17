package com.vandrushko.domain.network

import com.vandrushko.data.model.ContactRequest
import com.vandrushko.data.model.EditUser
import com.vandrushko.data.model.UserRequest
import com.vandrushko.data.model.UserResponse
import com.vandrushko.data.model.UsersResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface NetworkService {
    @POST("users")
    suspend fun registerUser(@Body body: UserRequest): UserResponse

    @POST("login")
    suspend fun loginUser(@Body body: UserRequest): UserResponse

    @POST("refresh")
    suspend fun refreshToken(@Header("refreshToken") refreshToken: String?): UserResponse

    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String
    ): UserResponse

    @PUT("users/{userId}")
    suspend fun editUser(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String,
        @Body body: EditUser
    ): UserResponse

    @GET("users")
    suspend fun getAllUsers(@Header("Authorization") accessToken: String): UsersResponse

    @PUT("users/{userId}/contacts")
    suspend fun addContact(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String,
        @Body contactRequest: ContactRequest
    ): UsersResponse

    @DELETE("users/{userId}/contacts/{contactId}")
    suspend fun deleteContact(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String,
        @Path("contactId") contactId: String,
    ): UsersResponse

    @GET("users/{userId}/contacts")
    suspend fun getUserContacts(
        @Path("userId") userId: String,
        @Header("Authorization") accessToken: String
    ): UsersResponse
}
