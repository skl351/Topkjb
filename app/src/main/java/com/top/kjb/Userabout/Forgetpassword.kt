package com.top.kjb.Userabout

import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.top.kjb.R
import com.top.kjb.originpack.BaseActivity
import kotlinx.android.synthetic.main.layout_forgetpassword.*

class Forgetpassword: BaseActivity() , View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.id_back->{
                onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_forgetpassword)
        init_click()
        init_view()

    }


    override fun init_click() {
        super.init_click()
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
    }
    override fun init_view() {
        super.init_view()
        id_password_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var text=s.toString()
                if (text.length==11){
                    id_click_send_sms.isEnabled=true
                    id_click_send_sms.setTextColor(resources.getColor(R.color.black))
                }else{
                    id_click_send_sms.isEnabled=false
                    id_click_send_sms.setTextColor(resources.getColor(R.color.gray))
                }
            }

        })
    }
}