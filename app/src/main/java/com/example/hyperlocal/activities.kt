package com.example.hyperlocal

import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

fun AppCompatActivity.snackbar(message : String) {
    Snackbar.make(this.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
}

fun AppCompatActivity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.finishAndStart(activity : Class<*>) {
    startActivity(Intent(this, activity))
    finish()
}