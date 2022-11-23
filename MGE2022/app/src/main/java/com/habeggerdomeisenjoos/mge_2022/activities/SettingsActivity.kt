package com.habeggerdomeisenjoos.mge_2022.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.widget.addTextChangedListener
import com.habeggerdomeisenjoos.mge_2022.R
import com.habeggerdomeisenjoos.mge_2022.SettingsHandler
import com.habeggerdomeisenjoos.mge_2022.model.AppRepository

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        SettingsHandler.getInstance().load(this)

        initDarkModeSwitch()
        initDistanceEditText()
        initUnitsSpinner()
    }

    override fun onPause() {
        super.onPause()
        SettingsHandler.getInstance().save(this)
    }

    private fun initDarkModeSwitch() {
        val darkModeSwitch = findViewById<SwitchCompat>(R.id.switch_dark_mode)
        darkModeSwitch.isChecked = SettingsHandler.getInstance().darkMode
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            SettingsHandler.getInstance().darkMode = isChecked
        }
    }

    private fun initUnitsSpinner() {
        val unitsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SettingsHandler.getInstance().getUnits())
        unitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val unitsSpinner = findViewById<Spinner>(R.id.spinner_units)
        unitsSpinner.adapter = unitsAdapter

        val selectedItemIndex = unitsAdapter.getPosition(SettingsHandler.getInstance().unit)
        unitsSpinner.setSelection(selectedItemIndex)

        unitsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long){
                val selectedItem = parent?.getItemAtPosition(position).toString()
                if (selectedItem in SettingsHandler.getInstance().getUnits()) {
                    SettingsHandler.getInstance().unit = selectedItem
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun initDistanceEditText() {
        val distanceEditText = findViewById<EditText>(R.id.editTextDistance)
        distanceEditText.setText(SettingsHandler.getInstance().radius.toString())
        distanceEditText.addTextChangedListener { control ->
            val distance = control.toString().toInt()
            SettingsHandler.getInstance().radius = distance
        }
    }
}