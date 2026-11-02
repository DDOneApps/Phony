# Phony

<div align="center">

<img src="https://github.com/user-attachments/assets/c36243df-659f-4ac5-8c63-a1344e8b876a" width=20% title="logo" />

<p>

[![GitHub stars](https://img.shields.io/github/stars/DDOneApps/Phony?style=for-the-badge)](https://github.com/DDOneApps/Phony/stargazers) [![GitHub forks](https://img.shields.io/github/forks/DDOneApps/Phony?style=for-the-badge)](https://github.com/DDOneApps/Phony/network) [![GitHub issues](https://img.shields.io/github/issues/DDOneApps/Phony?style=for-the-badge)](https://github.com/DDOneApps/Phony/issues)
[![Downloads](https://img.shields.io/github/downloads/DDOneApps/Phony/total?color=green&style=for-the-badge)](https://github.com/DDOneApps/Phony/releases/latest) 
[![Crowdin](https://img.shields.io/badge/dynamic/json?color=red&label=translation&query=$.message&style=for-the-badge&url=https://raw.githubusercontent.com/DDOneApps/Phony/main/badge.json&logo=crowdin)](https://crowdin.com/project/fakecall)

[![GitHub license](https://img.shields.io/badge/license-GPL%20v3-red?style=for-the-badge)](LICENSE)
</p>

**Phony (formerly FakeCall) is an open-source Android application to simulate incoming calls, featuring a modern Material 3 UI with dynamic Monet support.**


</div>

> **Using Android 6?** Phony needs a quick one-time setup on Android 6 (the calling account has to be enabled). See [Android 6 (API 23) setup](#android-6-api-23-setup).

## Overview
Ever wanted to get [that Feature of old Samsung phones](https://www.youtube.com/watch?v=OKV3Eei5JNE) to simulate an incoming call with audio on the original phone app?
Introducing Phony. Unlike other apps that merely mock a UI, this app integrates directly with the Android Telecom Framework to provide an indistinguishable calling experience. It has many features to make the call as real as possible.

<p align=center>

## Features

</p>

-  **Original Dialer:** Phony uses your real Phone app to simulate the incoming Call by creating [a Phone Account in android's TelecomManager](https://developer.android.com/reference/android/telecom/TelecomManager)
-  **Customizable:** The app lets you customize the name of the fake phone account in the settings to match your real service provider.
-  **Schedule:** Set exact Timers for when the call should come in
-  **Audio Support:** You can upload audio files that play when the call is answered
-  **Call History:** Simulated calls are being shown in call history
-  **IVR Mode:** assign audio files to keys and make sub-menus
-  **Recording:** record microphone audio of a simulated call
-  **Automation API:** trigger calls from Tasker, MacroDroid, or ADB via a broadcast intent
-  **Accessibility Shortcut:** schedule a fake call from the system accessibility button using saved defaults
-  **Quick Trigger Presets:** save up to 5 presets and expose them as launcher app actions + Quick Settings tiles

## Automation API

Phony exposes a broadcast receiver for automation apps.

**Action**

`com.upnp.fakeCall.TRIGGER`

**Extras**

- `caller_name` (`String`, optional)
- `caller_number` (`String`, optional)
- `delay` (`Int`, optional, seconds)

If one or more extras are omitted, Phony falls back to the saved **Automation & Quick Trigger Defaults** from Settings.

**ADB example (recommended, package-targeted, no `-n` needed)**

```bash
adb shell am broadcast -a com.upnp.fakeCall.TRIGGER -p com.upnp.fakeCall --es caller_name "Boss" --es caller_number "+49123456789" --ei delay 30
```

**Windows `cmd.exe` (single line)**

```cmd
adb shell am broadcast -a com.upnp.fakeCall.TRIGGER -p com.upnp.fakeCall --es caller_name "Boss" --es caller_number "+49123456789" --ei delay 30
```

**Explicit component fallback**

```bash
adb shell am broadcast -n com.upnp.fakeCall/.ExternalTriggerReceiver -a com.upnp.fakeCall.TRIGGER --es caller_name "Boss" --es caller_number "+49123456789" --ei delay 30
```

For Tasker, MacroDroid, etc. set:
- Action: `com.upnp.fakeCall.TRIGGER`
- Package: `com.upnp.fakeCall`

## Accessibility Quick Trigger

Enable the `Phony` accessibility service and assign it to the system accessibility button or shortcut. When invoked, it schedules a fake call using the saved quick-trigger defaults from Settings and shows a short confirmation toast.

You can configure these defaults inside:

`Settings -> Automation & Quick Trigger Defaults`

You can also save up to five quick trigger presets from the same section:

- presets appear as launcher app actions (long-press the app icon)
- presets are available as Quick Settings tiles (`Phony Preset 1` ... `Phony Preset 5`)

## Android 6 (API 23) setup

Phony runs on Android 6.0 or newer. On Android 5.x the system rejects the registration of the calling account, so the app cannot work there.

> **Experimental:** Android 6 support is experimental. Some features may not work correctly, and the steps below may differ between phones and manufacturers.

On Android 6 the calling account has to be enabled once before Phony can simulate a call.

### Option 1: switch in Settings

1. Open Phony once (this registers the calling account).
2. Tap **Open calling accounts** in the setup screen, or go to Phone -> Settings -> Calling accounts.
3. Find **Phony** in the list and turn it on.

**Note:** some Android 6 devices don't have this switch (or the settings screen closes by itself). If that happens, use Option 2.

### Option 2: adb (from a computer)

**Step 1.** On the phone, enable **Developer options** (go to Settings -> About phone [or Software information], then tap **Build number** 7 times). Go back to the main Settings menu, open the newly visible **Developer options** section, scroll down, and turn on **USB debugging**.

**Step 2.** Download and extract [Android Platform-Tools](https://developer.android.com/tools/releases/platform-tools) on your computer.

**Step 3.** Connect the phone with a USB cable and accept the debugging prompt on the phone screen.

**Step 4.** Open a terminal on your computer:

- **Windows:** Open the extracted `platform-tools` folder in File Explorer, click the **folder path bar at the top** (where it shows folder names like `This PC > Downloads > platform-tools`), clear everything from this bar, type `cmd`, and press Enter.
- **macOS / Linux:** Open Terminal, type `cd ` (with a space at the end), drag and drop the `platform-tools` folder into the Terminal window, and press Enter (or right-click the folder in Finder and choose **New Terminal at Folder**).

**Step 5.** Check that the phone is detected by running this command:

```bash
adb devices
```

On Windows PowerShell you might need to use `./adb devices` instead.

Your phone should appear in the list with the status `device`. If it says `unauthorized` or isn't listed at all, unlock the phone, unplug and reconnect the USB cable, accept the debugging prompt on the phone, and run the command again.

**Step 6.** Open Phony once.

**Step 7.** Run this command to enable the account:

```bash
adb shell telecom set-phone-account-enabled com.upnp.fakeCall/com.upnp.fakeCall.FakeCallConnectionService fake_call_provider_account 0
```

**Step 8.** Check the result. Run the command that matches your system:

**macOS / Linux:**

```bash
adb shell dumpsys telecom | grep "com.upnp.fakeCall"
```

**Windows (Command Prompt):**

```cmd
adb shell dumpsys telecom | findstr "com.upnp.fakeCall"
```

**Windows (PowerShell):**

```powershell
adb shell dumpsys telecom | Select-String "com.upnp.fakeCall"
```

Find the line containing `com.upnp.fakeCall`. If it starts with `[[X]`, the account is enabled; `[[ ]` means disabled.

Example output: `[[X] PhoneAccount: ComponentInfo{com.upnp.fakeCall/com.upnp.fakeCall.FakeCallConnectionService}, ...`

To disable the account again:

```bash
adb shell telecom set-phone-account-disabled com.upnp.fakeCall/com.upnp.fakeCall.FakeCallConnectionService fake_call_provider_account 0
```

**Troubleshooting**

- `is ComponentInfo{...} a valid PhoneAccount?`: open Phony once so the account gets registered, and make sure the package, class and account id are exactly as in the command above.
- Several devices connected: add `-s SERIAL` right after `adb` (the serial comes from `adb devices`).
- Samsung devices may print `MdfppReflectionUtils ... UnsatisfiedLinkError` warnings. They are harmless; look for the line starting with `Success`.
- The command may differ between phones and manufacturers.

### Limitations on Android 6

Quick Settings tiles (Android 7+) and launcher app actions (Android 7.1+) are not available on Android 6.

## Screenshots

![Screenshot 1](https://github.com/DDOneApps/Phony/blob/main/metadata/en-US/images/phoneScreenshots/1.png)
_Main screen_

![Screenshot 3](https://github.com/DDOneApps/Phony/blob/main/metadata/en-US/images/phoneScreenshots/3.png)
_settings screen_

![Screenshot 4](https://github.com/DDOneApps/Phony/blob/main/metadata/en-US/images/phoneScreenshots/2.png)
_Incoming call (example)_

## Tech Stack

**Mobile Development:**

[![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)

[![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/)

[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpack%20compose&logoColor=white)](https://developer.android.com/jetpack/compose)

**Build System:**

[![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)](https://gradle.org/)

**UI/UX:**

[![Material 3](https://img.shields.io/badge/Material%203-0057B7?style=for-the-badge&logo=materialdesign&logoColor=white)](https://m3.material.io/)

[![Dynamic Color](https://img.shields.io/badge/Monet-8BC34A?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/design/color/dynamic-color)


## Star History

<a href="https://www.star-history.com/?repos=DDOneApps%2FPhony&type=date&legend=top-left">
 <picture>
   <source media="(prefers-color-scheme: dark)" srcset="https://api.star-history.com/chart?repos=DDOneApps/Phony&type=date&theme=dark&legend=top-left&sealed_token=94RDa-jVjWC7k4fnw3wGcsA_OLrM7ysC39rUlACOBSv2YGxFEmZ2W9hCARovw72YNpf674AQYZVtkO9gFQHPjLRaf0Hgqnu4FKbXnsr-J8hkblFjmm9bLA" />
   <source media="(prefers-color-scheme: light)" srcset="https://api.star-history.com/chart?repos=DDOneApps/Phony&type=date&legend=top-left&sealed_token=94RDa-jVjWC7k4fnw3wGcsA_OLrM7ysC39rUlACOBSv2YGxFEmZ2W9hCARovw72YNpf674AQYZVtkO9gFQHPjLRaf0Hgqnu4FKbXnsr-J8hkblFjmm9bLA" />
   <img alt="Star History Chart" src="https://api.star-history.com/chart?repos=DDOneApps/Phony&type=date&legend=top-left&sealed_token=94RDa-jVjWC7k4fnw3wGcsA_OLrM7ysC39rUlACOBSv2YGxFEmZ2W9hCARovw72YNpf674AQYZVtkO9gFQHPjLRaf0Hgqnu4FKbXnsr-J8hkblFjmm9bLA" />
 </picture>
</a>
##  Project Structure

```
Phony/
├── .github/          # GitHub related files (e.g., issue templates, workflows) - TODO: If applicable
├── .idea/            # IntelliJ/Android Studio project configuration files
├── app/              # Main Android application module
│   ├── build.gradle.kts # Module-level Gradle build script (Kotlin DSL)
│   ├── src/          # Source code and resources
│   │   ├── main/     # Main source set (Kotlin code, resources, manifest)
│   │   └── androidTest/ # Android UI test source set
│   └── ...           # Other module files
├── build.gradle.kts  # Top-level Gradle build script (Kotlin DSL)
├── gradle/           # Gradle wrapper files
├── gradlew           # Gradle wrapper script (for Unix/macOS)
├── gradlew.bat       # Gradle wrapper script (for Windows)
├── gradle.properties # Global Gradle properties
├── settings.gradle.kts # Gradle settings file (defines project modules)
├── .gitignore        # Specifies intentionally untracked files to ignore
└── README.md         # This README file
```

## Contributing

We welcome contributions to Phony!

If you want to help translating, do it [Here](https://crowdin.com/project/fakecall/invite?h=ad1b7ff358ecf52e9f823b4f7f691f1d2725120) via crowdin
## License

This Project is licenced under GNU General Public License.
Read it [Here](https://raw.githubusercontent.com/DDOneApps/Phony/refs/heads/main/LICENSE)

## Acknowledgments

-   AI for helping me with my poor kotlin knowledge.
-   [NLL Apps](https://github.com/NLLAPPS) for telling me how this project could be implemented. ([Reddit](https://www.reddit.com/r/fossdroid/comments/1rj26ty/foss_alternative_to_fake_call_app_using_real/))

## Support

-   If you encounter any bugs or have feature requests, please open an issue on [GitHub Issues](https://github.com/DDOneApps/Phony/issues).

---


**☕️ If you want to support me, you can do it via monero:**
`42eQ1sZtR1USUcGRwc4hiTLNvL7q8U9XSCVVtuhRFKKREpevd4F1X3aN8X4UzkqNTy3n4BsfUooLvj1ydjpem5Ee9SRA2dZ`
![9a569a26-b6f7-4425-84b6-0584a914ae54](https://github.com/user-attachments/assets/10f6a5b6-bf4c-43c1-8929-21e7cc3951e2)


**⭐ Star this repo if you find it helpful!**

Made with ❤️(and AI 🤖)



