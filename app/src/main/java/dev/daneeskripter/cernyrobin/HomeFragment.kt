package dev.daneeskripter.cernyrobin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsChannel
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.decodeFromStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        @Serializable
        data class VideoInfo(
            @SerialName("title") val title: String,
            @SerialName("author_name") val authorName: String,
            @SerialName("author_url") val authorUrl: String,
            @SerialName("type") val type: String,
            @SerialName("height") val height: Int,
            @SerialName("width") val width: Int,
            @SerialName("version") val version: String,
            @SerialName("provider_name") val providerName: String,
            @SerialName("provider_url") val providerUrl: String,
            @SerialName("thumbnail_height") val thumbnailHeight: Int,
            @SerialName("thumbnail_width") val thumbnailWidth: Int,
            @SerialName("thumbnail_url") val thumbnailUrl: String,
            @SerialName("html") val html: String,
            @SerialName("description") val description: String
        )

        @Serializable
        data class Video(
            @SerialName("answers") val answers: String,
            @SerialName("transcript") val transcript: String,
            @SerialName("language") val language: String,
            @SerialName("video_info") val videoInfo: VideoInfo,
            @SerialName("video_url") val videoUrl: String

        )

        @Serializable
        data class AutoKafka(
            @SerialName("code") val code: Int,
            @SerialName("message") val message: String,
            @SerialName("list") val list: Map<String, Video>
        )

        val linearLayout : LinearLayout = view.findViewById(R.id.videosLayout)
      GlobalScope.launch(Dispatchers.Main) {
            val result = APIMethods().getRequest("https://api.cernyrob.in/kafka/list").bodyAsText()
          try {
              val jsonResult = Json { ignoreUnknownKeys = true }.decodeFromString<AutoKafka>(result)
              jsonResult.list.forEach {
                  val videoBox: View = layoutInflater.inflate(R.layout.video_template, null)
                  val title = videoBox.findViewById<TextView>(R.id.videoTitle)
                  val answers = videoBox.findViewById<TextView>(R.id.videoAnswers)
                  title.text = it.value.videoInfo.title
                  answers.text = it.value.answers

                  linearLayout.addView(videoBox)
              }
          } catch (e: Exception) {
              e.printStackTrace()
          }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}