package dev.daneeskripter.cernyrobin

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString

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


        val linearLayout : LinearLayout = view.findViewById(R.id.videosLayout)

            GlobalScope.launch(Dispatchers.Main) {
                val result = APIMethods().getRequest(/*"https://api.cernyrob.in/kafka/list"*/ "https://api.cernyrob.in/kafka/list")
                try {
                    if (result.status.value != 200) {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setTitle("Chyba serveru")
                            .setMessage("Nelze se spojit se serverem")
                            .setCancelable(false)
                            .setPositiveButton("vitek neco dosral tyvole") { _: DialogInterface, _: Int ->
                            }
                            .show()
                    } else {
                        val jsonResult = Json {
                            ignoreUnknownKeys = true
                        }.decodeFromString<AutoKafka>(result.bodyAsText())
                        jsonResult.list.forEach {
                            val videoBox: View = layoutInflater.inflate(R.layout.video_template, null)
                            val title = videoBox.findViewById<TextView>(R.id.videoTitle)
                            val image = videoBox.findViewById<ImageView>(R.id.videoImage)
                            title.text = it.value.videoInfo.title
                            Glide.with(this@HomeFragment)
                                .load("https://img.youtube.com/vi/${it.value.videoUrl.split("=")[1]}/maxresdefault.jpg")
                                .into(image)
                            val videoData = Json.encodeToString(Video.serializer(), it.value)
                            videoBox.setOnClickListener {
                                val args = Bundle()
                                args.putString("video", videoData)
                                val answersFragment = AnswersFragment()
                                answersFragment.arguments = args

                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.frameLayout, answersFragment)
                                    .addToBackStack(null)
                                    .commit()

                            }
                            linearLayout.addView(videoBox)
                        }
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