package cn.fitrecipe.android;

import android.app.Activity;
import android.content.Intent;
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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.fitrecipe.android.Adpater.CommentCardAdapter;
import cn.fitrecipe.android.Http.FrRequest;
import cn.fitrecipe.android.Http.FrServerConfig;
import cn.fitrecipe.android.Http.GetRequest;
import cn.fitrecipe.android.Http.PostRequest;
import cn.fitrecipe.android.UI.BorderScrollView;
import cn.fitrecipe.android.UI.RecyclerViewLayoutManager;
import cn.fitrecipe.android.entity.Author;
import cn.fitrecipe.android.entity.Comment;
import pl.tajchert.sample.DotsTextView;

/**
 * Created by 奕峰 on 2015/5/8.
 */
public class CommentActivity extends Activity implements View.OnClickListener {

    private ImageView backBtn;
    private TextView cancelBtn;

    private RecyclerView frCommentRecyclerView;
    private RecyclerViewLayoutManager frCommentLayoutManager;
    private CommentCardAdapter commentCardAdapter;

    private BorderScrollView scrollView;

    private ImageView commentBtn;
    private EditText editText;
    private TextView replyCommentID;
    private TextView replyUserID;

    private LinearLayout loadingInterface;
    private DotsTextView dotsTextView;

    private List<Comment> comments;
    private View rootView;
    private int recipeId;
    private int lastid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        rootView = View.inflate(this, R.layout.activity_comment, null);
        setContentView(rootView);

        initView();
        recipeId = getIntent().getIntExtra("recipe_id", -1);
        if(recipeId != -1)
            getData();
        initEvent();
    }


    private void getData() {
        String url = FrServerConfig.getCommentByRecipe(recipeId, lastid);
        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        GetRequest request = new GetRequest(url, FrApplication.getInstance().getToken(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                if(res.has("data")) {
                    try {
                        if(lastid == -1)
                            hideLoading(false, "");
                        else
                            scrollView.setCompleteMore();
                        JSONArray data = res.getJSONArray("data");
                        processData(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideLoading(false, getResources().getString(R.string.network_error));
            }
        });
        FrRequest.getInstance().request(request);
    }

    private void processData(JSONArray data) {
        List<Comment> add  = new Gson().fromJson(data.toString(), new TypeToken<List<Comment>>(){}.getType());
        if(comments ==  null)    comments = new ArrayList<>();
        if(add != null && add.size() > 0) {
            lastid = add.get(add.size() - 1).getId();
            comments.addAll(add);
        }else {
            Toast.makeText(this, "没有多余的评论!", Toast.LENGTH_SHORT).show();
            scrollView.setNoMore();
        }
        if(commentCardAdapter == null) {
            commentCardAdapter = new CommentCardAdapter(this, comments, rootView);
            frCommentRecyclerView.setAdapter(commentCardAdapter);
        }else
            commentCardAdapter.notifyDataSetChanged();
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

        scrollView = (BorderScrollView) findViewById(R.id.scrollView);
    }

    private void initEvent() {
        backBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        commentBtn.setOnClickListener(this);
        scrollView.setOnBorderListener(new BorderScrollView.OnBorderListener() {
            @Override
            public void onBottom() {
                getData();
            }

            @Override
            public void onTop() {

            }
        });
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
                editText.setText("");
                if(content.equals("")){
                    Toast.makeText(this,"回复内容不得为空><",Toast.LENGTH_LONG).show();
                }else{
                    if(cancelBtn.getVisibility()==View.GONE){
                        Toast.makeText(this,"回复内容为： "+content,Toast.LENGTH_LONG).show();
                        comment(null, content);
                    }else{
                        Toast.makeText(this,"回复用户的ID： "+ replyUserID.getText() + " 回复评论的ID： "+replyCommentID.getText() + " 回复内容为： "+content, Toast.LENGTH_LONG).show();
                        comment(replyUserID.getText().toString(), content);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void comment(String id, String content) {
        //modify the view
        Author author = FrApplication.getInstance().getAuthor();
        if(author == null) {
            Toast.makeText(this, "请登录！！！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
            return;
        }
        String url = FrServerConfig.getCreateCommentUrl();
        JSONObject params = null;
        try {
            params = new JSONObject();
            params.put("reply", id);
            params.put("recipe", String.valueOf(recipeId));
            params.put("content", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(params.toString());
        PostRequest request = new PostRequest(url, FrApplication.getInstance().getToken(), params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject res) {
                Toast.makeText(CommentActivity.this, "评论成功!", Toast.LENGTH_SHORT).show();
                if(res.has("data")) {
                    try {
                        Comment comment = new Gson().fromJson(res.getJSONObject("data").toString(), Comment.class);
                        comments.add(0, comment);
                        commentCardAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        FrRequest.getInstance().request(request);
    }
}
