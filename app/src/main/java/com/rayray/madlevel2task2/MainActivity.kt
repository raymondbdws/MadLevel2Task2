package com.rayray.madlevel2task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rayray.madlevel2task2.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Raymond Chang
 *
 */
class MainActivity : AppCompatActivity() {

    //alternative = 2d array
    private val quizzes = arrayListOf<Quiz>()
    private val quizAdapter = QuizAdapter(quizzes)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    /**
     * Load questions with answers in array. Answers are directions.
     * 4 is left(incorrect), 8 is right(correct).
     */
    private fun initViews() {
        val questions = arrayOf(
            arrayOf("A 'val' and 'var' are the same.", "8"),
            arrayOf("MAD grants 12 ECTS", "4"),
            arrayOf("A Unit in Kotlin corresponds to a void in Java.", "8"),
            arrayOf("In Kotlin 'when' replaces the 'switch' operator in Java.", "8")
        )

        addQuiz(questions)
        binding.rvQuiz.layoutManager = LinearLayoutManager(
            this@MainActivity, RecyclerView.VERTICAL, false
        )
        binding.rvQuiz.adapter = quizAdapter
        binding.rvQuiz.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        createItemTouchHelper().attachToRecyclerView(rvQuiz)
    }

    //notifydatasetchanged is important. Needs to be notified, when the data has been changed. It
    //will refresh itself
    /**
     * Add quiz
     * 4 = left | incorrect, 8 = right | correct
     */
    private fun addQuiz(questions: Array<Array<String>>) {

        for ((index, value) in questions.withIndex()){
            quizzes.add(Quiz(value[0], value[1]))
        }
    }

    //4 = left | incorrect, 8 = right | correct
    /**
     * When user swipes, then it received a direction in fun onSwipe.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val POSITION = viewHolder.adapterPosition
                checkAnswer(POSITION, direction, quizzes[POSITION].correctAnswer)
            }
        }
        return ItemTouchHelper(callback)
    }

    /**
     * delete an item
     */
    private fun deleteItem(p : Int){
        val POSITION = p
        quizzes.removeAt(POSITION)
        quizAdapter.notifyDataSetChanged()
    }

    /**
     * checks answer. If answer is correct, the selected item will be deleted. If not, then
     * a toast message will show.
     */
    private fun checkAnswer(position: Int, swipeDirection: Int, correctAnswer: String) {
        if (swipeDirection.toString().equals(correctAnswer)){
            deleteItem(position)
        }else{
            quizAdapter.notifyDataSetChanged()
            Toast.makeText(applicationContext, "Wrong answer, try again", Toast.LENGTH_LONG)
                .show()
        }
    }

}