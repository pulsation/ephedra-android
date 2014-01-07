# Food alerts

A minimalistic Android application that notifies food alerts from [http://alimentation.gouv.fr](http://alimentation.gouv.fr)'s RSS feed, and can display them as a list. Available on the French [Google Play Store](https://play.google.com/store/apps/details?id=eu.pulsation.ephedra).

## How to build

First, you have to configure the android platform target:

	$ android update project -p . -t <target>

Then, you can build the project:

	$ sbt android:package

Optionally, you could generate an IntelliJ project for these sources:

	$sbt gen-idea