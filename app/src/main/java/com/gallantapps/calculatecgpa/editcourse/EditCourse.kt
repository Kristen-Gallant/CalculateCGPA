package com.gallantapps.calculatecgpa.editcourse

import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gallantapps.calculatecgpa.R
import com.gallantapps.calculatecgpa.databinding.ActivityEditCourseBinding
import com.gallantapps.calculatecgpa.models.Course
import com.google.android.material.chip.Chip

class EditCourse : AppCompatActivity() {
    lateinit var binding : ActivityEditCourseBinding
    private lateinit var viewModel: EditCourseAndGradeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this , R.layout.activity_edit_course)
        viewModel = ViewModelProvider(this)[EditCourseAndGradeViewModel::class.java]
        binding.done.setOnClickListener {
            update()
        }
        initViews()
    }
    private fun update(){
        val intent = intent.extras
        val integer = Integer.parseInt(binding.unitsCG.findViewById<Chip>(binding.unitsCG.checkedChipId).text.toString())
        val course = Course(intent!!.getString("theid")!!.toInt() , binding.course.text.toString() ,
            integer,
            binding.gradesChipGroup.findViewById<Chip>(binding.gradesChipGroup.checkedChipId).text.toString(), intent.getString("semester")!!.toString() )
        viewModel.update(course)
        onBackPressedDispatcher.onBackPressed()
    }
    fun initViews(){
        val intent = intent.extras
        Toast.makeText(this , intent!!.getString("theid").toString() , Toast.LENGTH_LONG).show()
        when(intent!!.getString("courseCredit").toString()){
            "2" -> {
                binding.twoUnits.isChecked = true
            }
            "3" -> {
                binding.threeUnits.isChecked = true
            }
        }
        binding.course.setText(intent.getString("courseName").toString())
        when(intent!!.getString("courseGrade").toString()){
            "A" -> {
                binding.a.isChecked = true
            }
            "B" -> {
                binding.b.isChecked = true
            }
            "C" -> {
                binding.c.isChecked = true
            }
            "D" -> {
                binding.d.isChecked = true
            }
            "E" -> {
                binding.e.isChecked = true
            }
            "F" -> {
                binding.f.isChecked = true
            }
        }
    }
}