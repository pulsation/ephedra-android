# Food alerts

A minimalistic Android application that notifies food alerts from [http://alimentation.gouv.fr](http://alimentation.gouv.fr)'s RSS feed, and can display them as a list.

## How to build

First, you have to configure the android platform target:

	$ android update project -p . -t <target>

Then, you can build the project:

	$ sbt android:package

Optionally, you could generate an IntelliJ project for these sources:

	$sbt gen-idea