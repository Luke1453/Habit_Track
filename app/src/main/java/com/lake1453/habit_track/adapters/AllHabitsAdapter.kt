package com.lake1453.habit_track.adapters

import android.annotation.SuppressLint
import android.text.format.DateFormat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.OnClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.lake1453.habit_track.R
import com.lake1453.habit_track.model.Habit
import kotlinx.android.synthetic.main.habit_list_tile.view.*
import java.text.SimpleDateFormat


class AllHabitsAdapter(private val interaction: Interaction? = null) : ListAdapter<Habit, AllHabitsAdapter.AllHabitsViewHolder>(HabitDC()) {

    var onItemClick: ((Habit) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AllHabitsViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.habit_list_tile, parent, false), interaction
    )

    override fun onBindViewHolder(holder: AllHabitsViewHolder, position: Int) = holder.bind(getItem(position))

    fun swapData(data: List<Habit>) {
        submitList(data.toMutableList())
    }

    inner class AllHabitsViewHolder(itemView: View, private val interaction: Interaction?) : RecyclerView.ViewHolder(itemView), OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            val clicked = getItem(adapterPosition)
            onItemClick?.invoke(clicked)
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: Habit) = with(itemView) {
            habit_name.text = item.name
            habit_desc.text = item.description

            val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm")
            val dateString = formatter.format(item.startTime)
            habit_start_time.text = "Habit start time: ${dateString}"

            val typeArray = arrayOf<String>(*context.resources.getStringArray(R.array.habit_types))

            habit_type.text = "Habit Type: " + typeArray[item.habitType-1]

            if (item.repeatable) {
                habit_repeatable.text = "Habit is repeatable"
                habit_repeat_period.text = "Habit repeats every ${item.repeatPeriod} days"
            } else {
                habit_repeatable.text = "Habit is not repeatable"
                habit_repeat_period.visibility = View.GONE

            }
        }
    }

    interface Interaction {
    }

    private class HabitDC : DiffUtil.ItemCallback<Habit>() {
        override fun areItemsTheSame(
            oldItem: Habit,
            newItem: Habit
        ) = oldItem.id == newItem.id


        override fun areContentsTheSame(
            oldItem: Habit,
            newItem: Habit
        ) = oldItem == newItem
    }
}
