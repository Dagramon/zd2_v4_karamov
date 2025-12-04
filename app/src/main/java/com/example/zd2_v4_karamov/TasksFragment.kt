package com.example.zd2_v4_karamov

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.concurrent.thread

var taskList = mutableListOf<Task>()

class TasksFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        val backButton = view.findViewById<Button>(R.id.backButton)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val removeDone = view.findViewById<Button>(R.id.buttonRemoveDone)

        val db = MainDB.getDb(this.requireContext())

        removeDone.setOnClickListener {

            Thread {
                db.getDao().deleteCompletedTasks()
            }.start()

        }


        db.getDao().getAllItems().asLiveData().observe(this.requireContext() as LifecycleOwner){

            taskList.clear()
            it.forEach {
                taskList.add(Task(it.id, name = it.name, description = it.description, done = it.done, date = it.date, category = it.category))
            }

            recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
            recyclerView.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int,
                ): RecyclerView.ViewHolder {
                    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
                    return object : RecyclerView.ViewHolder(view) {}
                }

                override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

                    val textLabel = holder.itemView.findViewById<TextView>(R.id.itemLabel)
                    val textDescription = holder.itemView.findViewById<TextView>(R.id.itemDescription)
                    val checkDone = holder.itemView.findViewById<CheckBox>(R.id.itemCheckbox)
                    val textCategory = holder.itemView.findViewById<TextView>(R.id.itemCategory)
                    val textDate = holder.itemView.findViewById<TextView>(R.id.itemDate)
                    val corners = holder.itemView.findViewById<ConstraintLayout>(R.id.corners)

                    textLabel.setText(taskList[position].name)
                    textDescription.setText(taskList[position].description)
                    checkDone.isChecked = taskList[position].done
                    textCategory.setText(taskList[position].category)
                    textDate.setText(taskList[position].date)

                    when (taskList[position].category)
                    {
                        categories[0] -> {
                            corners.setBackgroundResource(R.drawable.shape_normal)
                            textCategory.setTextColor(resources.getColor(R.color.green))
                        }
                        categories[1] -> {
                            corners.setBackgroundResource(R.drawable.shape_important)
                            textCategory.setTextColor(resources.getColor(R.color.yellow))
                        }
                        categories[2] -> {
                            corners.setBackgroundResource(R.drawable.shape_critical)
                            textCategory.setTextColor(resources.getColor(R.color.red))
                        }
                    }

                    checkDone.setOnClickListener {
                        taskList[position].done = checkDone.isChecked

                        val item = taskList[position]
                        Thread{
                            db.getDao().updateTask(item)
                        }.start()
                    }

                }

                override fun getItemCount() = taskList.size

            }

        }

        backButton.setOnClickListener {
            val menuFragment = MenuFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, menuFragment)
                .commit()
        }

        return view
    }

}