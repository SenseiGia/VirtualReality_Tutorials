# Virtual Reality Tutorials (Being Updated for Processing)
These tutorials are designed as set of many tutorials which will build up your ability and confidence to create a Virtual Reality Application.

## Resources Needed:
The tutorials included in this repository were designed and tested with the following:

### Hardware
1. Android Phone or Device with Android 4.4 'KitKat' (API level 19 or higher)
2. Proper cable to connect Phone to computer
3. Google Cardboard (Both Version 1 and 2 work) - Not Essential (Use Monoscopic View)

### Software
1. Processing (Current Version: 3.5.3)
2. Android SDK 7.1.1 'Nougat' (API level 25 or higher)

## Install Everything needed on you System

###  Downloading and Installing Processing
Start off by downloading the latest version of [Processing](https://processing.org/download/) for your operating system.

Once the file has been downloaded, extract the zipped folder to a convienent location as the extracted folder will contain all the files, including the executable, used to run Processing. Inside the extracted folder, you may create a shortcut to Prococessing on your desktop.

**Right click "processing.exe" -> Mouse over "Send to" -> Click "Desktop(Create Shortcut)"**

### Put Processing into Android Virtual Reality Mode
You should now be able to open processing from the extracted folder. The program will bring up two windows. Feel free to go through the the patch notes brought up by the Welcome Window. Once you are ready to progress, close the window by pressing 'X' or "Get Started" button.

The First thing you will want to do is download Android Mode for processing, which will allows to properly configure Processing for creating Android Applications and VR applications as well. Start off by clicking the dropdown menu next to the debug button as seen in the image below. Select "Add Mode" to view the available options and bring up the Contribution Manager.

**IMAGE HERE**

This is the Contribution Manager. From here, you can download various Modes for processing and useful Libraries for you projects. All you need is Android Mode, which should appear on the initial opening of the Contribution Manager as seen in the following image.

**IMAGE HERE**

Select Android Mode and press install. At time of tutorial creation, Android Mode is at version 4.0.4. Once Android Mode has been installed, you can proceed to switch Processing into Android Mode by clicking the dropdown again and switch it from Java to Android. The application should restart and now have Android displayed at the location of the drop down menu.

Now that Processing is in Android Mode, change the application mode from regular application to Virtual Reality application. This can be done by hovering over android in the toolbar. From this menu, select VR. Your only indication the change took place will be a checkmark will be next to VR in the menu as seen in the image below.

**IMAGE HERE**


### Update Android SDK for Processing
For the final part of the Processing configuration step, we will now want to update the Android SDK. (Software Development Kit) This will allow us to have the latest examples, development tools, and required libraries for Android development. Start of by navigating to Android in the toolbar, and reveal the drop down menu. Select SDK Updater which will bring up the SDK Updater Window. If your SDK is up to date, the update button will be grayed out. If it is out of date, press update and close once finished.

Processing has now been completely configured and we can now start configuring you Android device for communication with you computer. 

## Configure you Android device for use with Processing.

**On Phone: -> Settings -> About phone -> Android Version: #.#.#**

If the version is equal or newer than Android SDK 7.1.1 'KitKat', the device supports Virtual Reality and you can proceed with downloading the SDK for Processing. Follow the steps (Could expand on this locally rather than redirect to site) provided by Android to [enable developer options](https://developer.android.com/studio/debug/dev-options#enable) 

This will allow the Android device user to access special functions. After enabling developer options, [enable USB Debugging](https://developer.android.com/studio/debug/dev-options#debugging).

You should be able to now connect the Android device to the computer and see the device appear in the device list provided in Processing as seen in the image below.

## Time to follow the tutorials:
Tutorial 0: Object Creation **LINK**
- This tutorial walks through the basics of virtual reality development. It will cover the important sections used by processing and how to create a basic object in 3D space. We will also breifly explore the concepts of object transfomration and lighting. 

Tutorial 1: Scene Creation **LINK**
- This Tutorial is the first "real" tutorial of the set of lessons that goes into the details of object creation in a 3D space. It will cover the basic objects that can be created with Processing's API as well the ability to create custom objects. Finally it will cover how to import external .obj (Wavefront) file.  

Tutorial 2: User Interaction
- To Be Done

Tutorial 3: Adding Spatial sound relative to objects and user
- To Be Done

tutorial 4: Integrating More complex movements and interactions
- To Be Done










