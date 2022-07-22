package and.org.recordream.data.local

import and.org.recordream.data.remote.response.ResponseHomeRecord

data class HomeItemData(
    val nickname: String,
    val records: List<ResponseHomeRecord>
)