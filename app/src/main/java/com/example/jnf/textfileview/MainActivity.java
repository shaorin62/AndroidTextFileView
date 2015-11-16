package com.example.jnf.textfileview;

import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class MainActivity extends AppCompatActivity {
    EditText mEditFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditFile = (EditText)this.findViewById(R.id.editFile);

        File file =  getFileStreamPath("text.txt");
        if(file.isFile() == false){
            String strBuf = ReadTextAssets("text.txt");
            WriteTextFile("text.txt",strBuf);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //파일을 불러와서 파일의 내용을 반환한다.
    public String ReadTextAssets(String strFileName){
        String text = null;
        try{
            //파일의 inputStream 을 구한다.
            InputStream is = getAssets().open(strFileName);
            //byte 배열을 생성한다. 읽을수 있는 byte 의 값을 리턴한다.
            int size = is.available();
            byte[] buffer = new byte[size];
            //InputStream 에서 텍스트 데이터를 읽는다.
            is.read(buffer);
            is.close();

            text = new String(buffer);

        }catch(IOException e){
            throw new RuntimeException(e);
        }
        return text;
    }

    public boolean WriteTextFile(String strFileName, String strBuf){
        try{
            //파일을 오픈한다.
            File file = getFileStreamPath(strFileName);
            //파일출력 스트림을 구한다.(흐름 데이터 세트 간에서 데이터의 전송이 실행되고 있는것! 항목이 연속으로 되어있는것)
            FileOutputStream fos = new FileOutputStream(file);
            //텍스트 인코딩 방식을 구한다.
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            //데이터를 파일에 저장한다.
            out.write(strBuf);
            out.close();

        }catch(IOException e){
            throw new RuntimeException(e);
        }
        return true;
    }


    // /file 폴더에 있는 파일의 내용을 읽기
    public String ReadTextFile(String strFileName){
        String text = null;
        try{
            //파일을 오픈한다.
            File file = getFileStreamPath(strFileName);
            FileInputStream fis = new FileInputStream(file);
            Reader in = new InputStreamReader(fis);
            int size = fis.available();

            //Char 배열을 생성
            char[] buffer = new char[size];
            //파일에서 텍스트 파일을 읽는다.
            in.read(buffer);
            in.close();
            text = new String(buffer);

        }catch(IOException e){
            throw new RuntimeException(e);
        }
        return text;
    }


    public void onClick(View v){
        String strBuf;

        switch(v.getId()){
            case R.id.buttonReadAssets:
                strBuf = ReadTextAssets("text.txt");
                mEditFile.setText(strBuf);
                break;

            case R.id.buttonWriteFile:
                strBuf = mEditFile.getText().toString();
                if(WriteTextFile("text.txt",strBuf)){
                    Toast.makeText(this,"File Saved",Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.buttonReadFile:
                strBuf = ReadTextFile("text.txt");
                mEditFile.setText(strBuf);
                break;
        }
    }
}
