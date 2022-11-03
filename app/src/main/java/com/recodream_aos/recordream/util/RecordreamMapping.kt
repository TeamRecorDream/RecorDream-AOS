package com.recodream_aos.recordream.util

import com.recodream_aos.recordream.R

class RecordreamMapping {
//    fun emotionMapping(emotion: Int): String {
//        var emotionStr: String = ""
//        when (emotion) {
//            0 -> emotionStr = "all"
//            1 -> emotionStr = "joy"
//            2 -> emotionStr = "shocked"
//            3 -> emotionStr = "love"
//            4 -> emotionStr = "shy"
//            5 -> emotionStr = "sad"
//            6 -> emotionStr = "angry"
//            7 -> emotionStr = "blank"
//        }
//        return emotionStr
//    }

    // 나중에 번호에 해당하는 스트링 수정 필요
    fun genreMapping(genre: List<Int>?): MutableList<String> {
        val genreStr = mutableListOf<String>()
        genre?.map {
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
                10 -> genreStr.add("아직 설정되지 않았어요")

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
            3 -> emotionImg = R.drawable.feeling_l_strange
            4 -> emotionImg = R.drawable.feeling_l_scary
            5 -> emotionImg = R.drawable.feeling_l_blank
            6 -> emotionImg = R.drawable.feeling_l_shy
        }
        return emotionImg
    }

    fun matchHomeColor(dream_color: Int): Int {
        var colorHomeImg: Int = 5
        when (dream_color) {
            0 -> colorHomeImg = R.drawable.card_m_yellow
            1 -> colorHomeImg = R.drawable.card_m_blue
            2 -> colorHomeImg = R.drawable.card_m_purple
            3 -> colorHomeImg = R.drawable.card_m_red
            4 -> colorHomeImg = R.drawable.card_m_white
            5 -> colorHomeImg = R.drawable.card_m_pink
        }
        return colorHomeImg
    }
}
