package com.gallantapps.calculatecgpa.addlevelandsemester

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gallantapps.calculatecgpa.models.CourseRoomDatabase
import com.gallantapps.calculatecgpa.models.LevelAndSemesterModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InsertLevelAndSemesterViewModel(application: Application) : AndroidViewModel(application) {
    private val scope = CoroutineScope(Dispatchers.Default)

    private val databaseDAO = CourseRoomDatabase.getDatabase(application).courseDao()
    fun insertGradeAndScore(levelAndSemester: LevelAndSemesterModel){
        scope.launch {
            databaseDAO.insertLevelAndSemester(levelAndSemester)
        }
    }
}