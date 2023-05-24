package com.example.demo.service

import com.example.grpcdemo.service.Demo
import com.example.grpcdemo.service.UserServiceGrpcKt
import com.google.protobuf.Int64Value
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class UserService: UserServiceGrpcKt.UserServiceCoroutineImplBase() {
    override suspend fun getUserById(userId: Int64Value): Demo.User {
        return Demo.User
            .newBuilder()
            .setId(userId.value)
            .setName("Lorefnon")
            .build()

//        return user {
//            id = 1
//            name = "Lorefnon"
//        }
    }

}