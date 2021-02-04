package com.life4.travelbook.model

data class ImageResponse(
    val hits: List<ImageResult>,
    val totalHits: Int,
    val total: Int
)
