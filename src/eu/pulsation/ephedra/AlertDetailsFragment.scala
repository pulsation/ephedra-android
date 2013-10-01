package eu.pulsation.ephedra

import android.app.Fragment
import android.os.Bundle
import android.view.View
import android.util.Log
import android.widget.TextView

import android.view.LayoutInflater
import android.view.ViewGroup

class AlertDetailsFragment extends Fragment 
{
  final private val TAG="eu.pulsation.ephedra.AlertDetailsFragment"
  
  // Needed to be converted in a Runnable called by runOnUiThread()
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle):View = {
    inflater.inflate(R.layout.alert_details, container, false)
  }

  lazy val activity = this.getActivity()
  lazy val detailsText : TextView = activity.findViewById(R.id.details_b) match {
    case txt: TextView => txt
    case _ => throw new ClassCastException
  }

/*
  override def onStart() {
    detailsText.setText("test")
  }
*/

  /** Called when the activity is first created. */
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)

//   val detailsText = activity.findViewById(R.id.details_a) 

  }
}
