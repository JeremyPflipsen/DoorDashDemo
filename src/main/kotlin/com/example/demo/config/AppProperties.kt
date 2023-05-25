package com.example.demo.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AppProperties {
    @Value("\${developer_id}")
    lateinit var developer_id: String

    @Value("\${key_id}")
    lateinit var key_id: String

    @Value("\${signing_secret}")
    lateinit var signing_secret: String
}