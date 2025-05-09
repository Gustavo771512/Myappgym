package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.ComponentActivity;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.tooling.preview.Preview;


class calendario : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )


        val spacer = TextView(this)
        spacer.text = "\n\n\n"
        layout.addView(spacer)


        val calendarView = CalendarView(this)
        calendarView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layout.addView(calendarView)


        val weeksTextView = TextView(this)
        weeksTextView.text = "Semana 1: Descarga\nSemana 2: " +
                "Carga Leve\nSemana 3: Carga Pesada\nSemana 4: Carga Leve" +
                "\nSemana 5: Carga Pesada "
        weeksTextView.textSize = 25f
        weeksTextView.setPadding(20, 50, 20, 20)
        layout.addView(weeksTextView)


        val inicioButton = Button(this)
        inicioButton.text = "Inicio"
        inicioButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        layout.addView(inicioButton)


        setContentView(layout)
    }
}


@Preview(showBackground = true)
@Composable
fun calendarioPreview() {


}
