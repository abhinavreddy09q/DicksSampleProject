package com.example.dicks.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.abinav_dicks.R
import com.example.abinav_dicks.databinding.ItemSeatGeekBinding
import java.text.SimpleDateFormat
import java.util.*


class SeatGeekAdapter(private val listener: SeatGeekItemListener) :
    RecyclerView.Adapter<SeatGeekViewHolder>() {

    private val mOriginalArrayList = emptyList<ItemSeatGeek>().toMutableList()
    private var mArrayList = emptyList<ItemSeatGeek>().toMutableList()

    fun setItems(items: List<ItemSeatGeek>) {
        this.mOriginalArrayList.clear()
        this.mOriginalArrayList.addAll(items)
        this.mArrayList.clear()
        this.mArrayList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatGeekViewHolder {
        val binding: ItemSeatGeekBinding =
            ItemSeatGeekBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeatGeekViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = mArrayList.size

    override fun onBindViewHolder(holder: SeatGeekViewHolder, position: Int) =
        holder.bind(mArrayList[position])

    interface SeatGeekItemListener {
        fun onItemClicked(itemSeatGeek: ItemSeatGeek)
    }

    fun performSearch(query: String) {
        if(query.isEmpty()){
            mArrayList.clear()
            mArrayList.addAll(mOriginalArrayList)
        }else{
            val newList = mOriginalArrayList.filter { it.title.contains(query, true) }
            mArrayList.clear()
            mArrayList.addAll(newList)
        }

        notifyDataSetChanged()
    }
}

class SeatGeekViewHolder(
    private val itemBinding: ItemSeatGeekBinding,
    private val listener: SeatGeekAdapter.SeatGeekItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var itemSeatGeek: ItemSeatGeek

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: ItemSeatGeek) {
        this.itemSeatGeek = item
        itemBinding.tvItemTitle.text = item.title
        itemBinding.tvItemLocation.text = item.location
        itemBinding.tvItemDate.text = item.date.toReadableDate()
        Glide.with(itemBinding.imvItemLogo)
            .load(item.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            .into(itemBinding.imvItemLogo)
    }

    override fun onClick(v: View?) {
        listener.onItemClicked(itemSeatGeek)
    }
}

fun String.toReadableDate(): String {
    val inputSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val outSdf = SimpleDateFormat("EEE,dd MMM yyyy HH:mm a ")
    val date: Date = inputSdf.parse(this)
    return outSdf.format(date)
}