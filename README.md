## Introduction

The goal of the project is to build a single-screen app that fetches NASA’s Astronomy Picture of the Day (APOD) and display it with some details

## Visual Preview

Landing Screen |  Select Date to display it's APOD
:-------------------------:|:-------------------------:
![Alt Text](https://github.com/neetidotexe/NASA_Photo_of_the_Day/blob/master/Landing_Screen.gif)  |  ![Alt Text](https://github.com/neetidotexe/NASA_Photo_of_the_Day/blob/master/Show_APOD_On_Date.gif)

Zoom the image |  Play option for videos
:-------------------------:|:-------------------------:
![Alt Text](https://github.com/neetidotexe/NASA_Photo_of_the_Day/blob/master/APOD_Zoom.gif)|![Alt Text](https://github.com/neetidotexe/NASA_Photo_of_the_Day/blob/master/APOD_Video_Option.gif)

## Requirements

### User flow
- User clicks the app icon
- Directly arrives at the NASA Photo of the Day Screen
- On the NASA Photo of the Day Screen the user observes : 
	* Status bar Visibile
	* App bar with Title "NASA Photo of the Day"  
	* A background screen that shows APOD image or APOD video thumbnail dependending on the media type for that day
	* A title that shows title of the image which can be multiline and thus scrollable
	* A description at the bottom that shows description for the APOD
  	* A calendar button at the top left corner to select date and show APOD for that day
	* If the currently selected day has a video then a play button appears, else if it is an image then a zoom button appears
		+ On pressing the play button a hyperlink to the youtube video is activated
		+ On pressing the zoom button photo description disappears and a X mark on Right Hand top appears. On pressing the X user goes back the view with image description


 ### Constraints
 - Minimum SDK Version is 21

## Technology

- Used Git with a commit history
- Followed standard coding guidelines 
- Implemented Unit Tests
- Used Kotlin
- Used AndroidX 
- Used MVVM arctitecture
- Used ViewModel & LiveData
- Used Retrofit
- Used RxJava
- Used Glide

## Icon Credits
- Kiranshastry at https://www.flaticon.com/authors/kiranshastry
- Pixel perfect at https://www.flaticon.com/authors/pixel-perfect
- Freepik at https://www.flaticon.com/authors/freepik
- Becris at https://www.flaticon.com/authors/becris

## Future enhancements (Skipped in the interest of time)
- Play button should be able to play the video inside the app itself
- Handle mobile rotation appropriately
- "No Internet" or "Internet interrupted" User Flow : ideally a residual image should be cached to show as default
- Time Overlap between NASA's update timezone & other timezones



