package com.example.demo.service

import com.example.demo.config.AppProperties
import com.example.demo.models.DeliveryStatus
import com.example.grpcdemo.service.DoorDashServiceGrpcKt
import com.example.grpcdemo.service.Doordash.Delivery
import com.example.grpcdemo.service.Doordash.EmptyInput
import com.example.grpcdemo.service.delivery
import com.google.gson.Gson
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import net.devh.boot.grpc.server.service.GrpcService
import java.security.Key
import java.time.ZoneOffset
import java.time.ZonedDateTime
import kotlin.collections.HashMap
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

@GrpcService
class DoorDashService(var appProperties: AppProperties) : DoorDashServiceGrpcKt.DoorDashServiceCoroutineImplBase() {

    var jwt: String = this.createJwt();

    override suspend fun makeAndCall(request: EmptyInput): Delivery {
        val createDeliveryResponse: DeliveryStatus = createDelivery(jwt);

        val deliveryStatus : DeliveryStatus = getDeliveryStatus(jwt, createDeliveryResponse.externalDeliveryId?: "")

        return delivery { hello = Gson().toJson(deliveryStatus) }
    }

    override suspend fun quotes(request: EmptyInput): Delivery {
        val deliveryStatus:DeliveryStatus = getQuote();
        return delivery { hello = Gson().toJson(deliveryStatus)}
    }

//    override suspend fun manyQuotes(request: EmptyInput): Delivery {
//        val deliveryStatus:DeliveryStatus = getQuote();
//        return delivery { hello = Gson().toJson(deliveryStatus)}
//    }

    fun getQuote(): DeliveryStatus {
        val body = """{
                        "external_delivery_id": "${UUID.randomUUID()}",
                        "pickup_address": "901 Market Street 6th Floor San Francisco, CA 94103",
                        "pickup_business_name": "Wells Fargo SF Downtown",
                        "pickup_phone_number": "+16505555555",
                        "pickup_instructions": "Enter gate code 1234 on the callbox.",
                        "dropoff_address": "901 Market Street 6th Floor San Francisco, CA 94103",
                        "dropoff_business_name": "Wells Fargo SF Downtown",
                        "dropoff_phone_number": "+16505555555",
                        "dropoff_instructions": "Enter gate code 1234 on the callbox.",
                        "order_value": 1999
                    }"""

        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder().uri(URI.create("https://openapi.doordash.com/drive/v2/quotes/"))
            .header("Authorization", "Bearer $jwt").header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body)).build();

        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        println(response.body())

        return Gson().fromJson(response.body(), DeliveryStatus::class.java);
    }

    fun acceptQuote(externalDeliveryId:String): DeliveryStatus{
        val body = """"""
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder().uri(URI.create("https://openapi.doordash.com/drive/v2/quotes/$externalDeliveryId/accept"))
            .header("Authorization", "Bearer $jwt").header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body)).build();

        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        println(response.body())

        return Gson().fromJson(response.body(), DeliveryStatus::class.java);
    }

    fun createJwt(): String {
        val claims = HashMap<String, Any?>();

        claims["aud"] = "doordash";
        claims["iss"] = appProperties.developer_id;
        claims["kid"] = appProperties.key_id;
        claims["exp"] = ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(1).toEpochSecond()
        claims["iat"] = ZonedDateTime.now(ZoneOffset.UTC).toEpochSecond();

        val keyBytes = Decoders.BASE64URL.decode(appProperties.signing_secret)
        val key: Key = Keys.hmacShaKeyFor(keyBytes)

        val jwt: String =
            Jwts.builder().setHeaderParam("dd-ver", "DD-JWT-V1").setHeaderParam("typ", "JWT").setClaims(claims)
                .signWith(key).compact();

        return jwt
    }

    fun createDelivery(jwt: String): DeliveryStatus {
        val body = """{
                        "external_delivery_id": "${UUID.randomUUID()}",
                        "pickup_address": "901 Market Street 6th Floor San Francisco, CA 94103",
                        "pickup_business_name": "Wells Fargo SF Downtown",
                        "pickup_phone_number": "+16505555555",
                        "pickup_instructions": "Enter gate code 1234 on the callbox.",
                        "dropoff_address": "901 Market Street 6th Floor San Francisco, CA 94103",
                        "dropoff_business_name": "Wells Fargo SF Downtown",
                        "dropoff_phone_number": "+16505555555",
                        "dropoff_instructions": "Enter gate code 1234 on the callbox.",
                        "order_value": 1999
                    }"""

        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder().uri(URI.create("https://openapi.doordash.com/drive/v2/deliveries/"))
            .header("Authorization", "Bearer $jwt").header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body)).build();

        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        println(response.body())

        return Gson().fromJson(response.body(), DeliveryStatus::class.java);
    }

    fun getDeliveryStatus(jwt: String, deliveryId: String): DeliveryStatus {
        val client = HttpClient.newBuilder().build();
        val request = HttpRequest.newBuilder()
            .uri(URI.create("https://openapi.doordash.com/drive/v2/deliveries/" + deliveryId))
            .header("Authorization", "Bearer $jwt")
            .build();

        val response = client.send(request, HttpResponse.BodyHandlers.ofString());
        println(response.body())

        return Gson().fromJson(response.body(), DeliveryStatus::class.java);
    }

}

