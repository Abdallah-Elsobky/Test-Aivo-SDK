package com.example.test_sdk

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform