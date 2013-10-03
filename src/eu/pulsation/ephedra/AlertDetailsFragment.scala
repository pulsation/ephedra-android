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

class AlertDetailsFragment extends Fragment {

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

  def webLinkButton : Button = {
    view.findViewById(R.id.web_link_button) match {
      case but : Button => but
      case _ => throw new ClassCastException
    }
  }
 
  override def onStart() {
    val args = getArguments()

    Log.v(TAG, "Content:")
    Log.v(TAG, args.getString("content"))

    titleText.setText(args.getString("title"))

    // Remove images as they won't be rendered.
    val pattern = "<img.*>".r
    val htmlString = pattern replaceAllIn(args.getString("content"), "")
    
    detailsText.setText(Html.fromHtml(htmlString))
    detailsText.setMovementMethod(LinkMovementMethod.getInstance())
    webLinkButton.setOnClickListener(new View.OnClickListener () {
      def onClick(v: View) {
        val browserIntent : Intent = new Intent(Intent.ACTION_VIEW, Uri.parse(args.getString("link")))
        startActivity(browserIntent)
      }
    })

    super.onStart()
  }

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
  }
}
