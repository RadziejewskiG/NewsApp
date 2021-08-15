# NewsApp

### About the app
This is a simple Football News app which contains three screens:
- News list screen: a list of football news with a title, date and a small photo. The list is paginated and the new page is loaded when the user reaches the bottom of the current list. Clicking on news redirects to the News details screen.
- News details screen: it contains detailed information about the clicked article (full title, date, full-sized photo and formatted article&apos;s body - from html).
- Matches screen: a list of matches between two teams with live-updated score (using fake SDK provided as an attachment to the task).

The News screens (list, details) and Matches screen are accessible by clicking the proper tab on the Bottom Navigation Bar (which is always visible). The back navigation is properly handled and the app is switching between the tabs and nested destinations:
- Matches screen -> News details screen -> News list screen
- Matches screen -> News list screen
- News details screen -> News list screen

The news pages are retrieved from the REST API and then stored in a local database (which is the single source of truth for the news list). To make everything work smoothly, the RemoteMediator&apos;s implementation is used. The database also contains a second table, where read news&apos;s ids are stored in order to persist that data and to inform the user which articles they have already read.

### Project structure - Clean Architecture
[![graph.png](https://i.postimg.cc/kGxpj9kL/graph.png)](https://postimg.cc/fSRCy4Wv)

### About the project
- Kotlin DSL
- Multi-module
- Clean Architecture
- Single Activity

### Tests
As for now, the project contains unit tests of those classes:
- MatchesViewModel
- All of the use cases
- ErrorUtil

### Tech stack
- Kotlin
- Hilt
- Coroutines, Flow
- Room
- Paging 3
- Retrofit, OkHttp, Moshi
- Glide
- Lottie
- Facebook&apos;s Shimmer
- Architecture Components
- Navigation Component
- Material Design
- Material Components
- JUnit
- Mockito
- Google&apos;s Truth
