package com.example.zd2_v4_karamov

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
                taskList.add(Task(it.id, label = it.label, description = it.description, done = it.done))
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

                    textLabel.setText(taskList[position].label)
                    textDescription.setText(taskList[position].description)
                    checkDone.isChecked = taskList[position].done

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