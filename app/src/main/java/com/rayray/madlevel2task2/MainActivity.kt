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
    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)

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
        this.questions.add(Question("A 'val' and 'var' are the same.", ItemTouchHelper.RIGHT))
        this.questions.add(Question("MAD grants 12 ECTS", ItemTouchHelper.LEFT))
        this.questions.add(Question("A Unit in Kotlin corresponds to a void in Java.", ItemTouchHelper.RIGHT))
        this.questions.add(Question("In Kotlin 'when' replaces the 'switch' operator in Java.", ItemTouchHelper.RIGHT))

        binding.rvQuiz.layoutManager = LinearLayoutManager(
            this@MainActivity, RecyclerView.VERTICAL, false
        )
        binding.rvQuiz.adapter = questionAdapter
        binding.rvQuiz.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        createItemTouchHelper().attachToRecyclerView(rvQuiz)
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
                val position = viewHolder.adapterPosition
                checkAnswer(position, direction)
            }
        }
        return ItemTouchHelper(callback)
    }

    /**
     * delete an item
     */
    private fun deleteItem(p : Int){
        val POSITION = p
        questions.removeAt(POSITION)
        questionAdapter.notifyDataSetChanged()
    }

    /**
     * checks answer. If answer is correct, the selected item will be deleted. If not, then
     * a toast message will show.
     */
    private fun checkAnswer(position: Int, swipeDirection: Int) {
        //todo dit gebruiken
        if (swipeDirection == questions.get(position).correctAnswer){

            deleteItem(position)
        }else{
            questionAdapter.notifyDataSetChanged()
            Toast.makeText(applicationContext, "Wrong answer, try again", Toast.LENGTH_LONG)
                .show()
        }
    }

}