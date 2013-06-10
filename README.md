gopro-download
==============

A simple (one class) java app to download all files from your [wifi-enabled GoPro](http://gopro.com/hd-hero3-cameras) camera.
It does a "does the file exist" check to skip previously downloaded files, so if you want to re-download, move the previous images and videos to another folder.
All files are dropped off in your UserHome/Pictures/gopro folder, which for most OSs, is the regular folder for pictures and videos.

Usage:

1. Turn on your camera's wifi
2. Connect your PC (any OS) to the camera's wifi network.  If you don't know the password, try "goprohero"
3. Make sure you are connected by trying to open http://10.5.5.9:8080/
4. If you are confident, run the jar by double-clicking.  If first time, you may want to run from the command line to watch the output:
4.1. cd gopro-download/dist; java -jar gopro-download.jar
