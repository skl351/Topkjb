package com.top.kjb.tabfragment.newfragmentone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.top.kjb.R
import com.top.kjb.originpack.BaseFragment

class fragment_one : BaseFragment() ,View.OnClickListener{
    override fun onClick(p0: View?) {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragmentone, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}