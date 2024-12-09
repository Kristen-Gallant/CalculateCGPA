package com.gallantapps.calculatecgpa.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LevelAndSemesterModel (@PrimaryKey() val LevelAndSemester : String, val TGP : Double, val TCU : Int, val GPA : String )