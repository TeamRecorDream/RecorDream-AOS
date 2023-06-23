package com.recodream_aos.recordream.presentation.record.uistate

import com.recodream_aos.recordream.R

// ktlint-disable package-name

enum class Genre(val genreId: Int, val genreName: Int) {
    NOTHING(-1, R.string.genre_name_nothing),
    COMEDY(1, R.string.genre_name_comedy),
    ROMANCE(2, R.string.genre_name_romance),
    FANTASY(3, R.string.genre_name_fantasy),
    HORROR(4, R.string.genre_name_horror),
    ANIMAL(5, R.string.genre_name_animal),
    FRIEND(6, R.string.genre_name_friend),
    FAMILY(7, R.string.genre_name_family),
    FOOD(8, R.string.genre_name_food),
    WORK(9, R.string.genre_name_work),
    ETC(10, R.string.genre_name_etc),
    ;

    companion object {

        fun getValue(genreId: Int): Genre = values().find { genre ->
            genre.genreId == genreId
        } ?: NOTHING
    }
}
