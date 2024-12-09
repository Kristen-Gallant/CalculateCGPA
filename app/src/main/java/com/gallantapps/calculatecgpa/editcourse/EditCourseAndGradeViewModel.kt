package com.gallantapps.calculatecgpa.editcourse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.gallantapps.calculatecgpa.models.Course
import com.gallantapps.calculatecgpa.models.CourseRoomDatabase
import kotlinx.coroutines.launch

class EditCourseAndGradeViewModel(application: Application) : AndroidViewModel(application) {
    private val databaseDAO = CourseRoomDatabase.getDatabase(application).courseDao()
    fun update(course: Course){
        viewModelScope.launch {
            databaseDAO.update(course)
        }
    }

}