package com.gallantapps.calculatecgpa.addlevelandsemester

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gallantapps.calculatecgpa.R
import com.gallantapps.calculatecgpa.databinding.ActivityAddLevelAndSemesterBinding
import com.gallantapps.calculatecgpa.models.LevelAndSemesterModel
import com.google.android.material.snackbar.Snackbar

class AddLevelAndSemester : AppCompatActivity() {
    lateinit var binding : ActivityAddLevelAndSemesterBinding
    val listOfLevelAndSemester = listOf<String>("100Level(First Semester)" , "100Level(Second Semester)" ,
        "200Level(First Semester)" , "200Level(Second Semester)" ,
        "300Level(First Semester)" , "300Level(Second Semester)" ,
        "400Level(First Semester)" , "400Level(Second Semester)",
        "500Level(First Semester)" , "500Level(Second Semester)" ,
        "600Level(First Semester)" , "600Level(Second Semester)" ,
        "700Level(First Semester)" , "700Level(Second Semester)")
    private lateinit var viewModel: InsertLevelAndSemesterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this , R.layout.activity_add_level_and_semester)
        viewModel = ViewModelProvider(this).get(InsertLevelAndSemesterViewModel::class.java)
        val gradesAdapter = ArrayAdapter(this, R.layout.add_list_item, listOfLevelAndSemester)
        (binding.levelAndSemester.editText as? AutoCompleteTextView)?.setAdapter(gradesAdapter)

        binding.saveAction.setOnClickListener {
            addNewCourse()
        }

    }
    private fun addNewCourse(){
        if (binding.levelAndSemester.editText?.text!!.isNotEmpty()){
            val course = LevelAndSemesterModel( binding.levelAndSemester.editText?.text.toString() , 0.0 ,0 , "GPA:---"  )
            viewModel.insertGradeAndScore(course)
            onBackPressedDispatcher.onBackPressed()
        }else{
            Snackbar.make(binding.root , "Select and level and semester" , Snackbar.LENGTH_SHORT).show()
        }
    }
}