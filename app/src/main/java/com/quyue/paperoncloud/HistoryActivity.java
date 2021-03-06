package com.quyue.paperoncloud;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.quyue.paperoncloud.adapter.RecyclerViewAdapter;
import com.quyue.paperoncloud.db.data.DataBaseConstants;
import com.quyue.paperoncloud.db.entity.VoiceHistory;
import com.quyue.paperoncloud.util.Util;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private final static String TAG = "HistoryActivity";
    private RecyclerView mHistoryRecyclerView;

    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        mHistoryRecyclerView = findViewById(R.id.history_recyclerView);
        mLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);//这里我们使用默认的线性布局管理器,将其设为垂直显示
        mHistoryRecyclerView.setLayoutManager(mLayoutManager);//设置布局管理器
        mAdapter = new RecyclerViewAdapter(this, DataBaseConstants.voiceHistoryList, R.layout.recyclerviewadapter_item, true);//实例化适配器
        mHistoryRecyclerView.setAdapter(mAdapter);//设置适配器
        mAdapter.setOnRecyclerViewItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                //隐性启动VoiceActivity
                Intent intent = new Intent();
                intent.setAction(VoiceActivity.VOICE_ACTIVITY_INTENT_ACTION);
                TextView idTextView = view.findViewById(R.id.id_tv);
                int id = Integer.valueOf(idTextView.getText().toString().trim());
                Bundle bundle=new Bundle();
                VoiceHistory voiceHistory=(VoiceHistory)(Util.getEntityFromList(DataBaseConstants.voiceHistoryList,id));

                bundle.putSerializable(VoiceActivity.VOICE_ACTIVITY_INTENT_Extras_OBJ_NAME, voiceHistory.getVoiceResource());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view) {
                return false;
            }
        });

    }


    public void callBackBtn(View view) {
        this.finish();
    }
}
