package infor.c14220016.room

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import infor.c14220016.room.database.daftarBelanja
import infor.c14220016.room.database.daftarBelanjaDB
import infor.c14220016.room.database.historyBelanja
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var DB: daftarBelanjaDB
    private lateinit var adapterDaftar: adapterDaftar
    private var arDaftar: MutableList<daftarBelanja> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        DB = daftarBelanjaDB.getDatabase(this)

        var _fabAdd = findViewById<FloatingActionButton>(R.id.favAdd)

        _fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahDaftar::class.java))
        }

        var _fabHistory = findViewById<FloatingActionButton>(R.id.toHistory)
        _fabHistory.setOnClickListener {
            startActivity(Intent(this, historyView::class.java))
        }

        adapterDaftar = adapterDaftar(arDaftar)

        var _rvDaftar = findViewById<RecyclerView>(R.id.rvNotes)
        _rvDaftar.layoutManager = LinearLayoutManager(this)
        _rvDaftar.adapter = adapterDaftar

        adapterDaftar.setOnItemClickCallback(
            object :  adapterDaftar.OnItemClickCallback {
                override fun delData(dtBelanja: daftarBelanja) {
                    CoroutineScope(Dispatchers.IO).async {
                        DB.fundaftarBelanjaDAO().delete(dtBelanja)
                        val daftar = DB.fundaftarBelanjaDAO().selectALL()
                        withContext(Dispatchers.Main){
                            adapterDaftar.isiData(daftar)
                        }
                    }
                }

                override fun done(dtBelanja: daftarBelanja) {
                    CoroutineScope(Dispatchers.IO).async {
                        DB.funHistoryBelanjaDAO().insert(
                            historyBelanja(
                                tanggal = dtBelanja.tanggal,
                                item = dtBelanja.item,
                                jumlah = dtBelanja.jumlah
                            )
                        )
                        delData(dtBelanja)
                    }
                }
            })

    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val daftarBelanja = DB.fundaftarBelanjaDAO().selectALL()
            Log.d("data ROOM", daftarBelanja.toString())
            adapterDaftar.isiData(daftarBelanja)
        }

    }
}