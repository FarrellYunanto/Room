package infor.c14220016.room.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface historyDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(daftar: historyBelanja)

    @Query("UPDATE historyBelanja SET tanggal=:isi_tanggal, item=:isi_item, jumlah=:isi_jumlah WHERE id=:pilihid")
    fun update(isi_tanggal:String, isi_item: String, isi_jumlah: String,  pilihid: Int)

    @Delete
    fun delete(daftar: historyBelanja)

    @Query("SELECT * FROM historyBelanja ORDER BY id asc")
    fun selectALL() : MutableList<historyBelanja>

    @Query("SELECT * FROM historyBelanja WHERE id=:isi_id")
    suspend fun getItem(isi_id:Int) : historyBelanja
}