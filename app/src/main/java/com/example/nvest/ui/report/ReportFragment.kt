package com.example.nvest.ui.report

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.nvest.R

class ReportFragment : Fragment() {
    private lateinit var editTextSearch: EditText
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_report, container, false)

        /* Declare All UI */
        editTextSearch = view.findViewById(R.id.editTextSearch)
        /* End Declare All UI */

        /* Text Event */
        editTextSearch.addTextChangedListener (object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}
        })
        /* End Text Event*/

        /* Clicked Event */
        /* End Clicked Event*/

        // Inflate the layout for this fragment
        return view
    }
}