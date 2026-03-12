package com.example.eduskunta.mappers

import com.example.eduskunta.local.MpEntity
import com.example.eduskunta.network.MpDto

fun MpDto.toEntity(): MpEntity = MpEntity(
    personNumber = personNumber,
    firstName = firstName,
    lastName = lastName,
    party = party,
    constituency = constituency,
    twitter = twitter,
    bornYear = bornYear,
    seatNumber = seatNumber,
    picture = picture
)