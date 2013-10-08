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
  
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle):View = {
    inflater.inflate(R.layout.about, container, false)
  }

  def dataOriginDetails : TextView = {
    getView().findViewById(R.id.about_data_origin_details) match {
      case txt: TextView => txt
      case _ => throw new ClassCastException
    }
  }

  def responsabilityDetails : TextView = {
    getView().findViewById(R.id.about_data_responsability_details) match {
      case txt: TextView => txt
      case _ => throw new ClassCastException
    }
  }

  def dataUseConditions : TextView = {
    getView().findViewById(R.id.about_data_use_conditions) match {
      case txt: TextView => txt
      case _ => throw new ClassCastException
    }
  }

  override def onStart() {
    // Activate web links
    dataOriginDetails.setMovementMethod(LinkMovementMethod.getInstance())
    responsabilityDetails.setMovementMethod(LinkMovementMethod.getInstance())
    dataUseConditions.setMovementMethod(LinkMovementMethod.getInstance())
    
    super.onStart()
  }

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
  }
}
