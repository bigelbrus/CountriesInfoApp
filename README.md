# CountriesInfoApp
Application for viewing information about all countries.

## Features
The app uses a **Model-View-ViewModel (MVVM)** architecture for the presentation layer. For each activity, a corresponding VievModel was created for storing data and loading it from the `DataRepository`.<br>
The application uses **Retrofit** to work with RESTFul API.<br>Data is also cached to the local database using **Realm**.
