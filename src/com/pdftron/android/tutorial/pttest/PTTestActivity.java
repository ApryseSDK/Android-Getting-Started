package com.pdftron.android.tutorial.pttest;

import java.io.IOException;
import java.io.InputStream;

import pdftron.Common.PDFNetException;
import pdftron.PDF.PDFDoc;
import pdftron.PDF.PDFNet;
import pdftron.PDF.PDFViewCtrl;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

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
        
        // Use the tool manager to add additional UI modules to PDFViewCtrl.
        mPDFViewCtrl.setToolManager(new pdftron.PDF.Tools.ToolManager());
        
        // Load a document
        PDFDoc doc = null;
        Resources res = getResources();
        InputStream is = res.openRawResource(R.raw.sample_doc);
        try {
            doc = new PDFDoc(is);
            // Or you can use the full path instead
            //doc = new PDFDoc("/mnt/sdcard/sample_doc.pdf");
            
            // Opening a password protectd document...
            //if (!doc.initStdSecurityHandler("the password")) {
            //    return; // Wrong password...
            //}
        } catch (PDFNetException e) {
            doc = null;
            e.printStackTrace();
        } catch (IOException e) {
            doc = null;
            e.printStackTrace();
        }
        mPDFViewCtrl.setDoc(doc);
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
