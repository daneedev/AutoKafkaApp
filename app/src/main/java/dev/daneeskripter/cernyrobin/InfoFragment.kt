package dev.daneeskripter.cernyrobin

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_info, container, false)

        // Import the components
        val webButton = view.findViewById<Button>(R.id.webVersionBtn)
        val versionButton = view.findViewById<Button>(R.id.versionButton)
        val versionText = view.findViewById<TextView>(R.id.versionText)

        webButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://cernyrob.in/kafka"))
            startActivity(intent)
        }
        // CHECK FOR UPDATES
       val currentVersion : String = context?.packageManager?.getPackageInfo("dev.daneeskripter.cernyrobin", 0)!!.versionName
        GlobalScope.launch(Dispatchers.Main) {
           val latestVersion = APIMethods().getRequest("https://version.daneeskripter.dev/autokafka/version.txt").bodyAsText()
            if (latestVersion == currentVersion) {
                versionButton.setBackgroundColor(Color.GREEN)
                versionButton.setText(R.string.latestversion)
            } else {
                versionButton.setBackgroundColor(Color.YELLOW)
                versionButton.setText(R.string.updateversion)
                versionButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/DaneeSkripter/AutoKafkaApp/releases"))
                    startActivity(intent)
                }
            }
        }

        // SET CURRENT VERSION
        versionText.setText("${versionText.text} ${currentVersion}")

        return view
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}