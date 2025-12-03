package com.example.zd2_v4_karamov

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText


class FormFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_form, container, false)

        val saveButton = view.findViewById<Button>(R.id.savetaskButton)
        val labelText = view.findViewById<EditText>(R.id.taskLabel)
        val descriptionText = view.findViewById<EditText>(R.id.taskDescription)

        saveButton.setOnClickListener {

            if (!labelText.text.toString().isNullOrEmpty())
            {

                val item = Task(null, labelText.text.toString(), descriptionText.text.toString(), false)

                val db = MainDB.getDb(this.requireContext())
                Thread{
                    db.getDao().insertItem(item)
                }.start()

                val menuFragment = MenuFragment()

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, menuFragment)
                    .commit()
            }

        }

        return view
    }
}