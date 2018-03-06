package com.example.lyq.software.ui.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lyq.software.R;
import com.example.lyq.software.base.BaseActivity;
import com.example.lyq.software.lib.Constants;
import com.example.lyq.software.utils.SpUtils;

public class SetActivity extends BaseActivity implements View.OnClickListener {

    private Button exit;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        exit = (Button) findViewById(R.id.exit);
        back = (ImageView) findViewById(R.id.back);
        exit.setOnClickListener(this);
        back.setOnClickListener(this);
        initVisible();
    }

    private void initVisible() {
        String tokenId = SpUtils.getTokenId(this, Constants.TOKENID);
        Log.e("1", "onCreateView: " + tokenId);
        if (tokenId.toString().isEmpty())
        {
            exit.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("是否退出登录?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpUtils.putTokenId(getBaseContext(),Constants.TOKENID,null);
                        SpUtils.putNick(getBaseContext(),Constants.NICK,null);
                        SpUtils.putHead(getBaseContext(),Constants.HEAD,null);
                        finish();
                        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                break;
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
                break;
        }
    }
}
