package and.org.recordream.util

import and.org.recordream.R

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

    fun emotionImgMapping(emotion: Int): Int {
        var emotionImg : Int = 0
        when (emotion) {
            1 -> emotionImg = R.drawable.icon_2_joy
            2 -> emotionImg = R.drawable.icon_2_shocked
            3 -> emotionImg = R.drawable.icon_2_love
            4 -> emotionImg = R.drawable.icon_2_shy
            5 -> emotionImg = R.drawable.icon_2_sad
            6 -> emotionImg = R.drawable.icon_2_angry
            7 -> emotionImg = R.drawable.icon_2_blank
        }
        return emotionImg
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

    fun genreMapping(genre: List<Int>): MutableList<String> {
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
                10 -> genreStr.add("아직 설정되지 않았어요")

                else -> {
                    throw IllegalArgumentException()
                }
            }
        }
        return genreStr
    }

    fun matchEmotion(emotion: Int): Int {
        var emotionImg: Int = 7
        when (emotion) {
            1 -> emotionImg = R.drawable.icon_3_joy
            2 -> emotionImg = R.drawable.icon_3_shocked
            3 -> emotionImg = R.drawable.icon_3_love
            4 -> emotionImg = R.drawable.icon_3_shy
            5 -> emotionImg = R.drawable.icon_3_sad
            6 -> emotionImg = R.drawable.icon_3_angry
            7 -> emotionImg = R.drawable.icon_3_blank
        }
        return emotionImg
    }

    fun matchHomeColor(dream_color: Int): Int {
        var colorHomeImg: Int = 0
        when (dream_color) {
            0 -> colorHomeImg = R.drawable.property_1_card_main_black
            1 -> colorHomeImg = R.drawable.property_1_card_main_green
            2 -> colorHomeImg = R.drawable.property_1_card_main_blue
            3 -> colorHomeImg = R.drawable.property_1_card_main_orange
            4 -> colorHomeImg = R.drawable.property_1_card_main_purple
            5 -> colorHomeImg = R.drawable.property_1_card_main_pink
            6 -> colorHomeImg = R.drawable.property_1_card
        }
        return colorHomeImg
    }

    fun matchDetailColor(dream_color: Int): Int {
        var colorDetailImg: Int = 0
        when (dream_color) {
            0 -> colorDetailImg = R.drawable.property_1_bg_record_black
            1 -> colorDetailImg = R.drawable.property_1_bg_record_green
            2 -> colorDetailImg = R.drawable.property_1_bg_record_blue
            3 -> colorDetailImg = R.drawable.property_1_bg_record_orange
            4 -> colorDetailImg = R.drawable.property_1_bg_record_purple
            5 -> colorDetailImg = R.drawable.property_1_bg_record_pink
            6 -> colorDetailImg = R.drawable.property_1_bg_record_red
        }
        return colorDetailImg
    }

    fun matchTextColor(dream_color: Int): Int {
        var textColor: Int = 0
        when (dream_color) {
            0 -> textColor = R.color.dark_blue03_28283A
            1 -> textColor = R.color.sub_green01_26C179
            2 -> textColor = R.color.sub_blue01_00C9FF
            3 -> textColor = R.color.sub_orange01_F85F4A
            4 -> textColor = R.color.sub_purple01_AB53F9
            5 -> textColor = R.color.sub_pink01_EA37A2
            6 -> textColor = R.color.sub_red01_D60228
        }
        return textColor
    }
}
