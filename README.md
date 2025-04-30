<div align="center">
  <h1>⏰ Always On Timer 🔒</h1>
  <p><i>Made by <a href="https://www.brunorochamoura.com/about/">BRM</a>.</i></p>
  <br />
</div>

**Always On Timer** is a minimal Android alarm clock app that stays visible and ready to use. Just turn on the screen, no unlocking required.

I built this application out of personal frustration with the stock Android clock while timing rest periods during workouts. Unlocking the phone after every set is a waste of time.

Since no FOSS option fit my needs, I built my own with three key features:

- **Minimalistic:** Set the hours, minutes, and seconds for your timer. When the countdown reaches zero, an alarm with a ringtone and vibration will go off. Simple as that.
- **Screen Stays Unlocked:** The screen remains on while the timer is active.
- **Accessible When Locked:** The timer stays visible after locking the device, as long as it was the last app used.

## Screenshots

<div align="center" style="display: flex; justify-content: space-around;">
  <img src="https://github.com/BrunoRochaDev/AlwaysOnTimer/raw/main/screenshots/screenshot_1.jpg" width="30%" />
  <img src="https://github.com/BrunoRochaDev/AlwaysOnTimer/raw/main/screenshots/screenshot_2.jpg" width="30%" />
  <img src="https://github.com/BrunoRochaDev/AlwaysOnTimer/raw/main/screenshots/screenshot_3.jpg" width="30%" />
</div>

## Where To Get

You can download the latest APK from the [Releases](https://github.com/BrunoRochaDev/AlwaysOnTimer/releases) page, or build the app yourself by opening the project in Android Studio and selecting **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

## How It Works

To allow the app to be used without unlocking the device when it was the last app opened, I use special window flags. On Android Oreo (API 26) and above, this is done by calling `setShowWhenLocked(true)`. For older versions, I apply the `WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED` flag. This ensures the timer stays visible and accessible directly from the lock screen.

Credit to the [Goodtime](https://github.com/adrcotfas/goodtime) app, whose implementation for this I *stole shamelessly*.

## License

This project is distributed under the AGPLv3 License. See the [LICENSE](https://github.com/BrunoRochaDev/AlwaysOnTimer/blob/main/LICENSE) file for details.
