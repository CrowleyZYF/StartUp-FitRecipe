package cn.fitrecipe.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.CommentCardAdapter;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.model.CommentCard;
import pl.tajchert.sample.DotsTextView;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class CommentActivity extends Activity implements View.OnClickListener {

    private ImageView backBtn;
    private TextView cancelBtn;

    private RecyclerView frCommentRecyclerView;
    private RecyclerViewLayoutManager frCommentLayoutManager;

    private ImageView commentBtn;
    private EditText editText;
    private TextView replyCommentID;
    private TextView replyUserID;

    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        View rootView = View.inflate(this, R.layout.activity_comment, null);
        setContentView(rootView);

        initView();
        initData(rootView);
        initEvent();
    }

    private void initData(View rootView) {
        List<CommentCard> result = new ArrayList<CommentCard>();
        for(int i = 0; i < 5; i++) {
            CommentCard cc = new CommentCard(i, i, R.drawable.pic_header, "用户"+i, i%3, "1991-11-26 21:11", "你说什么啊，好玩嘛", "被回复的"+i);
            result.add(cc);
        }
        for(int i = 5; i < 10; i++) {
            CommentCard cc = new CommentCard(i, i, R.drawable.pic_header, "用户"+i, i%3, "1991-11-26 21:11", "是富有浙江杭州地方特色的汉族名菜是富有浙江杭州地方特色的汉族名菜是富有浙江杭州地方特色的汉族名菜是富有浙江杭州地方特色的汉族名菜是富有浙江杭州地方特色的汉族名菜是富有浙江杭州地方特色的汉族名菜", "");
            result.add(cc);
        }
        CommentCardAdapter commentCardAdapter = new CommentCardAdapter(this, result, rootView);
        frCommentRecyclerView.setAdapter(commentCardAdapter);
    }

    private void initView() {
        backBtn = (ImageView) findViewById(R.id.back_btn);
        cancelBtn = (TextView) findViewById(R.id.comment_cancel_reply);

        frCommentRecyclerView = (RecyclerView) findViewById(R.id.comment_recycler_view);
        frCommentLayoutManager = new RecyclerViewLayoutManager(this);
        frCommentLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        frCommentRecyclerView.setLayoutManager(frCommentLayoutManager);

        commentBtn = (ImageView) findViewById(R.id.comment_reply_btn);
        editText = (EditText) findViewById(R.id.comment_editText);
        replyCommentID = (TextView) findViewById(R.id.comment_reply_comment_id);
        replyUserID = (TextView) findViewById(R.id.comment_reply_user_id);

        loadingInterface = (LinearLayout) findViewById(R.id.loading_interface);
        dotsTextView = (DotsTextView) findViewById(R.id.dots);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoading(false,"");
            }
        }, 2000);
    }

    private void initEvent() {
        backBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        commentBtn.setOnClickListener(this);
    }

    private void hideLoading(boolean isError, String errorMessage){
        loadingInterface.setVisibility(View.GONE);
        dotsTextView.stop();
        if(isError){
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }else{
            frCommentRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.comment_cancel_reply:
                cancelBtn.setVisibility(View.GONE);
                editText.setHint(R.string.comment_hint);
                editText.setText("");
                break;
            case R.id.comment_reply_btn:
                String content = editText.getText().toString();
                if(content.equals("")){
                    Toast.makeText(this,"回复内容不得为空><",Toast.LENGTH_LONG).show();
                }else{
                    if(cancelBtn.getVisibility()==View.GONE){
                        Toast.makeText(this,"回复内容为： "+content,Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(this,"回复用户的ID： "+replyUserID.getText() + " 回复评论的ID： "+replyCommentID.getText() + " 回复内容为： "+content, Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                break;
        }
    }
}
