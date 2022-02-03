package uz.umarxon.pdponline.DB.Dao

import androidx.room.*
import uz.umarxon.pdponline.DB.Entity.Modul

@Dao
interface ModulDao {
    @Query("select * from modul")
    fun getAllModul():List<Modul>

    @Insert
    fun addModul(modul: Modul)

    @Delete
    fun deleteModul(modul: Modul)

    @Update
    fun updateModul(modul: Modul)

    @Query("select * from modul where id=:id")
    fun getModulById(id:Int):Modul
}