# Assignment Radius Agent

This repository contains an Android application called "Assignment Radius Agent" that allows users to view and interact with facilities and options.

## Features

- Fetches facilities data from a remote API using Retrofit.
- Persists facilities data in a local database using Room.
- Displays facilities and their corresponding options in a scrollable list using Jetpack Compose.
- Handles loading, error, and data presentation using the MVP (Model-View-Presenter) architecture.
- Performs periodic data refresh using WorkManager.

## Installation

1. Clone the repository to your local machine using the following command: https://github.com/thisisvuk/Radiusagent-Assignment

2. Open the project in Android Studio.

3. Build and run the application on an Android device or emulator.

## Dependencies

The following dependencies are used in this project:

- Retrofit: A type-safe HTTP client for Android networking.
- Gson Converter: A converter library for Retrofit to convert JSON responses to objects.
- Room: An abstraction layer over SQLite for database operations.
- WorkManager: A library for managing and scheduling background tasks.

## Usage

Upon launching the application, it will fetch the facilities data from a remote API and display them in a scrollable list. Each facility will have a set of options that can be selected or disabled based on certain conditions.

The list automatically refreshes every day to ensure the data is up to date. You can also manually trigger a refresh by running the `DataRefreshWorker` using the `WorkManager`.

