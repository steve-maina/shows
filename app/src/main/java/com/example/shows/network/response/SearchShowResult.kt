package com.example.shows.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.Serial


@Serializable
data class ShowResult (
    val show: SearchShow? = null
)

@Serializable
data class SearchShow(
    val id: Int? = null,
    val name: String? = null,
    val ended: String? = null,
    val image: ShowImage? = null,
    val summary: String? = null,
    @SerialName("_links")val links: Links? = null
)

@Serializable
data class ShowImage(
    val medium: String?= null,
    val original: String? = null
)

@Serializable
data class Links(
    @SerialName("previousepisode")
    val previousEpisode: PreviousEpisode? = null,
    @SerialName("nextepisode")
    val nextEpisode: NextEpisode? = null
)

@Serializable
data class PreviousEpisode(
    val href: String? = null,
    val name: String? = null
)
@Serializable
data class NextEpisode(
    val href: String? = null,
    val name: String? = null
)

@Serializable
data class ShowEpisode(
    val id: Int? = null,
    val airdate: String ? = null,
    val summary: String? = null
)