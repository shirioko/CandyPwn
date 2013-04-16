CandyPwn
========

A simple one-click app for Android to reset your lives in Candy Crush Saga

In the game Candy Crush Saga you use your lives to play. 
After you run out of lives you have to wait 24 hours to receive 5 new lives.

This can be reset by clearing application data or by simply removing the files:

    /data/data/com.king.candycrushsaga/app_storage/save_*.*
    
Note that this will only work if you're connected to Facebook. After removing
the file the app will sync your current level from Facebook and you can continue
where you left off. This means that you also need to be connected to the internet.

Warning: If you're not connected to Facebook you will simply return to Level 1!
