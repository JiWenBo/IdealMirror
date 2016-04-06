package com.example.dllo.idealmirror.fragment;
import android.widget.TextView;
import com.example.dllo.idealmirror.R;
import com.example.dllo.idealmirror.base.BaseFragment;

/**
 * Created by dllo on 16/4/5.
 */
public class ShareFragmnet extends BaseFragment{
    private  TextView titles,subtitle,smalltitle;
    private String _titile,_subtitle,_smalltitle;

    public void ShareFragmnets(String titlke,String s,String ds) {
        _titile = titlke;
        _subtitle = s;
        _smalltitle = ds;
    }

    @Override
    public int getLayout() {

        return R.layout.fragment_share;

    }
    @Override
    protected  void initView() {

        subtitle = bindView(R.id.subtitle);
        titles = bindView(R.id.titles);
        smalltitle = bindView(R.id.smalltitle);
    }
    @Override
    protected void initData() {
        smalltitle.setText(_smalltitle);
    }
    public static ShareFragmnet getFragment(){
        ShareFragmnet shareFragmnet = new ShareFragmnet();
        return shareFragmnet;
    }
}
