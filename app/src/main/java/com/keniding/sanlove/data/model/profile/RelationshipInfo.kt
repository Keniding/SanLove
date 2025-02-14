package com.keniding.sanlove.data.model.profile

data class RelationshipInfo(
    val anniversaryDate: String,
    val specialMoments: List<SpecialMoment>,
    val petNames: Pair<String, String>, // Apodos cariñosos mutuos
    val song: SongInfo, // URL de la canción de la pareja
    val meetingStory: String
)