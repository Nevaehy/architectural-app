# Architectural App

## Goal
#### The goal was to create a sample application that would cover the maximum number of architectural aspects. Despite the fact that the functionality of this application is very simple and the architecture looks like "overkill" for such an application, however, the architecture is made in such a way that the application can be scaled to a significant size (hundreds of Fragments and dozens of APIs).

## Functionality
#### The functionality is extremely simple, but at the same time, it demonstrates several technologies.
#### On startup, the application attempts to access the open.ai service and generate a link to the image. On failure it shows Error screen. On success, the application shows a ViewPager with two fragments:
#### 1. The first fragment contains the title, and the rendered image that we got from the link. By clicking on the button, the BottomSheet opens.
#### 2. The second fragment contains a title and a link in text form. Clicking on the button opens a link in ChromeTab.

## Architecture
#### An attempt of a modular Clean Architecture* using MVVM.
#### * - There is a little trick to the implementation here. Unfortunately, I haven't yet implemented the new way to use Dagger 2 in multi-module apps, so there is a small workaround for the App module. However, the rest of the modules follow the strict principle of unidirection.

## How to use
#### 1. Create an account on https://openai.com
#### 2. Generate a secret key in your profile https://platform.openai.com/account/api-keys
#### 3. Download this Architectural App
#### 4. Use your secret key in `OkHttpApiClientConstants` class as a value for `AUTH_TOKEN` field
#### 5. Build and Launch the app

## Screenshots
