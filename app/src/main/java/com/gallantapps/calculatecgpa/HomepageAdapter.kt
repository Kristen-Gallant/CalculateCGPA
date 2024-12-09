package com.gallantapps.calculatecgpa

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gallantapps.calculatecgpa.databinding.HomepageitemsBinding
import com.gallantapps.calculatecgpa.gpa.GPA
import com.gallantapps.calculatecgpa.models.CourseRoomDatabase
import com.gallantapps.calculatecgpa.models.LevelAndSemesterModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomepageAdapter(val context : Context) : ListAdapter<LevelAndSemesterModel ,HomepageAdapter.LevelAndSemesterViewHolder >(LevelAndSemesterDiffUtil()){
    val scope = CoroutineScope(Dispatchers.Default)
    private val databaseDAO = CourseRoomDatabase.getDatabase(context).courseDao()

    class LevelAndSemesterViewHolder(val binding : HomepageitemsBinding) : RecyclerView.ViewHolder(binding.root){
        val levelAndSemester = binding.levelAndSemester
        val creditUnit = binding.creditUnit
        val gradePoints = binding.gradePoints
        val GPA = binding.GPA
        val delete = binding.delete
        val pa = binding.pa
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelAndSemesterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val inflatedlayout = HomepageitemsBinding.inflate(layoutInflater)
        return LevelAndSemesterViewHolder(inflatedlayout)
    }
    override fun onBindViewHolder(holder: LevelAndSemesterViewHolder, position: Int) {
       val data = getItem(position)
        holder.levelAndSemester.text = data.LevelAndSemester
        holder.creditUnit.text = data.TCU.toString()
        holder.gradePoints.text = data.TGP.toString()
        holder.GPA.text = data.GPA

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context , GPA::class.java).putExtra("LaS" , data.LevelAndSemester))
        }
        holder.delete.setOnClickListener {
            options(data)
        }


    }
    private fun options(id : LevelAndSemesterModel){
        val optionsBottomSheet = BottomSheetDialog(context)
        optionsBottomSheet.setContentView(R.layout.bottom_sheet_layout)
        optionsBottomSheet.findViewById<LinearLayout>(R.id.delete)!!.setOnClickListener {
            scope.launch {
                databaseDAO.deleteLevelAndSemester(id)
                databaseDAO.deleteCoursesBySemester(id.LevelAndSemester)
            }
            optionsBottomSheet.dismiss()
        }
        optionsBottomSheet.show()
    }
}
class LevelAndSemesterDiffUtil : DiffUtil.ItemCallback<LevelAndSemesterModel>(){
    override fun areItemsTheSame(oldItem: LevelAndSemesterModel, newItem: LevelAndSemesterModel): Boolean {
        return  oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: LevelAndSemesterModel, newItem: LevelAndSemesterModel): Boolean {
        return  oldItem == newItem
    }
}