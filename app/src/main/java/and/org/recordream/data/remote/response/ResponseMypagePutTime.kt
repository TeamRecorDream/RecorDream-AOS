package and.org.recordream.data.remote.response

data class ResponseMypagePutTime(
    val `data`: Data,
) {
    data class Data(
        val _id: String
    )
}
