package com.edvorg.neonbox;

import android.app.Activity;
import android.os.Bundle;

public class NeonboxActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        m_MyGLView = new NeonboxSurfaceView(this);
        setContentView(m_MyGLView);
        
        m_Level = new Level();
        m_Level.start();
        
        m_MyGLView.setLevel(m_Level);
    }
    
    @Override
    protected void onPause()
    {
    	super.onPause();
    	m_MyGLView.onPause();
    	m_Level.pause();
    }
    
    @Override
    protected void onResume()
    {
    	super.onResume();
    	m_MyGLView.onResume();
    	m_Level.start();
    }

    private Level m_Level;
	private NeonboxSurfaceView m_MyGLView;
}