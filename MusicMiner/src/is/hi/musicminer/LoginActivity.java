package is.hi.musicminer;

import java.util.Arrays;
import java.util.List;

import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.widget.LoginButton;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity {

    private LoginButton loginButton;
    private Session.StatusCallback statusCallback = new SessionStatusCallback();
    private final List<String> PERMISSIONS = Arrays.asList("user_likes");
    private final String TAG = "LoginActivity";
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        loginButton = (LoginButton)findViewById(R.id.buttonLogin);
        loginButton.setReadPermissions(PERMISSIONS);
        loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) { onClickLogin();}	
        });
        		
        		
        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        Session session = Session.getActiveSession(); //Sækjum virkt Session ef eitthvað er, annars null
                     
        if (session == null) {
            if (savedInstanceState != null) {
            	//Endurreisum vistað Session ef eitthvað var, annars null
                session = Session.restoreSession(this, null, statusCallback, savedInstanceState); 
            }
            if (session == null) {
                session = new Session(this);
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
            }
        }
        else{
        	if(session.isOpened()){
        		Intent nextActivity = new Intent(LoginActivity.this, MainActivity.class);
        		startActivity(nextActivity);
        		LoginActivity.this.finish();  	
        	}
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
        Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        Session.getActiveSession().removeCallback(statusCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }

	private void onClickLogin() {
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
        } else {
            Session.openActiveSession(this, true, statusCallback);
        }
    }

	private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            if(state.isOpened() && session.isOpened()){
            	Intent nextActivity = new Intent(LoginActivity.this, MainActivity.class);
            	startActivity(nextActivity);
            	LoginActivity.this.finish();  	
            }
        }
    }
}
