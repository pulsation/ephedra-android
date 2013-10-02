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
    Log.v(TAG, "View created")
    inflater.inflate(R.layout.alert_details, container, false)
  }

  def view = getView()

  def detailsText : TextView = {
    view.findViewById(R.id.details) match {
      case txt: TextView => txt
      case _ => throw new ClassCastException
    }
  }

  def titleText : TextView = {
    view.findViewById(R.id.title) match {
      case txt : TextView => txt
      case _ => throw new ClassCastException
    }
  }
 
  override def onStart() {
    val args = getArguments()

    Log.v(TAG, "Content:")
    Log.v(TAG, args.getString("content"))

    titleText.setText(args.getString("title"))
    detailsText.setText(args.getString("content"))

    super.onStart()
  }
  

  /** Called when the activity is first created. */
  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
  }
}
