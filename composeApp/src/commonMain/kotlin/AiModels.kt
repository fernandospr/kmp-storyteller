import kotlinx.serialization.Serializable

@Serializable
data class AiResponse(
    val candidates: List<Candidate>
)

@Serializable
data class AiRequest(
    val contents: List<Content>
)

@Serializable
data class Candidate(
    val content: Content
)

@Serializable
data class Content(
    val parts: List<Part>
)

@Serializable
data class Part(
    val text: String
)