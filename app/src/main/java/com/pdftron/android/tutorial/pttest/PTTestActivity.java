package com.pdftron.android.tutorial.pttest;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

import com.pdftron.common.PDFNetException;
import com.pdftron.pdf.PDFDoc;
import com.pdftron.pdf.PDFNet;
import com.pdftron.pdf.PDFViewCtrl;
import com.pdftron.pdf.tools.ToolManager;

import java.io.IOException;
import java.io.InputStream;

public class PTTestActivity extends Activity {

    private PDFViewCtrl mPDFViewCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the library
        try {
            PDFNet.initialize(this, R.raw.pdfnet);
        } catch (PDFNetException e) {
            // Do something...
            e.printStackTrace();
        }

        // Inflate the view and get a reference to PDFViewCtrl
        setContentView(R.layout.main);
        mPDFViewCtrl = (PDFViewCtrl) findViewById(R.id.pdfviewctrl);

        mPDFViewCtrl.setToolManager(new ToolManager(mPDFViewCtrl));

        // Load a document
        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.sample);
        try {
            PDFDoc doc = new PDFDoc(is);
            mPDFViewCtrl.setDoc(doc);
            // Or you can use the full path instead
            //doc = new PDFDoc("/mnt/sdcard/sample.pdf");
        } catch (PDFNetException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        // This method simply stops the current ongoing rendering thread, text
        // search thread, and tool
        super.onPause();
        if (mPDFViewCtrl != null) {
            mPDFViewCtrl.pause();
        }
    }

    @Override
    protected void onResume() {
        // This method simply starts the rendering thread to ensure the PDF
        // content is available for viewing.
        super.onResume();
        if (mPDFViewCtrl != null) {
            mPDFViewCtrl.resume();
        }
    }

    @Override
    protected void onDestroy() {
        // Destroy PDFViewCtrl and clean up memory and used resources.
        super.onDestroy();
        if (mPDFViewCtrl != null) {
            mPDFViewCtrl.destroy();
        }
    }

    @Override
    public void onLowMemory() {
        // Call this method to lower PDFViewCtrl's memory consumption.
        super.onLowMemory();
        if (mPDFViewCtrl != null) {
            mPDFViewCtrl.purgeMemory();
        }
    }
}