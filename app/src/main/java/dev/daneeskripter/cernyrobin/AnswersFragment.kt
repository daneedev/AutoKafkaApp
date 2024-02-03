package dev.daneeskripter.cernyrobin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val text = view.findViewById<TextView>(R.id.testText)

        text.text = video.videoInfo.title

        return view
    }

}
