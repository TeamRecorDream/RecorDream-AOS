package com.team.recordream.util

import com.team.recordream.R

class RecordreamMapping {
//    fun emotionMapping(emotion: Int): String {
//        var emotionStr: String = ""
//        when (emotion) {
//            0 -> emotionStr = "all"
//            1 -> emotionStr = "joy"
//            2 -> emotionStr = "sad"
//            3 -> emotionStr = "scary"
//            4 -> emotionStr = "weird"
//            5 -> emotionStr = "embarrassing"
//            6 -> emotionStr = "blank"
//        }
//        return emotionStr
//    }

    // 나중에 번호에 해당하는 스트링 수정 필요
    fun genreMapping(genre: List<Int>?): MutableList<String> {
        val genreStr = mutableListOf<String>()
        genre?.map {
            when (it) {
                0 -> genreStr.add("아직 설정되지 않았어요")
                1 -> genreStr.add("코미디")
                2 -> genreStr.add("로맨스")
                3 -> genreStr.add("판타지")
                4 -> genreStr.add("공포")
                5 -> genreStr.add("동물")
                6 -> genreStr.add("친구")
                7 -> genreStr.add("가족")
                8 -> genreStr.add("음식")
                9 -> genreStr.add("일")
                10 -> genreStr.add("기타")

                else -> {
                    throw IllegalArgumentException()
                }
            }
        }
        return genreStr
    }

    // Home, Document 모두에 사용 예정
    fun matchHomeEmotion(emotion: Int): Int {
        var emotionImg: Int = 5 // 기본은 blank로 설정
        when (emotion) {
            0 -> emotionImg = 0
            1 -> emotionImg = R.drawable.feeling_l_joy
            2 -> emotionImg = R.drawable.feeling_l_sad
            3 -> emotionImg = R.drawable.feeling_l_scary
            4 -> emotionImg = R.drawable.feeling_l_strange
            5 -> emotionImg = R.drawable.feeling_l_shy
            6 -> emotionImg = R.drawable.feeling_l_blank
        }
        return emotionImg
    }

    fun matchHomeColor(dream_color: Int): Int {
        var colorHomeImg: Int = 5
        when (dream_color) {
            0 -> colorHomeImg = R.drawable.card_l_yellow
            1 -> colorHomeImg = R.drawable.card_l_blue
            2 -> colorHomeImg = R.drawable.card_l_red
            3 -> colorHomeImg = R.drawable.card_l_purple
            4 -> colorHomeImg = R.drawable.card_l_pink
            5 -> colorHomeImg = R.drawable.card_l_white
        }
        return colorHomeImg
    }
}
