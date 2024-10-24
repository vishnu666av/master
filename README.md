> [!CAUTION]
> Under the `Keystore` folder, there is a file named `Release.jks`. Also, its credentials are
> mentioned in the app's `build.gradle` file. They exist there to help reviewers to run the `release`
> build variant, that is configured to apply `minify` with _R8-Full mode_.
>
> **They shouldn't be there in real-world projects.**

# Challenge, and how it is implemented

Author: Ahmad Daneshvar

- [Challenge, and how it is implemented](#challenge-and-how-it-is-implemented)
  - [Screenshots](#screenshots)
    - [Pixel 9](#pixel-9)
    - [Pixel fold](#pixel-fold)
    - [Videos](#videos)
  - [Dependencies](#dependencies)
  - [Dagger (DI)](#dagger-di)
    - [Graph overview](#graph-overview)
    - [Details](#details)
  - [Architecture (MVP)](#architecture-mvp)
    - [BookListView](#booklistview)
    - [BookListPresenter](#booklistpresenter)
    - [BookListUseCase](#booklistusecase)
      - [DTOs](#dtos)
  - [BookListActivity](#booklistactivity)
  - [Unit tests](#unit-tests)
  - [Preparation for release](#preparation-for-release)


## Screenshots

At the first step, let's have a brief look at the output:

### Pixel 9

| Portrait                                                                                                    | Portrait (night mode)                                                                                       | Landscape                                                                                                  | 
|-------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------|
| <image src="https://github.com/user-attachments/assets/dd4f9cd3-9a60-49f7-93e3-ef8d16f7569f" width="150" /> | <image src="https://github.com/user-attachments/assets/8b7256db-b684-452f-b9a2-69f3c7ec20a7" width="150" /> | <image src="https://github.com/user-attachments/assets/43a39c32-baf4-4caa-80a1-f4f5091cb046" width="200"/> |

### Pixel fold

| Portrait                                                                                                    | Landscape (night mode)                                                                                    | Landscape                                                                                                   | 
|-------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|
| <image src="https://github.com/user-attachments/assets/08bb3cb9-8e76-41de-a7b4-2f755131e2a9" width="150" /> | <image src="https://github.com/user-attachments/assets/31931303-5cbd-43d0-bb15-cc46fd3e7dc5" width="200"> | <image src="https://github.com/user-attachments/assets/2efdc9e1-95ea-4393-bcc4-9a16af2ec8ee" width="200" /> |

### Videos

| Auto reload on connect                                                                                      | Pixel fold adaptive layout                                                                                |
|-------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|
| <video src="https://github.com/user-attachments/assets/4db00f07-253f-4faf-8a3c-5070af2fbe24" width="150" /> | <video src="https://github.com/user-attachments/assets/0bcd195c-4d2d-4980-b51d-481f2f3c9b8e" width="250"> |

## Dependencies

Dependencies that I've used are:

1. **Dagger**: For dependency injection
2. **Gson**: For serialization
3. **Retrofit**: For "Rest" network calls
    1. **GSON converter factory**: To use GSON to manage the serialization/deserialization process
4. **Glide**: For remote image loading/caching
5. **Mokito**: For unit tests
    1. **Mokito-Kotlin**: Contains Kotlin-based helper functions
    2. **Mockito-inline**: Gives access to Mockito, to open, `final` classes
6. **Mock-Webservice**: To test API with the mock server
7. **Android core JDK desugaring**: To work with `LocalDataTime/ZonedDateTime` on the APIs less than


## Dagger (DI)

### Graph overview

This is the dagger graph overview:

<image src="https://github.com/user-attachments/assets/6d65ac5f-2979-481a-be7f-1a9e2ce59c93" />

### Details

These are modules and components that exist in the graph:

* **App component**: The main component, that is entry point for the graph. It contains other
  modules, that are singleton and exist on the entire app's lifecycle. Also, main
  `ApplicationContext` is supplied to the graph, by this component.
* **BookListComponent**: Is Subcomponent. This subcomponent injects everything that is needed for
  the main `BookListActivity`. Also, this component, supplies `BookListView` and `ActivityContext`
  to the graph. So, if any operation that needs `ActivityContext`, can use the context that is
  supplied by this component.
> [!TIP]
> This component is scoped by `@ActivityScope`. So, all objects that are provided by this component
> is identical for EACH activity, independently. For example, `BookListPresenter` is injected
> to `BookListActivity`. If two instances of this activity exist, it creates a new `Presenter` for each one.
* **use case component**: Module, provides instances of use cases. The use cases are reusable, so, they
  are existed as long as the app is alive.
* **Network Module**: Module, provides any network-related classes like `Retrofit`, `Caching`,
  `OkHttpKlient`, `NytApiService` and `ConnectivityManager`. They have existed as long as the app is
  active and alive.
* **Serialization Component**: Provides `Gson` and its serialization policies.

## Architecture (MVP)

The architecture is separated into 3 main components.

1. **BookListView**
2. **BookListPresenter**
3. **BookListUseCase**

We used these components in a way that guarantees UDF (Unidirectional-Data-Flow). Let's open them
one-by-one.

### BookListView

Interface to the UI layer. Has direct access to UI elements and bridges between the presenter and the
activity. Is implemented by the `BookListActivity`.

* **`deliverState` function**: Updates the UI, by the `UIState` that is provided by the presenter.
* **ownerLifecycleScope**: This is a computed property, that returns the activity's lifecycle scope to the
  presenter. The presenter uses this scope to execute any long-running operations. The good thing about
  this is that because it binds to the activity's lifecycle, if the activity is destroyed, in the middle of
  running operations, it cancels any active task.

### BookListPresenter

The main component that connects the domain layer to the UI layer. It contains the `UIState` and
also methods to load the books. It receives the `BookListuse case` as well to provide the data to the
UI, through itself. Because of it, it contains `map` functions to receive the `DTO` object and
converts it to the `UIState`. So, the `UIState` objects are isolated from any use case.

### BookListUseCase

Loads the books, by calling the rest API and returns to any presenter that is called it.

#### DTOs

The use case works with the `DTO` objects that belong to the domain layer. The reason is that use
cases don't know anything about the UI. Also, the structure of the UIStates can be different from
the DTOs.

## BookListActivity

The activity contains all pure Android operations and UI components. It also implements the
`BookListView` methods.

> [!IMPORTANT]
> To update the UI for according to the UIState, we don't use Fragments. The reason was that
> fragments have overhead, and is suggested to prevent using them until they are doing something
> major. For simple states like showing a list, updating the state by switching the `visibility`
> would be sufficient.

Also, we use `ConnectivityManager` in the activity, which helps to reload the data, in the case of no
internet connection, on the first try. By obtaining an internet connection, the list reloads
automatically.

## Unit tests

For unit tests, the `Mockito` is used. The main 3 components that unit tests are covering are
the presenter, use case, and API service that contain logic. For the presenter, the behavioral
test has been done, to make sure that all functions are handling all possible scenarios.

## Preparation for release

To make sure that the project is ready for publishing, decided to run the project under `Release`
build variant. The `minify` is enabled and `R8` is activated in full mode. Also, the necessary rules were added
to the `proguard-rules.pro` file to ensure everything is working, after obfuscation and
optimization.

> [!CAUTION]
> The Keystore is prepared and its credentials are placed in the `build.gradle`. **THIS IS FOR THE
> REVIEWER, FOR THIS PROJECT ONLY AND SHOULDN'T BE DONE IN ANY REAL-WORLD PROJECTS.**
