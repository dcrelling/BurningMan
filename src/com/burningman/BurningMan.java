package com.burningman;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class BurningMan extends Activity {

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.start_screen);

    // --- Camp Button view---
    ImageButton launchButton = (ImageButton) findViewById(R.id.LaunchButton01);
    launchButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent("com.burningman.Home");
        startActivity(intent);
      }
    });

  }

}
