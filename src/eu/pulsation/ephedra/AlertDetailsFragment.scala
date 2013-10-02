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
  lazy val view = this.getView()

  lazy val detailsText : TextView = { 
    view.findViewById(R.id.details_b) match {
      case txt: TextView => txt
      case _ => throw new ClassCastException
    }
  }

  override def onStart() {
    super.onStart()

    val args = getArguments()
    Log.v(TAG, "onStart() called. Title:" + args.getString("title"))
    Log.v(TAG, args.getString("description"))
    detailsText.setText(args.getString("description"))
  }


  /** Called when the activity is first created. */
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
  }
}
