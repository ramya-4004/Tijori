# Tijori
An Android App to lock your apps and files from access to unauthorised users.

## Table of contents
* [Description](#description)
* [Permissions](#permissions)
* [Dependencies](#dependencies)

## Description

This android application enables users to lock their confidential files and protect them from unauthorised access.
### Features
* Biometric authentication to open the app, if device does not have biometric authentication enabled, device credentials could be used alternatively.
* Files get automatically deleted from the accessible memory location.
* Screenshot and Screen-recording protection.
* App icon and name disguised as that of a regular Calendar app.
* Fake password setup, and access to actual working application is prompted using gesture listener.
* Files are encrypted using **Android JETPACK Security**.
* Image, video and PDF Viewer to view the files
* Secret browser for accessing the web

## Permissions
* Phone storage


## Dependencies
* Biometric Authentication<br/>
   `androidx.biometric:biometric:1.1.0`

* Lifecycle Observation dependency<br/>
    `android.arch.lifecycle:extensions:1.1.1`

* Glide library<br/>
    `com.github.bumptech.glide:glide:4.12.0`

* Get file extension<br/>
    `commons-io:commons-io:2.6`
    
* PDF Viewer<br/>
    `com.github.barteksc:android-pdf-viewer:2.8.2`

* Android jetpack security<br/>
    `androidx.security:security-crypto:1.0.0`

