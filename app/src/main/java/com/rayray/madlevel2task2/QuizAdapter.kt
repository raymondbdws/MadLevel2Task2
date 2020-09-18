package com.rayray.madlevel2task2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rayray.madlevel2task2.databinding.ItemQuizBinding

/**
 * @author Raymond Chang
 *
 * Quiz adapter.
 */
class QuizAdapter(private val quiz: List<Quiz>) : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val BINDING = ItemQuizBinding.bind(itemView)

        fun databind(quiz: Quiz) {
            BINDING.tvQuiz.text = quiz.quizText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_quiz, parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return quiz.size
    }

    override fun onBindViewHolder(holder: QuizAdapter.ViewHolder, position: Int) {
        holder.databind(quiz[position])
    }
}