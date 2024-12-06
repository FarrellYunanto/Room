package infor.c14220016.room

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import infor.c14220016.room.database.daftarBelanjaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {

    private lateinit var DB: daftarBelanjaDB

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



    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val daftarBelanja = DB.fundaftarBelanjaDAO().selectALL()
            Log.d("data ROOM", daftarBelanja.toString())
        }
    }
}