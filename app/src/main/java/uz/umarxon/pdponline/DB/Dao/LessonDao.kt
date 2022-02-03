package uz.umarxon.pdponline.DB.Dao

import androidx.room.*
import uz.umarxon.pdponline.DB.Entity.Lesson

@Dao
interface LessonDao {
    @Query("select * from lesson")
    fun getAllLesson():List<Lesson>

    @Insert
    fun addLesson(lesson: Lesson)

    @Delete
    fun deleteLesson(lesson: Lesson)

    @Update
    fun updateLesson(lesson: Lesson)

    @Query("select * from lesson where id=:id")
    fun getLessonById(id:Int):Lesson
}