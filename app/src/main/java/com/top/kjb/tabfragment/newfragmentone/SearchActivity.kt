package com.top.kjb.tabfragment.newfragmentone

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.top.kjb.R
import com.top.kjb.adapter.adapter_searchlist
import com.top.kjb.bean.bean_main_item
import com.top.kjb.bean.bean_search_item
import com.top.kjb.model.MainModel
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_searchactivity_view.*
import retrofit2.Call
import retrofit2.Response

class SearchActivity : BaseActivity() {
    val mainModel: MainModel by lazy { MainModel() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_searchactivity_view)
        init_view()
        init_refre()
    }

    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(this)
        id_RecyclerView?.layoutManager = layoutmanager
    }

    override fun init_view() {
        super.init_view()
        list = ArrayList()
        id_edit_view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var text = p0.toString()
                init_data(text)
            }

        })
    }

    lateinit var list: ArrayList<bean_main_item>
    lateinit var adapter: adapter_searchlist
    private fun init_data(text: String) {
        mainModel.gymnasiumgymLikeSearch(functionClass.getToken(), text)
            .enqueue(object :
                retrofit2.Callback<com.top.kjb.bean.Result<ArrayList<bean_main_item>>> {
                override fun onFailure(
                    call: Call<com.top.kjb.bean.Result<ArrayList<bean_main_item>>>,
                    t: Throwable
                ) {
                    functionClass.error_open(t.toString())
                    Show_toast.showText(this@SearchActivity, "查询结果不存在")
                    id_no_data.visibility = View.VISIBLE
                    try {
                        list.clear()
                        adapter.notifyDataSetChanged()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onResponse(
                    call: Call<com.top.kjb.bean.Result<ArrayList<bean_main_item>>>,
                    response: Response<com.top.kjb.bean.Result<ArrayList<bean_main_item>>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        list = bean?.result!!
                        if (list.size == 0) {
                            id_no_data.visibility = View.VISIBLE
                        } else {
                            id_no_data.visibility = View.GONE
                        }
                        adapter = adapter_searchlist(this@SearchActivity, list)
                        id_RecyclerView.adapter = adapter

                    } else {
                        try {
                            list.clear()
                            adapter.notifyDataSetChanged()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        id_no_data.visibility = View.VISIBLE
                    }

                }

            })
    }
}