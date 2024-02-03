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

NOTE: This doesn't get you unstuck. Generally, the best ways to unstick yourself is to throw yourself into the void, or
if you really need to: go into creative and destroy/reset the 4 crystals around the portal frame. Another way, if you
have this mod and NBTExplorer, would be to add a byte tag in the DragonFight entry called isRespawning, and set it to 1. 
Then restart the game and this mod will take care of the rest.