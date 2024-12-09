package com.gallantapps.calculatecgpa.insertcourse

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gallantapps.calculatecgpa.R
import com.gallantapps.calculatecgpa.databinding.ActivityInsertCourseBinding
import com.gallantapps.calculatecgpa.models.Course
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar

class InsertCourse : AppCompatActivity() {
    lateinit var binding : ActivityInsertCourseBinding
    private lateinit var viewModel: InsertCoursesAndGradesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this , R.layout.activity_insert_course)
        viewModel = ViewModelProvider(this)[InsertCoursesAndGradesViewModel::class.java]
        binding.done.setOnClickListener {
           if (binding.course.text.isNotEmpty() && binding.unitsCG.checkedChipIds.isNotEmpty() && binding.gradesChipGroup.checkedChipIds.isNotEmpty() ){
           addNewCourse()
        }else{
            Snackbar.make(binding.root , "Complete all fields" , Snackbar.LENGTH_SHORT).show()
           }
       }

    }
    fun addNewCourse(){
        val intent = intent.extras
        val integer = Integer.parseInt(binding.unitsCG.findViewById<Chip>(binding.unitsCG.checkedChipId).text.toString())
        val course = Course(0 , binding.course.text.toString(),
            integer,
             binding.gradesChipGroup.findViewById<Chip>(binding.gradesChipGroup.checkedChipId).text.toString(), intent!!.getString("LaS").toString())
        viewModel.insertGradeAndScore(course)
        onBackPressedDispatcher.onBackPressed()
    }
}
