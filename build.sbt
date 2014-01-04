import android.Keys._                                                                                                                                                                                                                         
android.Plugin.androidBuild

name := "ephedra-android"

libraryDependencies ++= Seq(
  "com.netflix.rxjava" % "rxjava-scala" % "0.15.1"
)

proguardOptions in Android ++= Seq(
  "-dontwarn rx.operators.OperationParallelMergeTest",
  "-dontwarn rx.subjects.UnsubscribeTester",
  "-keep class android.widget.TextView"
)

scalacOptions ++= Seq(
  "-feature"
)
