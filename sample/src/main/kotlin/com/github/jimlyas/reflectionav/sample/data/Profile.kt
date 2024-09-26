package com.github.jimlyas.reflectionav.sample.data

import java.math.BigDecimal

data class Profile(
    val id: String,
    val name: String,
    val age: Int,
    val currentBalance: BigDecimal
)

val dummyData = listOf(
    Profile(
        "twdgfsdsdfs",
        "Robert Downey Jr.",
        59,
        BigDecimal(100000)
    ),
    Profile(
        "df3rw4etfes",
        "Mark Ruffalo",
        56,
        BigDecimal(329400)
    ),
    Profile(
        "231sasadsa4",
        "Scarlet Johansson",
        39,
        BigDecimal(2133433)
    ),
    Profile(
        "2435kjdrtgjidfklg",
        "Chris Evans",
        43,
        BigDecimal(2342423)
    ),
    Profile(
        "35trdghdfhj",
        "Chris Hemsworth",
        41,
        BigDecimal(32234454)
    ),
    Profile(
        "kljhtlerukhtgdsf",
        "Jeremy Renner",
        53,
        BigDecimal(65654333)
    )
)