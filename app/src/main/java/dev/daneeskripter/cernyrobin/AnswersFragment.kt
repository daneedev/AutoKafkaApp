package dev.daneeskripter.cernyrobin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.serialization.json.Json

class AnswersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_answers, container, false)
        val videoData = arguments?.getString("video")
        val video = Json.decodeFromString(Video.serializer(), videoData ?: "")
        val title = view.findViewById<TextView>(R.id.answers_title)
        title.text = video.videoInfo.title

        val answersLayout = view.findViewById<LinearLayout>(R.id.answersLayout)
        val questions = video.videoInfo.description.replace("\r\n", "\n").split(":\n")[1].trim().split("\n")
        val answers = video.answers.replace("\r\n", "\n").trim().split("\n")
        val backButton = view.findViewById<ImageButton>(R.id.btnBack)
            for (i in 0..answers.size - 1) {
                val answerBox = layoutInflater.inflate(R.layout.answer_template, null)
                val question = answerBox.findViewById<TextView>(R.id.questionText)
                val answer = answerBox.findViewById<TextView>(R.id.answerText)
                if (i > 9) {
                    question.text = "Chyba formatování"
                } else {
                    question.text = questions[i]
                }
                answer.text = answers[i]
                answersLayout.addView(answerBox)
            }
        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, HomeFragment())
                .addToBackStack(null)
                .commit()
        }
        return view
    }

}
