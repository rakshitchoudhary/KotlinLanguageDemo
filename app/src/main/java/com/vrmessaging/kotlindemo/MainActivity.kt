package com.vrmessaging.kotlindemo

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var btn_eng : Button
    lateinit var btn_frn : Button
    lateinit var txt_lang : TextView
    var lang = "en"
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_eng = findViewById(R.id.btn_eng)
        btn_frn = findViewById(R.id.btn_frn)
        txt_lang = findViewById(R.id.txt_lang)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        btn_eng.setOnClickListener(this)
        btn_frn.setOnClickListener(this)
    }

    override fun attachBaseContext(newBase: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(newBase)
        super.attachBaseContext(ContextWrapper(sharedPreferences.getString("SELECTED_LANGUAGE", "en")?.let {
            newBase.setAppLocale(it)
        }))
        Log.e("Language", "Changed")
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_eng->{
                val sharedPreferencesEditor = sharedPreferences.edit()
                lang = "en"
                sharedPreferencesEditor?.putString("SELECTED_LANGUAGE", lang)
                sharedPreferencesEditor.putBoolean("LANGUAGE_IS_SELECTED", true)
                sharedPreferencesEditor?.apply()
                finish()
                startActivity(intent)

            }
            R.id.btn_frn->{
                val sharedPreferencesEditor = sharedPreferences.edit()
                lang = "fr"
                sharedPreferencesEditor?.putString("SELECTED_LANGUAGE", lang)
                sharedPreferencesEditor.putBoolean("LANGUAGE_IS_SELECTED", true)
                sharedPreferencesEditor?.apply()
                finish()
                startActivity(intent)
            }
        }
    }

    fun Context.setAppLocale(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        return createConfigurationContext(config)
    }
}