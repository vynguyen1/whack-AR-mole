package com.example.mosy.objects

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mosy.R
import com.example.mosy.helpers.GameScoreDatabaseHelper

object DialogBoxHelper {

    @SuppressLint("InflateParams")
    fun openDialogBox(context: Context, dbHelper: GameScoreDatabaseHelper) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.fragment_dialog_high_score, null)
        val dialogScore = view.findViewById<TextView>(R.id.dialogScore)
        dialogScore.text = Player.score.toString()
        val editTextName = view.findViewById<EditText>(R.id.nameEntered)
        val submitButton = view.findViewById<Button>(R.id.enterNameButton)

        builder.setView(view)
        val dialog: AlertDialog = builder.create()

        submitButton.setOnClickListener {
            if(editTextName.text.isEmpty()) {
                Toast.makeText(context, "Bitte einen Namen eintragen",
                    Toast.LENGTH_SHORT).show()
            } else {
                Player.name = editTextName.text.toString()
                dbHelper.insertScore(dbHelper.writableDatabase, Player.name, Player.score)
                Toast.makeText(context, "Name & Score sind eingetragen",
                    Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
        dialog.show()
    }
}