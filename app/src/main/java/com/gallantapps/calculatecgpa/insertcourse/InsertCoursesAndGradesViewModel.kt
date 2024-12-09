package com.gallantapps.calculatecgpa.insertcourse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gallantapps.calculatecgpa.models.Course
import com.gallantapps.calculatecgpa.models.CourseRoomDatabase
import kotlinx.coroutines.launch

class InsertCoursesAndGradesViewModel(application: Application) : AndroidViewModel(application) {
    private val databaseDAO = CourseRoomDatabase.getDatabase(application).courseDao()
   fun insertGradeAndScore(course: Course){
       viewModelScope.launch {
           databaseDAO.insert(course)
       }
   }


}