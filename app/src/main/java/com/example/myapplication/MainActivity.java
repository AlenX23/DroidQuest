package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "QuestActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_DECEIT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mDeceitButton;
    private ImageButton mBackButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;

private Question[] mQuestionBank = new Question[]{
        new Question(R.string.question_android, true),
        new Question(R.string.question_linear, false),
        new Question(R.string.question_service, false),
        new Question(R.string.question_res, true),
        new Question(R.string.question_manifest, true),
        new Question(R.string.question_android1, true),
        new Question(R.string.question_linear1, false),
        new Question(R.string.question_service1, false),
        new Question(R.string.question_res1, true),
        new Question(R.string.question_manifest1, true),
};
private boolean mUsedHint[] = new boolean[mQuestionBank.length];
private int mCurrentIndex = 0;
private boolean mIsDeceiter;
    private  static final String KEY_INDEX_ONE = "index";
    private  static final String KEY_INDEX_TWO = "indextwo";
    private  static final String KEY_INDEX_QUESTION = "QuestionBank";

private void updateQuestion(){
    int question = mQuestionBank[mCurrentIndex].getTextResId();
    mQuestionTextView.setText(question);
}

private void checkAnswer(boolean userPressedTrue) {
    boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
    int messageResId = 0;
    if (mIsDeceiter || mUsedHint[mCurrentIndex]) {
        messageResId = R.string.judgment_toast;
    } else {
        if (userPressedTrue == answerIsTrue)
            messageResId = R.string.correct_toast;
            else
                messageResId = R.string.incorrect_toast;
        }
    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
}

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() вызван");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() вызван");
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() вызван");
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() вызван");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() вызван");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onStart() вызван");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsDeceiter = savedInstanceState.getBoolean(KEY_INDEX_TWO,false);
            mUsedHint = savedInstanceState.getBooleanArray(KEY_INDEX_QUESTION);
        }

        mQuestionTextView=(TextView)findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                checkAnswer(false);
            }
        });

        mDeceitButton = (Button)findViewById(R.id.deceit_button);
        mDeceitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent i = DeceitActivity.newIntent(MainActivity.this,answerIsTrue);
                startActivityForResult(i,REQUEST_CODE_DECEIT);
            }
        });

        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsDeceiter = false;
                updateQuestion();
            }
        });

        mBackButton = (ImageButton)findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                    if(mCurrentIndex == 0 || mCurrentIndex < 0){
                        mCurrentIndex = mQuestionBank.length - 1;
                    } else {
                        mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    }
                    updateQuestion();
            }
        });
        updateQuestion();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_INDEX_TWO,mIsDeceiter);
        savedInstanceState.putBooleanArray(KEY_INDEX_QUESTION,mUsedHint);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)return;
        if (requestCode == REQUEST_CODE_DECEIT) {
            if (data == null)return;
        }
        mIsDeceiter = DeceitActivity.wasAnswerShow(data);
        mUsedHint[mCurrentIndex] = true;
        super.onActivityResult(requestCode, resultCode, data);
    }
}