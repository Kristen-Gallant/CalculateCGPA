package com.gallantapps.calculatecgpa.gpa

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gallantapps.calculatecgpa.R
import com.gallantapps.calculatecgpa.databinding.CoursesitemBinding
import com.gallantapps.calculatecgpa.editcourse.EditCourse
import com.gallantapps.calculatecgpa.insertcourse.InsertCourse
import com.gallantapps.calculatecgpa.models.Course
import com.gallantapps.calculatecgpa.models.CourseRoomDatabase
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GPAAdapter(val context: Context) : ListAdapter<Course ,GPAAdapter.GPAHolder >(CoursesAndGradeAdapterDiffUtil()){
    val scope = CoroutineScope(Dispatchers.Default)
    private val databaseDAO = CourseRoomDatabase.getDatabase(context).courseDao()

    class GPAHolder(private val binding : CoursesitemBinding) : RecyclerView.ViewHolder(binding.root){
        val courseName = binding.courseName
        val units = binding.units
        val grade = binding.grade
        val delete = binding.delete
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GPAHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val inflatedlayout = CoursesitemBinding.inflate(layoutInflater)
        return GPAHolder(inflatedlayout)
    }
    override fun onBindViewHolder(holder: GPAHolder, position: Int) {
       val data = getItem(position)
        holder.courseName.text = data.courseName
        holder.units.text = "${data.courseCredit.toString()} units"
        holder.grade.text = "Grade ${data.courseGrade}"
        holder.courseName.text = data.courseName
        holder.itemView.setOnClickListener {
            val intent = Intent(context , EditCourse::class.java)
            intent.putExtra("theid" , data.id.toString())
            intent.putExtra("courseName" , data.courseName)
            intent.putExtra("courseCredit" , data.courseCredit.toString())
            intent.putExtra("courseGrade" , data.courseGrade)
            intent.putExtra("semester" , data.semester)
            context.startActivity(intent)
        }
        holder.delete.setOnClickListener {
            options(data)
        }
    }
    private fun options(id : Course){
        val optionsBottomSheet = BottomSheetDialog(context)
        optionsBottomSheet.setContentView(R.layout.bottom_sheet_layout)
        optionsBottomSheet.findViewById<LinearLayout>(R.id.delete)!!.setOnClickListener {
            scope.launch {
                databaseDAO.delete(id)
            }
            optionsBottomSheet.dismiss()
        }
        optionsBottomSheet.show()
    }
}
class CoursesAndGradeAdapterDiffUtil : DiffUtil.ItemCallback<Course>(){
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return  oldItem == newItem
    }
    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return  oldItem == newItem
    }
}