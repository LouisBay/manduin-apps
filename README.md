# Mobile Development Project For Manduin Apps

## Design Prototype
Before entering the code implementation process, we designed the UI of the application using the [Figma](https://www.figma.com/) which is prototyping tools application.

Here is the [link](https://www.figma.com/file/9bvFP0ZFZMHASRDQ6ACSko/mandu.in?node-id=143%3A1764) to our design prototype.

## Code Implementation

Mandu.in Apps was developed using Native Programming Language Kotlin. We build this application to give information about Indonesian Tourism through Image Classification from Machine Learning which we process with TensorFlow Lite. We implement MVVM with Repository pattern, using LiveData to stream data from resource to UI, and using CameraX to create build in camera apps that used for detection. 

## Libraries

Here are some libraries that we use on this project.
| Library                  | Version |
| :----------------------- | :-----: |
| Coroutines               | [2.4.1](https://developer.android.com/kotlin/coroutines?gclid=CjwKCAjwkYGVBhArEiwA4sZLuJ-c3uxtAZaJLTKbnsCQgo3s83EnCxf7NwW34fdGDRYXCcZWP3XBeRoCNcwQAvD_BwE&gclsrc=aw.ds) |
| Retrofit2                | [2.9.0](https://square.github.io/retrofit/) |
| CameraX                  | [1.2.0-alpha02](https://developer.android.com/training/camerax) |
| TensorFlow Lite          | [0.1.0](https://www.tensorflow.org/lite) |
| Dagger-Hilt              | [2.41](https://dagger.dev/hilt/) |
| Room                     | [2.4.2](https://developer.android.com/jetpack/androidx/releases/room?gclid=EAIaIQobChMIofDYuu6d-AIVgX0rCh2yjAkfEAAYASAAEgKRUPD_BwE&gclsrc=aw.ds) |
| Firebase Auth            | [21.0.5](https://firebase.google.com/docs/auth/android/start) |
| Firebase Database        | [20.0.5](https://firebase.google.com/docs/database/android/start) |
| Google Maps SDK          | [18.0.2](https://developers.google.com/maps/documentation/android-sdk) |
| Fused Location Provided  | [19.0.1](https://developer.android.com/training/location) |
| Datastore                | [1.0.0](https://developer.android.com/topic/libraries/architecture/datastore) |
| Glide                    | [4.13.1](https://bumptech.github.io/glide/) |
| Shimmer                  | [0.5.0](http://facebook.github.io/shimmer-android/) |
| Lottie                   | [5.0.3](http://airbnb.io/lottie/) |
| Rounded Image            | [2.3.0](https://github.com/vinc3m1/RoundedImageView) |

## Data Source

Here are some API that we use on this application.
- <b>News API</b>
  > https://api-berita-indonesia.vercel.app/tribun/travel/
  
  API to get data about Travel News.
  <br>Credit  : [renomureza](https://github.com/renomureza/api-berita-indonesia)
  
- <b>Mandu.in API</b>
  > https://manduin-app.et.r.appspot.com/
  
  Main API for this application that created by CC Path
  <br>Credit  : [Manduin CC Path](https://github.com/LouisBay/manduin-apps/tree/cloud-computing)
  
## Download

Here is the [link](https://drive.google.com/file/d/1fDfXF_4QcsNzybaRa6arrJiawigZP7q6/view?usp=sharing) of Manduin App.

## Preview 
<p align="center">
  <a href="https://github.com/LouisBay/manduin-apps/blob/main/images/manduin_mockup.jpg">
    <img src="images/manduin_mockup.jpg">
  </a>
</p>
