package com.example.a32

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.*

fun String.hasLetters() = any { it.isLetter() }

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rGroup : RadioGroup = findViewById(R.id.radioGroup)
        val textInput: EditText = findViewById(R.id.editTextTextPersonName)
        val b: Button = findViewById(R.id.button)

        rGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.browser -> {
                    textInput.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_URI)
                }
                R.id.map -> {
                    textInput.setInputType(InputType.TYPE_CLASS_PHONE)
                }
                R.id.phone -> {
                    textInput.setInputType(InputType.TYPE_CLASS_PHONE)
                }
                R.id.any -> {
                    textInput.setInputType(InputType.TYPE_CLASS_TEXT)
                }
            }
        }

        b.setOnClickListener(){
            val value: String = textInput.text.toString()

            if (value.isEmpty())
                Toast.makeText(this, getString(R.string.no_text), Toast.LENGTH_SHORT).show()
            else
                when (rGroup.checkedRadioButtonId){
                    R.id.browser -> {
                        if (value.startsWith("http://", ignoreCase = true) ||
                            value.startsWith("https://", ignoreCase = true))
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(value)))
                        else
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + value)))
                    }
                    R.id.map -> {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + value)))
                    }
                    R.id.phone -> {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + value)))
                    }
                    R.id.any -> {
                        if (value.hasLetters())
                        {
                            if (value.startsWith("http://", ignoreCase = true) ||
                                value.startsWith("https://", ignoreCase = true))
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(value)))
                            else
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + value)))
                        }
                        else if( value.contains(" "))
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + value)))
                        else
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + value)))
                    }
                    else -> Toast.makeText(this, getString(R.string.no_choice), Toast.LENGTH_SHORT).show()
                }

        }
    }
}