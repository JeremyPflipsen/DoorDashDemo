package com.example.demo.notionsecrets

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties("notion")
data class NotionConfig(val developer_id: String, val key_id: String, val signing_secret: String)