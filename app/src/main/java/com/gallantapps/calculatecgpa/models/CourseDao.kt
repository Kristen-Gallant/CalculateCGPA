
package com.gallantapps.calculatecgpa.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(course: Course)

    @Update
    suspend fun update(course: Course)

    @Delete
    suspend fun delete(course: Course)

    @Delete
    suspend fun deleteLevelAndSemester(course: LevelAndSemesterModel)

    @Query("DELETE FROM course WHERE semester = :semester")
    fun deleteCoursesBySemester(semester: String)

    @Query("SELECT * FROM course WHERE id = :id")
    fun getCourse(id: Int): Flow<Course>

    @Query("SELECT * FROM course WHERE semester =:semester ORDER BY name ASC")
    fun getCourses(semester : String): Flow<List<Course>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCGPA(course: LevelAndSemesterModel)

    @Query("SELECT * FROM LevelAndSemesterModel WHERE LevelAndSemester  =:semester")
    fun getLevelAndYear(semester : String): Flow<LevelAndSemesterModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLevelAndSemester(course: LevelAndSemesterModel)

    @Query("SELECT * FROM LevelAndSemesterModel ORDER BY LevelAndSemester ASC")
    fun getAllLevelAndSemester(): Flow<List<LevelAndSemesterModel>>
}