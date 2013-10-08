package eu.pulsation.ephedra

import android.app.Fragment
import android.os.Bundle
import android.view.View
import android.util.Log
import android.widget.{TextView, Button}
import android.webkit.WebView
import android.text.Html
import android.text.method.LinkMovementMethod
import android.net.Uri

import android.content.Intent
import android.view.{LayoutInflater, ViewGroup}

import scala.util.matching.Regex

class AboutFragment extends Fragment {

  final private val TAG="eu.pulsation.ephedra.AboutFragment"
  
  // Needed to be converted in a Runnable called by runOnUiThread()
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle):View = {
    inflater.inflate(R.layout.about, container, false)
  }

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
  }
}
