package infor.c14220016.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import infor.c14220016.room.database.historyBelanja

class adapterHistory(private val historyBelanja: MutableList<historyBelanja>): RecyclerView
.Adapter<adapterHistory.ListViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterHistory.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.history_list, parent,
            false
        )
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return historyBelanja.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var daftar = historyBelanja[position]

        holder._tvTanggal.setText(daftar.tanggal)
        holder._tvItemBarang.setText(daftar.item)
        holder._tvJumlahBarang.setText(daftar.jumlah)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvItemBarang = itemView.findViewById<TextView>(R.id.itemBarang)
        var _tvJumlahBarang = itemView.findViewById<TextView>(R.id.jumlahText)
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tanggalTv)
    }

    fun isiData(daftar: List<historyBelanja>){
        historyBelanja.clear()
        historyBelanja.addAll(daftar)
        notifyDataSetChanged()
    }
}
