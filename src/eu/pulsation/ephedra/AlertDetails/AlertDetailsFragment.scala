/**
 *  Ephedra Food Alerts
 *  Copyright (C) 2013-2014 Philippe Sam-Long aka pulsation
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.pulsation.ephedra

import android.app.Fragment
import android.os.Bundle
import android.view.View
import android.util.Log
import android.widget.{TextView, Button}
import android.text.Html
import android.text.method.LinkMovementMethod
import android.net.Uri

import android.content.Intent
import android.view.{LayoutInflater, ViewGroup}

class AlertDetailsFragment extends Fragment {

  final private val TAG="eu.pulsation.ephedra.AlertDetailsFragment"
  
  // Needed to be converted in a Runnable called by runOnUiThread()
  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle):View = {
    inflater.inflate(R.layout.alert_details, container, false)
  }

  def view = getView()

  def detailsText : TextView = {
    view.findViewById(R.id.alert_details_text) match {
      case txt: TextView => txt
      case _ => throw new ClassCastException
    }
  }

  def titleText : TextView = {
    view.findViewById(R.id.alert_details_title) match {
      case txt : TextView => txt
      case _ => throw new ClassCastException
    }
  }

  def webLinkButton : Button = {
    view.findViewById(R.id.alert_details_web_button) match {
      case but : Button => but
      case _ => throw new ClassCastException
    }
  }
 
  def shareLinkButton : Button = {
    view.findViewById(R.id.alert_details_share_button) match {
      case but : Button => but
      case _ => throw new ClassCastException
    }
  }

  override def onStart() {
    val args = getArguments()

    // Remove images as they won't be rendered.
    val pattern = "<img.*>".r
    val htmlString = pattern replaceAllIn(args.getString("content"), "")
    
    detailsText.setText(Html.fromHtml(htmlString))
    detailsText.setMovementMethod(LinkMovementMethod.getInstance())
    titleText.setText(args.getString("title"))

    webLinkButton.setOnClickListener(new View.OnClickListener () {
      def onClick(v: View) {
        val browserIntent : Intent = new Intent(Intent.ACTION_VIEW, Uri.parse(args.getString("link")))
        startActivity(browserIntent)
      }
    })

    shareLinkButton.setOnClickListener(new View.OnClickListener() {
      def onClick(v: View) {
        val shareIntent : Intent = new Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
        shareIntent.putExtra(Intent.EXTRA_TEXT, args.getString("title") + " - " + args.getString("link"))
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, args.getString("title"))
        startActivity(Intent.createChooser(shareIntent, "Share"))
      }
    })

    super.onStart()
  }

  override def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
  }
}
