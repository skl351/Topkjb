package com.top.kjb.adapter.wuxianhuadongtab

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_sports_list_item
import com.top.kjb.model.SportsModel
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.functionClass
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by ruiwen
 * Data:2018/8/16 0016
 * Email:1054750389@qq.com
 * Desc:
 */
class GoodsFragment : BaseFragment() {
    private var mAdapter: GoodsAdapter? = null
    private var type = 0
    override fun initView() {
        if (arguments != null) {
            type = arguments!!.getInt("type")
        }
        mAdapter = GoodsAdapter(_mActivity)
        goodsRv.layoutManager = LinearLayoutManager(_mActivity)
        goodsRv.clipToPadding = false
        getListData(type)
    }

    val sportsModel: SportsModel by lazy { SportsModel() }
    override fun setData() {}
    private fun getListData(type: Int) {

        sportsModel.togetherLogsearchTogetherLog(functionClass.getToken(), type)
            .enqueue(object : retrofit2.Callback<Result<ArrayList<bean_sports_list_item>>> {
                override fun onFailure(
                    call: Call<Result<ArrayList<bean_sports_list_item>>>,
                    t: Throwable
                ) {

                }

                override fun onResponse(
                    call: Call<Result<ArrayList<bean_sports_list_item>>>,
                    response: Response<Result<ArrayList<bean_sports_list_item>>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        if (bean?.result?.size==0){
                            id_nodate.visibility=View.VISIBLE
                        }else{
                            id_nodate.visibility=View.GONE
                        }
                        mAdapter!!.setNewData(bean?.result)
                        goodsRv.adapter=mAdapter
                    } else {
                        Show_toast.showText(mContext,"sports false")
                    }
                }

            })
    }

    companion object {
        fun getInstance(type: Int): GoodsFragment {
            val bundl = Bundle()
            bundl.putInt("type", type)
            val goodsFragment = GoodsFragment()
            goodsFragment.arguments = bundl
            return goodsFragment
        }
    }


}