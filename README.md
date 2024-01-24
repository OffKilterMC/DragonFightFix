# DragonFightFix

![Screenshot](https://i.imgur.com/fF7165X.jpg)

A simple mod to fix the issue where the dragon fight can get stuck if you quit the game while the Ender Dragon is making
her dramatic entrance. Without this, you will get stuck in the end with no way out other than to jump into the void. Or
if you had enough end crystals, you could reset the crystals to restart the sequence.

The core issue is that the code simply always writes false for isRespawning. Strangely, when the end fight is restored
next time, it does try to reset the dragon fight, but since the value is always false, it never does. I fixed this by
now saving the proper boolean value if the dragon is respawning, and also added code to reset the spike crystals when
the dragon fight starts again to start with a clean slate. Otherwise, they'd still be sitting there. It looks cleaner
this way.

The end result (pun intended) is that the dragon restarts the respawn cycle from the start once you rejoin. Ideally it 
would be great if we could just remember where we left off, but that's a bunch of state to save, and I thought it was
overkill for this small fix. 