/*
 Privacy Friendly Finance Manager is licensed under the GPLv3.
 Copyright (C) 2019 Leonard Otto, Felix Hofmann

 This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 General Public License as published by the Free Software Foundation, either version 3 of the
 License, or (at your option) any later version.
 This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License along with this program.
 If not, see http://www.gnu.org/licenses/.

 Additionally icons from Google Design Material Icons are used that are licensed under Apache
 License Version 2.0.
 */

package com.example.myfinancemanager.activity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinancemanager.BuildConfig;
import com.example.myfinancemanager.R;


/**
 * Activity that show the about information of the app.
 *
 * @author Felix Hofmann
 * @author Leonard Otto
 */
public class AboutActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ((TextView) findViewById(R.id.textFieldVersion)).setText(getString(R.string.activity_about_version, BuildConfig.VERSION_NAME));
        ((TextView) findViewById(R.id.secusoWebsite)).setMovementMethod(LinkMovementMethod.getInstance());
        ((TextView) findViewById(R.id.githubURL)).setMovementMethod(LinkMovementMethod.getInstance());
    }
}

