package infor.c14220016.room

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import infor.c14220016.room.database.daftarBelanja
import infor.c14220016.room.database.daftarBelanjaDB
import infor.c14220016.room.database.historyBelanja
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class historyView : AppCompatActivity() {

    private lateinit var DB: daftarBelanjaDB
    private lateinit var adapterHistory: adapterHistory
    private var arHistory: MutableList<historyBelanja> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        DB = daftarBelanjaDB.getDatabase(this)
        adapterHistory = adapterHistory(arHistory)

        var _rvHistory = findViewById<RecyclerView>(R.id.rvHistory)
        _rvHistory.layoutManager = LinearLayoutManager(this)
        _rvHistory.adapter = adapterHistory
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            val historyBelanja = DB.funHistoryBelanjaDAO().selectALL()
            Log.d("data ROOM", historyBelanja.toString())
            adapterHistory.isiData(historyBelanja)
        }
    }
}
