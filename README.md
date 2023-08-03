# ComposeCollection
A sample Android application using Retrofit and Jetpack Compose to display lists of images

## Design patterns:
* MVVM
* Repository pattern

## Libraries:
* Compose
* Retrofit
* Room
* Glide

## Features
The application reaches the endpoint and caches the response. The cache expires after 5 minutes and will get updated every 5 minutes while using the app.
When the cache is ready and valid (not expired) no endpoint call is made and the UI is populated purely with the cache itself.

The images displayed as thumbnails are also cached for better experience.

## API
Using [Rijksmuseum API](https://data.rijksmuseum.nl/object-metadata/api/) the art collections are retrieved and displayed in an [Vertical Staggered Grid](https://developer.android.com/reference/kotlin/androidx/compose/foundation/lazy/staggeredgrid/package-summary#LazyVerticalStaggeredGrid(androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells,androidx.compose.ui.Modifier,androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState,androidx.compose.foundation.layout.PaddingValues,kotlin.Boolean,androidx.compose.ui.unit.Dp,androidx.compose.foundation.layout.Arrangement.Horizontal,androidx.compose.foundation.gestures.FlingBehavior,kotlin.Boolean,kotlin.Function1)).

## APK
A [pre-built APK](app-debug.apk) is placed in the root of the project for your convenience.

### Contact developer

If there's ***anything*** you'd like to discuss, feel free to contact me at [Sd.ghasemi1@gmail.com](mailto:Sd.ghasemi1@gmail.com).

Cheers🍻
