package com.example.assingment1gymexcercise

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var exerciseSpinner: Spinner
    private lateinit var recommendButton: Button
    private lateinit var youtubeButton: Button
    private lateinit var recommendationsHeading: TextView
    private lateinit var recommendationsTable: TableLayout
    private var isExerciseSelected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exerciseSpinner = findViewById(R.id.exerciseSpinner)
        recommendButton = findViewById(R.id.recommendButton)
        youtubeButton = findViewById(R.id.youtubeButton)
        recommendationsHeading = findViewById(R.id.recommendationsHeading)
        recommendationsTable = findViewById(R.id.recommendationsTable)

        // Set up the spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.excersice_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            exerciseSpinner.adapter = adapter
        }

        exerciseSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                isExerciseSelected = position != AdapterView.INVALID_POSITION
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                isExerciseSelected = false
            }
        }

        recommendButton.setOnClickListener {
            if (!isExerciseSelected) {
                recommendationsHeading.text = "Select an Exercise Type"
                recommendationsTable.removeAllViews()
            } else {
                val selectedExercise = exerciseSpinner.selectedItem.toString()
                val recommendations = getRecommendations(selectedExercise)
                displayRecommendations(recommendations)
            }
        }

        youtubeButton.setOnClickListener {
            val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=PY6DrbX4W4o"))
            startActivity(youtubeIntent)
        }
    }

    private fun getRecommendations(exercise: String): List<String> {
        return when (exercise) {
            "Cardio Exercises" -> listOf(
                "Running (treadmill)",
                "Cycling (stationary bike)",
                "Elliptical training",
                "Rowing",
                "Jump rope",
                "Stair climbing"
            )
            "Strength Training Exercises" -> listOf(
                "Bench Press",
                "Push-ups",
                "Dumbbell Press",
                "Shoulder Press",
                "Bicep Curls",
                "Tricep Extensions",
                "Pull-ups",
                "Rows (barbell or dumbbell)",
                "Squats (barbell or bodyweight)",
                "Lunges",
                "Deadlifts",
                "Leg Press",
                "Calf Raises",
                "Planks",
                "Sit-ups",
                "Russian Twists",
                "Leg Raises",
                "Bicycle Crunches"
            )
            "Flexibility and Stretching Exercises" -> listOf(
                "Static stretches (e.g., hamstring stretch, quad stretch)",
                "Dynamic stretches (e.g., leg swings, arm circles)",
                "Yoga poses (e.g., Downward Dog, Cobra)"
            )
            "Functional Training Exercises" -> listOf(
                "Kettlebell swings",
                "Medicine ball slams",
                "Battle ropes",
                "Box jumps",
                "TRX suspension training"
            )
            "Compound Exercises" -> listOf(
                "Squat to Press",
                "Deadlift to Row",
                "Burpees",
                "Clean and Press"
            )
            "Isolation Exercises" -> listOf(
                "Leg Extension",
                "Leg Curl",
                "Bicep Curl",
                "Tricep Pushdown",
                "Lateral Raise"
            )
            else -> emptyList()
        }
    }

    private fun displayRecommendations(recommendations: List<String>) {
        // Clear previous recommendations
        recommendationsTable.removeAllViews()

        if (recommendations.isEmpty()) {
            recommendationsHeading.text = "No recommendations available"
            return
        }

        recommendationsHeading.text = "Recommendations"

        // Add table header
        val header = TableRow(this)
        val exerciseHeader = TextView(this).apply {
            text = "Exercise"
            setTypeface(null, android.graphics.Typeface.BOLD)
            setPadding(8, 8, 8, 8)
        }
        header.addView(exerciseHeader)
        recommendationsTable.addView(header)

        // Add exercise recommendations
        for (exercise in recommendations) {
            val row = TableRow(this)

            val exerciseTextView = TextView(this).apply {
                text = exercise
                setPadding(8, 8, 8, 8)
            }

            row.addView(exerciseTextView)
            recommendationsTable.addView(row)
        }
    }
}
