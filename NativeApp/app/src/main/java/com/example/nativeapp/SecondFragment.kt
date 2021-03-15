package com.example.nativeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.view.FlutterMain

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {


    //private var flutterView: FlutterView? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

     //   val flutterView : View = createView(activity, lifecycle, "")

        if (flutterEngine == null) {
            //println(args)
            flutterEngine = FlutterEngine(context!!, null)
            flutterEngine!!.dartExecutor.executeDartEntrypoint(
                // set which of dart methode will be used here
                DartExecutor.DartEntrypoint(FlutterMain.findAppBundlePath(), "main")
                // to set here the main methode you can use this function to do this
                // inteade of DartEntrypoint(FlutterMain.findAppBundlePath(),"myMainDartMethod")
                // write this mdethode DartEntrypoint.createDefault()
            )
        }

        //setContentView(R.layout.flutter_view_layout) // <- the layout that i use to show the flutterActivity inside it         flutterView = findViewById(R.id.flutter_view)


        view.findViewById<FlutterView>(R.id.flutter_view)!!.attachToFlutterEngine(flutterEngine!!)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    // hire will be tested if the channel lifecycle is resumed
    override fun onResume() {
        super.onResume()
        flutterEngine!!.lifecycleChannel.appIsResumed()
    }     // hire will be tested if the channel lifecycle is paused
    override fun onPause() {
        super.onPause()
        flutterEngine!!.lifecycleChannel.appIsInactive()
    }     // hire will be tested if the channel lifecycle is stoped
    override fun onStop() {
        super.onStop()
        flutterEngine!!.lifecycleChannel.appIsPaused()
    }     // hire will be tested if the channel lifecycle is destroied

    override fun onDestroy() {
        //flutterView!!.detachFromFlutterEngine()
        super.onDestroy()
    }

    companion object {
        //
        private var flutterEngine: FlutterEngine? = null
    }
}