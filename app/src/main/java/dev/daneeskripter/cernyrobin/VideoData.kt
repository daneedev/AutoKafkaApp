package dev.daneeskripter.cernyrobin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoInfo(
    @SerialName("title") val title: String,
    @SerialName("author_name") val authorName: String,
    @SerialName("author_url") val authorUrl: String,
    @SerialName("type") val type: String,
    @SerialName("height") val height: Int,
    @SerialName("width") val width: Int,
    @SerialName("version") val version: String,
    @SerialName("provider_name") val providerName: String,
    @SerialName("provider_url") val providerUrl: String,
    @SerialName("thumbnail_height") val thumbnailHeight: Int,
    @SerialName("thumbnail_width") val thumbnailWidth: Int,
    @SerialName("thumbnail_url") val thumbnailUrl: String,
    @SerialName("html") val html: String,
    @SerialName("description") val description: String
)

@Serializable
data class Video(
    @SerialName("answers") val answers: String,
    @SerialName("transcript") val transcript: String,
    @SerialName("language") val language: String,
    @SerialName("video_info") val videoInfo: VideoInfo,
    @SerialName("video_url") val videoUrl: String

)

@Serializable
data class AutoKafka(
    @SerialName("code") val code: Int,
    @SerialName("message") val message: String,
    @SerialName("list") val list: Map<String, Video>
)