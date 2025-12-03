package com.example.zd2_v4_karamov

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout

class MenuFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        val button1 = view.findViewById<ConstraintLayout>(R.id.button1)
        val button2 = view.findViewById<ConstraintLayout>(R.id.button2)

        button1.setOnClickListener {
            var tasksFragment = TasksFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, tasksFragment)
                .commit()
        }
        button2.setOnClickListener {
            var formFragment = FormFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, formFragment)
                .commit()
        }

        return view
    }
}