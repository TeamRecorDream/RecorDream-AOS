package and.org.recordream.util

class RecordreamMapping {
    fun emotionMapping(emotion: Int): String {
        var emotionStr: String = ""
        when (emotion) {
            0 -> emotionStr = "all"
            1 -> emotionStr = "joy"
            2 -> emotionStr = "shocked"
            3 -> emotionStr = "love"
            4 -> emotionStr = "shy"
            5 -> emotionStr = "sad"
            6 -> emotionStr = "angry"
            7 -> emotionStr = "blank"
        }
        return emotionStr
    }

    fun colorMapping(dream_color: Int): String {
        var colorStr: String = ""
        when (dream_color) {
            0 -> colorStr = "dark"
            1 -> colorStr = "green_grad"
            2 -> colorStr = "blue"
            3 -> colorStr = "orange"
            4 -> colorStr = "purple"
            5 -> colorStr = "pink"
            6 -> colorStr = "red"

        }
        return colorStr

    }

    fun genreMapping(genre: Array<Int>): MutableList<String> {
        val genreStr = mutableListOf<String>()
        genre.map {
            when (it) {
                0 -> genreStr.add("코미디")
                1 -> genreStr.add("로맨스")
                2 -> genreStr.add("액션")
                3 -> genreStr.add("스릴러")
                4 -> genreStr.add("미스터리")
                5 -> genreStr.add("공포")
                6 -> genreStr.add("SF")
                7 -> genreStr.add("판타지")
                8 -> genreStr.add("가족/친구")
                9 -> genreStr.add("기타")
                10 -> genreStr.add("장르 선택 안됨")

                else -> {
                    throw IllegalArgumentException()
                }
            }
        }
        return genreStr
    }
}