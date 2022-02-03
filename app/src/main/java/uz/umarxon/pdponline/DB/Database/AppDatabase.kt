package uz.umarxon.pdponline.DB.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.umarxon.pdponline.DB.Dao.CourseDao
import uz.umarxon.pdponline.DB.Dao.LessonDao
import uz.umarxon.pdponline.DB.Dao.ModulDao
import uz.umarxon.pdponline.DB.Entity.Course
import uz.umarxon.pdponline.DB.Entity.Lesson
import uz.umarxon.pdponline.DB.Entity.Modul

@Database(entities = [Course::class,Modul::class,Lesson::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun courseDao():CourseDao
    abstract fun moduleDao():ModulDao
    abstract fun lessonDao(): LessonDao

    companion object{
        private var instanse:AppDatabase?= null

        @Synchronized
        fun getInstance(context: Context):AppDatabase{

            when(instanse){
                null->{
                    instanse = Room.databaseBuilder(context,AppDatabase::class.java,"db_order")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }

            return instanse!!
        }

    }
}