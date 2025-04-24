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

TODO

## Where To Get

TODO

## How It Works

To allow the app to be used without unlocking the device, I use special window flags. On devices running Android Oreo (API 26) or higher, I call `setShowWhenLocked(true)` and `setTurnScreenOn(true)`, which ensures the app remains visible and the screen turns on automatically. For devices running older versions, I use `WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED` and `WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON` to achieve the same effect, enabling the app to stay on screen and wake up the device when needed.

Credit to the [Goodtime](https://github.com/adrcotfas/goodtime) app, whose implementation for this I *stole shamelessly* because I had no idea how to do it.

## License

This project is distributed under the AGPLv3 License. See the [LICENSE](https://github.com/BrunoRochaDev/AlwaysOnTimer/blob/main/LICENSE) file for details.