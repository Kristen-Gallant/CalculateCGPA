package com.gallantapps.calculatecgpa.gpa

import android.app.Application
import androidx.lifecycle.*
import com.gallantapps.calculatecgpa.models.Course
import com.gallantapps.calculatecgpa.models.CourseRoomDatabase
import com.gallantapps.calculatecgpa.models.LevelAndSemesterModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoursesAndGradesListViewModel(application: Application ,val semester: String) : AndroidViewModel(application) {
    private val databaseDAO = CourseRoomDatabase.getDatabase(application).courseDao()
    val allCourses: LiveData<List<Course>> = databaseDAO.getCourses(semester).asLiveData()


    fun calculateGpa(): Double {
        val gpa = 0.0
        var creditTimesGrade = 0.0
        var credits = 0

        allCourses.value?.forEach {
            credits += it.courseCredit
        }

        allCourses.value?.forEach {
            creditTimesGrade += it.courseCredit * letterGradeToNumber(it.courseGrade)
        }

        CoroutineScope(Dispatchers.Default).launch {
            databaseDAO.insertCGPA(LevelAndSemesterModel(semester , creditTimesGrade , credits , String.format("%.2f", creditTimesGrade / credits).toDouble().toString() ))
        }


        return String.format("%.2f", creditTimesGrade / credits).toDouble()
    }

    fun letterGradeToNumber(letterGrade: String): Double {
        val result: Double

        when (letterGrade) {
            "A" -> result = 5.0
            "B" -> result = 4.0
            "C" -> result = 3.0
            "D" -> result = 2.0
            "E" -> result = 1.0
            else -> result = 0.0
        }

        return result
    }



}

class CoursesAndGradesListViewModelFactory(val application: Application , val semester: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoursesAndGradesListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoursesAndGradesListViewModel(application , semester) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}