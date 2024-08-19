# The Challenge:

The challenge is to create a simple Android app that exercises a REST-ful API. The API endpoint `https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json?api-key=KoRB4K5LRHygfjCL2AH6iQ7NeUqDAGAB&offset=0` returns a JSON object which is a list of different books published by the New York Times. 

Using this endpoint, show a list of these items, with each row displaying at least the following data:

- Image
- Title
- Description 

### Technical Instructions:
- MVP architecture
- Demonstrate use of Dagger, Retrofit and Glide (for the images)
- Feel free to make any assumptions you want along the way or use any third party libraries as needed and document why you used them.

### General Instructions:
- This isn't a visual design exercise. For example, if you set random background colors to clearly differentiate the views when debugging, pick Comic Sans or Papyrus, we won't hold that against you. Well, maybe a little bit if you use Comic Sans :)
- This is also most of the code you'll be showing us – don't understimate the difficulty of the task, or the importance of this exercise in our process, and rush your PR. Put up your best professional game.
- This isn't just about handling the happy path. Think slow network (or no network at all), supporting different device sizes, ease of build and run of the project. If we can't check out and click the run button in Android Studio, you're off to a bad start (we've had PRs without a graddle for instance).
- Explanations on any choice you've made are welcome.
- We appreciate there's a lot that is asked in this exercise. If you need more time, feel free to ask. If you need to de-prioritize something, apply the same judgement you would on a professional project, argument your decision. 

Bonus Points:
  - Unit tests


### -------------------------------- Solution --------------------------------

## Build tools & versions used

- **Kotlin:** 2.0.0
- **Java:** VERSION_1_8
- **Gradle:** 8.7
- **Android Studio Koala Feature Drop | 2024.1.2 Beta 1**
- **Hilt for dependency injection**
- **Retrofit for network calls**
- **Moshi for JSON serialization**
- **Glide for Image Loading**
- **Mockito and mockk for Unit Testing**

## Steps to run the app

1. Open the project in Latest Android Studio.
2. Ensure that you have the correct Kotlin, Java and Gradle version installed.
3. Sync the project with Gradle files.
4. Run the app on an emulator or a physical device.

## What areas of the app did you focus on?

- Focused on building the best user experience for customers, using modern MVVM + Clean Architecture.
- Handled all possible states for a page, such as Loading, Empty, Error, and Success.
- Added support for Dark Mode, Do check this out. It looks amazing.
- Added support Tab / Landscape Mode.
- Handled configuration changes by saving data in the ViewModel.
- Added test cases for core business logic and state transitions.

## What was the reason for your focus? What problems were you trying to solve?

The focus was to build a maintainable and testable codebase that can easily adapt to changes.
The aim was to solve common issues related to app architecture, such as managing UI state, handling
configuration changes, and reducing boilerplate code.
Also i focused on adding testcase to cover the core business logic and state transitions.

## How long did you spend on this project?

Approximately 4-5 Hours, including planning, development, and testing phases.

## Did you make any trade-offs for this project? What would you have done differently with more time?

Trade-offs were made in terms of UI design and animations to prioritize core functionality and app
stability. With more time, I would have focused on enhancing the UI/UX and adding more interactive
elements.

## What do you think is the weakest part of your project?

The weakest part might be the UI/UX design and providing the additional feature support like search
and filter, as more focus was placed on the backend and architecture.
Future improvements can include a more engaging user interface and smoother animations.

## Did you copy any code or dependencies? Please make sure to attribute them here!

Some boilerplate code for ViewModel and LiveData setup was adapted from the official Android documentation.

# Package Structure

The app has the following base packages:

- domain: Contains models that are used by UI.
- network: Contains services and network models.
- repository: Contains the repository.
- util: Contains utility classes.
- ui: Contains view classes along with their corresponding UseCase and ViewModel.

# Library References

- Hilt: Dependency Injection framework for Android.
  Visit https://developer.android.com/training/dependency-injection/hilt-android for more
  information.
- MVVM Architecture: A pattern for building user interfaces.
  Visit https://developer.android.com/jetpack/guide for more information.
- Coroutines: Provides a way to write asynchronous, non-blocking code.
  Visit https://developer.android.com/kotlin/coroutines for more information.
- Data Binding: Binds UI components in layouts to data sources in app using a declarative format.
  Visit https://developer.android.com/topic/libraries/data-binding for more information.

# Concept Reference Resources

- RecyclerView: A powerful widget for displaying large data sets.
  Visit https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals for
  more information.
- Repository Codelab: A tutorial that covers the use of the Repository pattern.
  Visit https://developer.android.com/codelabs/kotlin-android-training-repository for more
  information.
- Hilt Codelab: A tutorial that covers the use of the Hilt dependency injection framework.
  Visit https://developer.android.com/codelabs/android-hilt for more information.
