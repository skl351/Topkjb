package com.top.kjb.Userabout

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.top.kjb.R
import com.top.kjb.bean.Result
import com.top.kjb.bean.beanuserlogin
import com.top.kjb.model.LoginModel
import com.top.kjb.originpack.BaseActivity
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.functionClass
import kotlinx.android.synthetic.main.layout_loginactivity.*
import retrofit2.Call
import retrofit2.Response

class LoginActivity : BaseActivity(), View.OnClickListener {

    val loginModel: LoginModel by lazy { LoginModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_loginactivity)
        init_click()
        init_view()
    }

    override fun init_view() {
        super.init_view()
        id_phone_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var text = s.toString()
                if (text.length == 11) {
                    id_click_send_sms.isEnabled = true
                    id_click_send_sms.setTextColor(resources.getColor(R.color.black))
                } else {
                    id_click_send_sms.isEnabled = false
                    id_click_send_sms.setTextColor(resources.getColor(R.color.gray))
                }
                loginbutton_check()
            }

        })
        id_yzm_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                loginbutton_check()
            }

        })
    }

    private fun loginbutton_check() {
        var phone = id_phone_edit.text.toString()
        var yzm = id_yzm_edit.text.toString()
        if (phone.length == 11 && yzm.length == 6) {
            id_click_login.isEnabled = true
            id_click_login.setTextColor(resources.getColor(R.color.black))
        } else {
            id_click_login.isEnabled = false
            id_click_login.setTextColor(resources.getColor(R.color.gray))
        }
    }

    override fun init_click() {
        super.init_click()
        id_click_send_sms.setOnClickListener(this)
        id_click_login.setOnClickListener(this)
        id_click_yzm.setOnClickListener(this)
        id_click_password.setOnClickListener(this)
        id_click_forget_password.setOnClickListener(this)
        id_top.findViewById<View>(R.id.id_back).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.id_click_login -> {
                init_login()
            }
            R.id.id_click_send_sms -> {
                init_sendmsm()
            }
            R.id.id_back -> {
                onBackPressed()
            }
            R.id.id_click_forget_password -> {
                val intent = Intent(this, Forgetpassword::class.java)
                startActivity(intent)
            }
            R.id.id_click_yzm -> {
                id_click_yzm_text.setTextColor(resources.getColor(R.color.black))
                id_click_password_text.setTextColor(resources.getColor(R.color.gray))
                id_yzm_line.visibility = View.VISIBLE
                id_password_line.visibility = View.GONE
                id_yzm_big.visibility = View.VISIBLE
                id_password_big.visibility = View.GONE
            }
            R.id.id_click_password -> {
                id_click_yzm_text.setTextColor(resources.getColor(R.color.gray))
                id_click_password_text.setTextColor(resources.getColor(R.color.black))
                id_yzm_line.visibility = View.GONE
                id_password_line.visibility = View.VISIBLE
                id_yzm_big.visibility = View.GONE
                id_password_big.visibility = View.VISIBLE
            }
        }
    }

    fun init_login() {
        var phone = id_phone_edit.text.toString()
        var yzm = id_yzm_edit.text.toString()

        loginModel.phonelogin(phone, yzm)
            .enqueue(object : retrofit2.Callback<Result<beanuserlogin>> {
                override fun onFailure(call: Call<Result<beanuserlogin>>, t: Throwable) {
                    println("失败" + t.toString())
                    Show_toast.showText(this@LoginActivity,"验证码错误")
                }

                override fun onResponse(
                    call: Call<Result<beanuserlogin>>,
                    response: Response<Result<beanuserlogin>>
                ) {
                    println("登录成功" + response?.body()?.result)
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {


                        functionClass.setToken(bean?.result?.taken.toString())
                        Show_toast.showText(this@LoginActivity, "登录成功")
                        var intent=Intent(Sp.loginsuccess)
                        sendBroadcast(intent)
                        finish()

                    } else {
                        Show_toast.showText(this@LoginActivity, "登录失败")

                    }
                }

            })
    }

    private fun init_sendmsm() {
        var phone = id_phone_edit.text.toString()

        loginModel.phonegetSMS(phone).enqueue(object : retrofit2.Callback<Result<String>> {
            override fun onFailure(call: Call<Result<String>>, t: Throwable) {
                println("失败" + t.toString())
            }

            override fun onResponse(
                call: Call<Result<String>>,
                response: Response<Result<String>>
            ) {
                println("获取验证码" + response?.body()?.result)
                var bean = response?.body()
                if ("success".equals(bean?.flag)) {
                    Toast.makeText(this@LoginActivity, bean?.result, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, "发送验证码失败", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
    }
}