package com.gallantapps.calculatecgpa.gpa

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gallantapps.calculatecgpa.R
import com.gallantapps.calculatecgpa.databinding.ActivityGpaBinding
import com.gallantapps.calculatecgpa.insertcourse.InsertCourse
import com.gallantapps.calculatecgpa.models.Course
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class GPA : AppCompatActivity() {
    lateinit var binding : ActivityGpaBinding
    val scope = CoroutineScope(Dispatchers.Default)
    lateinit var adapter : GPAAdapter
    private lateinit var myViewModel : CoursesAndGradesListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this , R.layout.activity_gpa)
        val intent = intent.extras
        myViewModel = ViewModelProvider(this, CoursesAndGradesListViewModelFactory(application, intent!!.getString("LaS").toString()))[CoursesAndGradesListViewModel::class.java]
        adapter =
            GPAAdapter(this            )
        myViewModel.allCourses.observe(this){data ->
            initAdapter(data.toMutableList())
            if (myViewModel.calculateGpa().isNaN()){
                binding.CGPA.text = "---"
            }else{
                binding.CGPA.text = myViewModel.calculateGpa().toString()
            }
        }
        binding.FAB.setOnClickListener {
           startActivity(Intent(this , InsertCourse::class.java).putExtra("LaS" , intent.getString("LaS").toString()))
        }
    }
    fun initAdapter(semester : MutableList<Course>){
        binding.coursesAndGradesRecyclerview.adapter = adapter
        binding.coursesAndGradesRecyclerview.layoutManager = GridLayoutManager(this , 2 , RecyclerView.VERTICAL , false)
        adapter.submitList(semester)
    }
}