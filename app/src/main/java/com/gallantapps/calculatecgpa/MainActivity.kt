package com.gallantapps.calculatecgpa

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.gallantapps.calculatecgpa.addlevelandsemester.AddLevelAndSemester
import com.gallantapps.calculatecgpa.databinding.ActivityMainBinding
import com.gallantapps.calculatecgpa.models.LevelAndSemesterModel

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: LevelAndSemsterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this , R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(LevelAndSemsterViewModel::class.java)

        if (viewModel.calculateGpa().isNaN()){
            binding.CGPA.text = "---"
        }else{
            binding.CGPA.text = viewModel.calculateGpa().toString()
        }
        viewModel = ViewModelProvider(this).get(LevelAndSemsterViewModel::class.java)
        val adapter = HomepageAdapter(this)
        binding.levelAndSemester.adapter = adapter
        viewModel.allLevelAndSemester.observe(this){
            adapter.submitList(it)
            if (viewModel.calculateGpa().isNaN()){
                binding.CGPA.text = "---"
            }else{
                binding.CGPA.text = viewModel.calculateGpa().toString()
            }

        }
        binding.FAB.setOnClickListener {
            startActivity(Intent(this , AddLevelAndSemester::class.java))
        }

    }

}