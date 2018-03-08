/*
 *  Copyright (c) 2018 Alexander Filbert, Carolyn Binns, Jeanna Somoza, JingMing Huang, Matthew Quigley, Nathanael Belayneh
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.example.n8tech.taskcan;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ahuangJM on 2/22/2018.
 *
 * NOTE IMPORTANT *****
 *
 * split this class into 2, one for input, one for output
 * this class should have 2 static classes that the input and output class
 * can inherent and implement and there are OpenFile and CloseFile!!!!
 */

public class FileIO {
    private File file;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    public FileIO(Context c) {
        final String fileName = "home.cache";
        this.file = new File(c.getCacheDir(), fileName);
    }
    public void OpenFile() {
        try {
            FileWriter fileWriter = new FileWriter(this.file.getAbsoluteFile());
            FileReader fileReader = new FileReader(this.file.getAbsoluteFile());
            this.bufferedWriter = new BufferedWriter(fileWriter);
            this.bufferedReader = new BufferedReader(fileReader);
        } catch (IOException e) {
            Log.e("FileIO:", "Open File Failed", e);
        }
    }
    public void WriteFile(String s) {
        try {
            this.bufferedWriter.write(s);
            this.bufferedWriter.flush();
        } catch (IOException e) {
            Log.e("FileIO:", "Write File Failed", e);
        }
    }
    public String ReadFile() {
        char[] content = new char[100]; // is 100 enough or too much?
        try {
            this.bufferedReader.read(content);
        } catch (IOException e) {
            Log.e("FileIO:", "Read File Failed", e);
        }
        return String.valueOf(content);
    }
    public void CloseFile() {
        try {
            this.bufferedWriter.close();
            this.bufferedReader.close();
        } catch (IOException e) {
            Log.e("FileIO:", "Close File Failed", e);
        }
    }
}
