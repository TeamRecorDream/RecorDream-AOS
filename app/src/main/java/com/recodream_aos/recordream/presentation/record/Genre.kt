package com.recodream_aos.recordream.presentation.record // ktlint-disable package-name

import com.recodream_aos.recordream.R

enum class Genre(val genreId: Int, val viewId: Int) {

    COMEDY(1, R.id.tv_record_comedy),
    ROMANCE(2, R.id.tv_record_romance),
    FANTASY(3, R.id.tv_record_fantasy),
    HORROR(4, R.id.tv_record_horror),
    ANIMAL(5, R.id.tv_record_animal),
    FRIEND(6, R.id.tv_record_friend),
    FAMILY(7, R.id.tv_record_family),
    FOOD(8, R.id.tv_record_food),
    WORK(9, R.id.tv_record_work),
    ETC(10, R.id.tv_record_etc),
}
