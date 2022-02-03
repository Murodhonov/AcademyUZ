package uz.umarxon.pdponline.DB.Dao

import androidx.room.*
import uz.umarxon.pdponline.DB.Entity.Course

@Dao
interface CourseDao {

    @Query("select * from course")
    fun getAllCourse():List<Course>

    @Insert
    fun addCourse(course: Course)

    @Delete
    fun deleteCourse(course: Course)

    @Update
    fun updateCourse(course: Course)

    @Query("select * from course where id=:id")
    fun getCourseById(id:Int):Course
}