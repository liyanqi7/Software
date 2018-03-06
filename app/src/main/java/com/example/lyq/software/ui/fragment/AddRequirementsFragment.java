package com.example.lyq.software.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.lyq.software.R;
import com.example.lyq.software.ui.activity.AddRequirementsActivity;
import com.example.lyq.software.ui.adapter.AddRequirementAdapter;
import com.example.lyq.software.ui.bean.Requirement;
import java.util.ArrayList;
import java.util.List;

public class AddRequirementsFragment extends Fragment {
    private List<Requirement> requirementList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_requirements,null,false);
        requirementList.clear();//清除List
        initRequirement();
        AddRequirementAdapter adapter = new AddRequirementAdapter(getContext(),R.layout.item_add_requirement,requirementList);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Requirement requirement = requirementList.get(position);
                Intent intent = new Intent();
                intent.putExtra("type",requirement.getType());
                intent.setClass(getContext(), AddRequirementsActivity.class);
                startActivity(intent);
//                Toast.makeText(getContext(), requirement.getType(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void initRequirement() {
        Requirement typeOne = new Requirement("网站建设","网站定制、模板建站、手机网站",R.mipmap.icon_back_right);
        requirementList.add(typeOne);
        Requirement typeTwo = new Requirement("APP开发","综合商城、餐饮外卖、社交聊天",R.mipmap.icon_back_right);
        requirementList.add(typeTwo);
        Requirement typeThree = new Requirement("微信开发","小程序开发、微信商场、微信餐饮",R.mipmap.icon_back_right);
        requirementList.add(typeThree);
        Requirement typeFour = new Requirement("UI设计","banner设计、icon设计、单页设计",R.mipmap.icon_back_right);
        requirementList.add(typeFour);
        Requirement typeFive = new Requirement("H5开发","H5开发",R.mipmap.icon_back_right);
        requirementList.add(typeFive);
        Requirement typeSix = new Requirement("解决方案","解决方案",R.mipmap.icon_back_right);
        requirementList.add(typeSix);
        Requirement typeSeven = new Requirement("数据服务","数据服务",R.mipmap.icon_back_right);
        requirementList.add(typeSeven);
        Requirement typeEight = new Requirement("安全服务","安全服务",R.mipmap.icon_back_right);
        requirementList.add(typeEight);
        Requirement typeNine = new Requirement("测试服务","测试服务",R.mipmap.icon_back_right);
        requirementList.add(typeNine);
        Requirement typeTen = new Requirement("软件SaaS","软件SaaS",R.mipmap.icon_back_right);
        requirementList.add(typeTen);
        Requirement typeEleven = new Requirement("域名服务","域名注册、域名备案、域名解析",R.mipmap.icon_back_right);
        requirementList.add(typeEleven);
        Requirement typeTwelve = new Requirement("云服务","服务器托管、云主机、环境部署",R.mipmap.icon_back_right);
        requirementList.add(typeTwelve);
        Requirement typeThirteen = new Requirement("其他服务","其他服务",R.mipmap.icon_back_right);
        requirementList.add(typeThirteen);
    }
}
