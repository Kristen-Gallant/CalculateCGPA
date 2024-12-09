package com.gallantapps.calculatecgpa

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.gallantapps.calculatecgpa.models.CourseRoomDatabase
import com.gallantapps.calculatecgpa.models.LevelAndSemesterModel

class LevelAndSemsterViewModel(application: Application) : AndroidViewModel(application) {
    private val databaseDAO = CourseRoomDatabase.getDatabase(application).courseDao()
    val allLevelAndSemester: LiveData<List<LevelAndSemesterModel>> = databaseDAO.getAllLevelAndSemester().asLiveData()

    fun calculateGpa(): Double {
        val gpa = 0.0
        var todayGradePoint = 0.0
        var credits = 0
        allLevelAndSemester.value?.forEach {
            credits += it.TCU
        }
        allLevelAndSemester.value?.forEach {
            todayGradePoint += it.TGP
        }
       return if (credits == 0){
           return 0.0
        }else{
             String.format("%.2f", todayGradePoint / credits).toDouble()
        }


    }



}