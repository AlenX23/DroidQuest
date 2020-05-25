package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DeceitActivity extends AppCompatActivity {
    private  boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswer;
    private static final String KEY_INDEX = "index";
    private boolean mResponseReviewed = false;

    public static final String EXTRA_ANSWER_IS_TRUE = "com.example.myapplication.answer_is_true";
    public static final String EXTRA_ANSWER_SHOW = "com.example.myapplication.answer_show";

    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent i = new Intent(packageContext, DeceitActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }
    public static boolean wasAnswerShow(Intent result){
        return  result.getBooleanExtra(EXTRA_ANSWER_SHOW, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deceit);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);
        if(savedInstanceState!=null)mResponseReviewed = savedInstanceState.getBoolean(KEY_INDEX);
        if(mResponseReviewed){
            if(mAnswerIsTrue)mAnswerTextView.setText(R.string.true_button);
            else mAnswerTextView.setText(R.string.false_button);
            setAnswerShowResult(true);
        }

        mShowAnswer = (Button)findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue)mAnswerTextView.setText(R.string.true_button);
                else mAnswerTextView.setText(R.string.false_button);
                setAnswerShowResult(true);
                mResponseReviewed = true;
            }
        });
    }

    private  void setAnswerShowResult(boolean isAnswerShow){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOW, isAnswerShow);
        setResult(RESULT_OK, data);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_INDEX, mResponseReviewed);
    }
}