package com.example.demo.service

import com.example.grpcdemo.service.Demo
import com.example.grpcdemo.service.UserServiceGrpcKt
import com.example.grpcdemo.service.user
import com.google.protobuf.Int64Value
import io.grpc.stub.StreamObserver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class UserService: UserServiceGrpcKt.UserServiceCoroutineImplBase() {
    override suspend fun getUserById(userId: Int64Value): Demo.User {
//        return Demo.User
//            .newBuilder()
//            .setId(userId.value)
//            .setName("Lorefnon")
//            .build()

        return user {
            id = 1
            name = "Lorefnon"
        }
    }

    override fun listUsers(request: Demo.ListUsersInput): Flow<Demo.User> {
        return listOf(
            Demo.User.newBuilder().apply {
                id = 10
                name = "Harry"
            }.build(),
            Demo.User.newBuilder().apply {
                id = 20
                name = "Hermione"
            }.build(),
            Demo.User.newBuilder().apply {
                id = 20
                name = "Ron"
            }.build()
        ).asFlow()
    }
}