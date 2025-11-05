package com.example.helloflow.ui

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Displays a short Toast message from a Fragment.
 *
 * @param message The text to show.
 */
fun Fragment.showShortToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

/**
 * Displays a long Toast message from a Fragment.
 *
 * @param message The text to show.
 */
fun Fragment.showLongToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

/**
 * Displays a short Toast message from an Activity.
 *
 * @param message The text to show.
 */
fun Activity.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/**
 * Displays a long Toast message from an Activity.
 *
 * @param message The text to show.
 */
fun Activity.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}