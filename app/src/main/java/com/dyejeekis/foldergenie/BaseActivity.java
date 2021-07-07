package com.dyejeekis.foldergenie;

import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.appcompat.app.AppCompatActivity;

import com.dyejeekis.foldergenie.util.BetterActivityResult;

public class BaseActivity extends AppCompatActivity {

    protected final BetterActivityResult<Intent, ActivityResult> activityLauncher =
            BetterActivityResult.registerActivityForResult(this);
}
