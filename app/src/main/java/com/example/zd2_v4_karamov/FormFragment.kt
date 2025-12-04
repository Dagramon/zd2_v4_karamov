package com.example.zd2_v4_karamov

import android.content.Context
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

val categories = arrayOf("Обычная", "Важная", "Срочная")

class FormFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_form, container, false)

        val spinner = view.findViewById<Spinner>(R.id.categorySpinner)
        val backButton = view.findViewById<Button>(R.id.backButton2)
        val saveButton = view.findViewById<Button>(R.id.savetaskButton)
        val labelText = view.findViewById<EditText>(R.id.taskLabel)
        val descriptionText = view.findViewById<EditText>(R.id.taskDescription)
        val datePicker = view.findViewById<DatePicker>(R.id.datePicker)

        val adapter = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categories)

        spinner.adapter = adapter

        datePicker.minDate = Calendar.getInstance().timeInMillis
        datePicker.maxDate = Calendar.getInstance().apply {
            add(Calendar.YEAR, 1)
        }.timeInMillis



        backButton.setOnClickListener {
            val menuFragment = MenuFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, menuFragment)
                .commit()
        }

        saveButton.setOnClickListener {

            if (!labelText.text.toString().isNullOrEmpty())
            {

                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

                val date = Calendar.getInstance().apply {
                    set(
                        datePicker.year,
                        datePicker.month,
                        datePicker.dayOfMonth
                    )
                }

                val item = Task(null, labelText.text.toString(), descriptionText.text.toString(),dateFormat.format(date.time), spinner.selectedItem.toString(), false)

                val db = MainDB.getDb(this.requireContext())
                Thread{
                    if (!db.getDao().taskExists(item.name, item.date))
                    {
                        db.getDao().insertItem(item)
                    }
                }.start()

                val menuFragment = MenuFragment()

                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, menuFragment)
                    .commit()
            }
            else
            {
                Snackbar.make(saveButton, "Задача не введена", Snackbar.LENGTH_SHORT).show()
            }

        }

        return view
    }
}