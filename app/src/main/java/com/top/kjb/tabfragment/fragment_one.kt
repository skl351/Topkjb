package com.top.kjb.tabfragment

import android.Manifest
import android.app.AlertDialog
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ZoomControls
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.baidu.location.BDLocation
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.route.*
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.top.kjb.MainActivity
import com.top.kjb.R
import com.top.kjb.adapter.adapter_searchlist
import com.top.kjb.bean.Result
import com.top.kjb.bean.bean_main_item
import com.top.kjb.bean.bean_type_item
import com.top.kjb.customview.DrivingRouteOverlay
import com.top.kjb.customview.dialog.selectlocatonitem_dialog
import com.top.kjb.customview.dialog.sport_type_dialog
import com.top.kjb.location.OrientSensor
import com.top.kjb.model.MainModel
import com.top.kjb.originpack.BaseFragment
import com.top.kjb.tabfragment.newfragmentone.fragmentone_newdetail
import com.top.kjb.utils.Show_toast
import com.top.kjb.utils.Sp
import com.top.kjb.utils.Sp.status_search_dialog
import com.top.kjb.utils.Sp.status_show
import com.top.kjb.utils.Sp.status_show_dialog
import com.top.kjb.utils.functionClass
import com.top.kjb.utils.java_util
import com.yzq.zxinglibrary.android.CaptureActivity
import kotlinx.android.synthetic.main.layout_mainpage.*
import per.wsj.library.AndRatingBar
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class fragment_one : BaseFragment(), View.OnClickListener, OrientSensor.OrientCallBack {
    val mainModel: MainModel by lazy { MainModel() }
    override fun onClick(p0: View?) {
        when (p0?.id) {

            R.id.id_click_more_top -> {
                if (status_show_dialog) {
                    hide_bottom_location()
                    status_show_dialog = !status_show_dialog
                }
                var dialog = sport_type_dialog(activity!!)
                dialog.setlist(list_type)
                XPopup.Builder(getContext())
                    .popupAnimation(PopupAnimation.ScrollAlphaFromTop)
                    .isCenterHorizontal(true)
                    .asCustom(dialog)
                    .show();
            }
            R.id.id_click_erweima -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val checkCallPhonePermission =
                        ContextCompat.checkSelfPermission(activity!!, Manifest.permission.CAMERA)
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                            activity!!, arrayOf(
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ), Sp.REQUEST_CODE_camera
                        )
                        return
                    } else {
                        var intent = Intent(activity, CaptureActivity::class.java)
                        activity?.startActivityForResult(intent, Sp.REQUEST_CODE_SCAN);
                    }
                } else {
                    var intent = Intent(activity, CaptureActivity::class.java)
                    activity?.startActivityForResult(intent, Sp.REQUEST_CODE_SCAN);
                }


            }
            R.id.id_click_location, R.id.id_click_location_left_bottom -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    val checkCallPhonePermission = ContextCompat.checkSelfPermission(
                        activity!!,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                            activity!!,
                            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                            Sp.REQUEST_CODE_Location2
                        )
                        return
                    } else {
                        (activity as MainActivity).init_start()
                    }
                } else {
                    (activity as MainActivity).init_start()
                }

            }
            R.id.id_click_search -> {
                if (status_show) {
                    hidetop()
                    status_show = !status_show
                }
                if (status_show_dialog) {
                    hide_bottom_location()
                    status_show_dialog = !status_show_dialog
                }
                if (!status_search_dialog) {
                    id_RecyclerView_big.visibility = View.VISIBLE
                    functionClass.setanimvisible_tbottom(id_top_bar_2_big)
                    status_search_dialog = !status_search_dialog
                }
//                var intent = Intent(activity, SearchActivity::class.java)
//                startActivity(intent)
            }
            R.id.id_top_bar_2_big -> {

            }
            R.id.id_cancel -> {
                if (status_search_dialog) {
                    hide_editabout()
                    status_search_dialog = !status_search_dialog
                }
                if (status_show) {
                    hidetop()
                } else {
                    showtop()
                }
                status_show = !status_show
                hide_editabout()

            }
        }
    }

    override fun init_click() {
        super.init_click()
        id_click_more_top.setOnClickListener(this)
        id_click_location.setOnClickListener(this)
        id_click_erweima.setOnClickListener(this)
        id_click_location_left_bottom.setOnClickListener(this)
        id_click_search.setOnClickListener(this)
        id_top_bar_2_big.setOnClickListener(this)
        id_cancel.setOnClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_mainpage, null)
    }

    private fun init_banner() {
        mainModel.mainbannergetad(functionClass.getToken())
            .enqueue(object : retrofit2.Callback<Result<ArrayList<String>>> {
                override fun onFailure(call: Call<Result<ArrayList<String>>>, t: Throwable) {
                    println("失败" + t.toString())
                    functionClass.error_open(t.toString())
                }

                override fun onResponse(
                    call: Call<Result<ArrayList<String>>>,
                    response: Response<Result<ArrayList<String>>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        var list = bean?.result
                        id_banner.setViewUrls(activity, list, null)
                    } else {
                        Toast.makeText(activity, "bannner flag false", Toast.LENGTH_SHORT).show()
                    }
                }

            })
    }


    override fun init_view() {
        super.init_view()
        id_edit_view.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                id_RecyclerView_big.visibility = View.VISIBLE
            }

        })
        id_edit_view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var text = p0.toString()
                init_search(text)
            }

        })
    }

    open fun showtop() {
        functionClass.setanimvisible_tbottom(id_top_bar_1_big!!)
        (activity as MainActivity).showbottom()
    }

    open fun hidetop() {
        functionClass.setanimgone_totopy(id_top_bar_1_big!!)
        (activity as MainActivity).hidebottom()
    }

    // MapView 是地图主控件
    var mBaiduMap: BaiduMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id_top_bar_1.layoutParams.height = functionClass.getbarHight(activity!!)
        id_top_bar_2.layoutParams.height = functionClass.getbarHight(activity!!)
        registerBoradcastReceiver()
        init_view()
        init_refre()
        init_click()
        init_data()
        init_banner()

    }

    private fun init_refre() {

        val layoutmanager = LinearLayoutManager(activity)
        id_RecyclerView?.layoutManager = layoutmanager
    }

    var list_type = ArrayList<bean_type_item>()
    private fun init_data() {
        mainModel.selectAllGroundingSports()
            .enqueue(object : retrofit2.Callback<Result<ArrayList<bean_type_item>>> {
                override fun onFailure(
                    call: Call<Result<ArrayList<bean_type_item>>>,
                    t: Throwable
                ) {
                    functionClass.error_open(t.toString())
                }

                override fun onResponse(
                    call: Call<Result<ArrayList<bean_type_item>>>,
                    response: Response<Result<ArrayList<bean_type_item>>>
                ) {

                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        var list = bean?.result
                        list?.removeAt(0)
                        for (i in 0..list?.size!! - 1) {
                            val view = View.inflate(activity, R.layout.layout_type_tab, null)
                            view.findViewById<TextView>(R.id.id_text)
                                .setText(list.get(i).sportsName)
                            view.findViewById<TextView>(R.id.id_text).setTextSize(16f)
                            view.setOnClickListener(object : View.OnClickListener {
                                override fun onClick(p0: View?) {
                                    for (z in 0..id_main_tab_group.childCount - 1) {
                                        (id_main_tab_group.getChildAt(z).findViewById<View>(R.id.id_text) as TextView).setTextSize(
                                            16f
                                        )
                                        (id_main_tab_group.getChildAt(z).findViewById<View>(R.id.id_text) as TextView).setTypeface(
                                            Typeface.defaultFromStyle(Typeface.NORMAL)
                                        );
                                        (id_main_tab_group.getChildAt(z).findViewById<View>(R.id.id_text_line)).visibility =
                                            View.INVISIBLE
                                    }

                                    view.findViewById<TextView>(R.id.id_text).setTextSize(18f)
                                    view.findViewById<View>(R.id.id_text_line).visibility =
                                        View.VISIBLE
                                    (id_main_tab_group.getChildAt(i).findViewById<View>(R.id.id_text) as TextView).setTypeface(
                                        Typeface.defaultFromStyle(Typeface.BOLD)
                                    );
                                    //点击搜素
                                    init_getplacelist(list.get(i).sportsName)
                                    if (status_show_dialog) {
                                        hide_bottom_location()
                                        status_show_dialog = !status_show_dialog
                                    }

                                }
                            })
                            id_main_tab_group.addView(view)
                        }


                        list_type = list
                    } else {
                        Show_toast.showText(activity, "获取顶部分类失败")
                    }
                }

            })
    }

    private fun init_baidu(location: BDLocation) {
        var child = bmapView.getChildAt(1);
        if (child != null && (child is ImageView || child is ZoomControls)) {
            child.visibility = View.GONE
        }
        bmapView.showScaleControl(false)//不显示标尺
        bmapView.showZoomControls(false)//不显示缩放空间
        mBaiduMap = bmapView.map
        // 构建地图状态
        // 构建地图状态
        val builder = MapStatus.Builder()
        // 默认 天安门
        // 默认 天安门
        val center = LatLng(new_lat.toDouble(), new_lng.toDouble())
        // 默认 11级
        // 默认 11级
        val zoom = 19.0f
        builder.target(center).zoom(zoom)
        val mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(builder.build())

        // 设置地图状态
        // 设置地图状态
        mBaiduMap?.setMapStatus(mapStatusUpdate)
        mBaiduMap?.isMyLocationEnabled = true
        val locData = MyLocationData.Builder()
            .accuracy(location.radius) // 此处设置开发者获取到的方向信息，顺时针0-360
//            .direction(360f)
            .latitude(location.latitude)
            .longitude(location.longitude).build()
        mBaiduMap?.setMyLocationData(locData)

        bmapView.visibility = View.VISIBLE

        // 修改为自定义图层
        val currentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_location_my_icon)
        mBaiduMap?.setMyLocationConfiguration(
            MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL,
                true,
                currentMarker
            )
        )
        mBaiduMap?.setOnMapClickListener(object : BaiduMap.OnMapClickListener {
            override fun onMapClick(p0: LatLng?) {
                if (status_show) {
                    hidetop()
                } else {
                    showtop()
                }
                status_show = !status_show
                if (status_show_dialog) {
                    hide_bottom_location()
                    status_show_dialog = !status_show_dialog
                }
                if (status_search_dialog) {
                    hide_editabout()
                    status_search_dialog = !status_search_dialog
                }
            }

            override fun onMapPoiClick(p0: MapPoi?) {

            }

        })
        // 默认开启个性化样式
        // 默认开启个性化样式
        val customStyleFilePath: String? =
            getCustomStyleFilePath(activity!!, "baidu_customconfig.sty")

        // V6.0.0版本起，强烈建议使用新的个性化地图API。能够实现动态更改样式（同一地图设置不同的样式），适配多地图场景（不同的地图设置不同的样式）
        // 并且路径设置API不再要求在地图创建之前，地图创建完成之后设置即可。在地图释放时，也无需关闭个性化开关了。
        // 老版本个性化样式API功能保持兼容，但是限制较多。不建议使用。
        // V6.0.0版本起，强烈建议使用新的个性化地图API。能够实现动态更改样式（同一地图设置不同的样式），适配多地图场景（不同的地图设置不同的样式）
// 并且路径设置API不再要求在地图创建之前，地图创建完成之后设置即可。在地图释放时，也无需关闭个性化开关了。
// 老版本个性化样式API功能保持兼容，但是限制较多。不建议使用。
        bmapView.setMapCustomStylePath(customStyleFilePath)
        bmapView.setMapCustomStyleEnable(true)

        // 注册方向监听
        //        }
// 注册方向监听
        myOrientationListener = OrientSensor(activity, this)
        myOrientationListener.registerOrient()
    }

    var myCurrentX = 0f
    lateinit var myOrientationListener: OrientSensor

    private fun registerBoradcastReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Sp.location_send)
        intentFilter.addAction(Sp.searchback)
        intentFilter.addAction(Sp.select_type)
        activity?.registerReceiver(mBroadcastReceiver, intentFilter)
    }

    lateinit var new_location: BDLocation
    var flag_first = false
    var new_lat = "0"//当前
    var new_lng = "0"//当前
    var mBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                Sp.select_type -> {
                    var str = intent.getStringExtra("type")

                    for (z in 0..id_main_tab_group.childCount - 1) {
                        if (list_type.get(z).sportsName.equals(str)) {
                            (id_main_tab_group.getChildAt(z).findViewById<View>(R.id.id_text) as TextView).setTextSize(
                                18f
                            )
                            (id_main_tab_group.getChildAt(z).findViewById<View>(R.id.id_text) as TextView).setTypeface(
                                Typeface.defaultFromStyle(Typeface.BOLD)
                            )
                            (id_main_tab_group.getChildAt(z).findViewById<View>(R.id.id_text_line)).visibility =
                                View.VISIBLE
                        } else {
                            (id_main_tab_group.getChildAt(z).findViewById<View>(R.id.id_text_line)).visibility =
                                View.GONE
                            (id_main_tab_group.getChildAt(z).findViewById<View>(R.id.id_text) as TextView).setTextSize(
                                16f
                            )
                            (id_main_tab_group.getChildAt(z).findViewById<View>(R.id.id_text) as TextView).setTypeface(
                                Typeface.defaultFromStyle(Typeface.NORMAL)
                            );
                        }

                    }

                    //点击搜素
                    init_getplacelist(str)
                }
                Sp.searchback -> {
                    hide_editabout_rey()

                    var list_s = intent.getSerializableExtra("list") as ArrayList<String>
                    var item = intent.getParcelableExtra<bean_main_item>("bean_search_item")
                    item.businessHours = list_s
                    addmarker(item)


                }
                Sp.location_send -> {
                    var flag = intent.getBooleanExtra("flag", false)
                    if (!flag) {
                        if (functionClass.getetworkstate(activity!!).equals("G")) {
                            AlertDialog.Builder(activity)
                                .setTitle("定位不准确，请打开定位")
//                    .setMessage(R.string.gpsNotifyMsg) // 拒绝, 退出应用
                                .setNegativeButton("取消",
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(p0: DialogInterface?, p1: Int) {
                                            p0?.dismiss()
                                        }


                                    })
                                .setPositiveButton(
                                    "确定",
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(p0: DialogInterface?, p1: Int) {
                                            val intent =
                                                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                            activity?.startActivityForResult(intent, Sp.REQUEST_GPS)
                                        }

                                    })
                                .setCancelable(false)
                                .show()
                        } else {
                            Toast.makeText(activity, "定位失败，建议打开gps并重新定位", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }


                    var location = intent.getParcelableExtra<BDLocation>("locationinfo")
                    new_location = location
                    new_lat = location.latitude.toString()
                    new_lng = location.longitude.toString()
                    var locationplace = location.district
                    id_locationplace.setText(locationplace)
                    if (!flag_first) {
                        init_baidu(location)
                        flag_first = true
                    } else {
                        requestlocation(location)
                    }


                }

            }
        }
    }

    private fun requestlocation(location: BDLocation) {
        val builder = MapStatus.Builder()
        // 默认 天安门
        // 默认 天安门
        val center = LatLng(location.latitude, location.longitude)
        // 默认 11级
        // 默认 11级
        val zoom = 19.0f
        builder.target(center).zoom(zoom)
        val mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(builder.build())

        // 设置地图状态
        // 设置地图状态
        mBaiduMap?.setMapStatus(mapStatusUpdate)
        val locData = MyLocationData.Builder()
            .accuracy(location.radius) // 此处设置开发者获取到的方向信息，顺时针0-360
//            .direction(360f)
            .latitude(location.latitude)
            .longitude(location.longitude).build()
        mBaiduMap?.setMyLocationData(locData)
    }

    lateinit var mMarkerC: Marker;

    private fun addmarker(item: bean_main_item) {
        mBaiduMap?.clear()
        var b_lat = item.lat.toDouble()
        var b_lng = item.lng.toDouble()
        val latLngC = LatLng(b_lat, b_lng)
        val markerOptionsC = MarkerOptions()
            .position(latLngC) // 经纬度
            .icon(
                BitmapDescriptorFactory.fromBitmap(
                    java_util().getBitmap(
                        activity,
                        R.mipmap.icon_location_flag
                    )
                )
            ) // 设置 Marker 覆盖物的图标
            .perspective(true) // 设置是否开启 marker 覆盖物近大远小效果，默认开启
            .anchor(0.5f, 0f) // 设置 marker 覆盖物的锚点比例，默认（0.5f, 1.0f）水平居中，垂直下对齐
//            .rotate(30f) // 设置 marker 覆盖物旋转角度，逆时针
            .zIndex(3) // 设置 marker 覆盖物的 zIndex

        val view = View.inflate(activity, R.layout.layout_map_point, null)
        view.findViewById<TextView>(R.id.id_location_text).setText(item.name)
        val markerOptionsC_2 = MarkerOptions()
            .position(latLngC) // 经纬度
            .icon(
                BitmapDescriptorFactory.fromView(view)
            ) // 设置 Marker 覆盖物的图标
            .perspective(true) // 设置是否开启 marker 覆盖物近大远小效果，默认开启
            .anchor(0.5f, 2f) // 设置 marker 覆盖物的锚点比例，默认（0.5f, 1.0f）水平居中，垂直下对齐

            .zIndex(-100) // 设置 marker 覆盖物的 zIndex
        var asd = mBaiduMap!!.addOverlay(markerOptionsC_2) as Marker
        var zxc = mBaiduMap!!.addOverlay(markerOptionsC) as Marker


        val builder = MapStatus.Builder()

        val zoom = 19.0f
        builder.target(latLngC).zoom(zoom)
        val mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(builder.build())

        // 设置地图状态
        // 设置地图状态
        mBaiduMap?.setMapStatus(mapStatusUpdate)
        mBaiduMap?.setOnMarkerClickListener(object : BaiduMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker?): Boolean {
                if (marker == asd || marker == zxc) {
                    mBaiduMap?.clear()
                    click_point(latLngC, item)
                }
                return true;
            }

        })
    }

    private fun addmarkers(list: ArrayList<bean_main_item>) {
        mBaiduMap?.clear()
        for (i in 0..list.size - 1) {
            val latLng = LatLng(list.get(i).lat.toDouble(), list.get(i).lng.toDouble())//那个点
            val markerOptionsC = MarkerOptions()
                .position(latLng) // 经纬度
                .icon(
                    BitmapDescriptorFactory.fromBitmap(
                        java_util().getBitmap(
                            activity,
                            R.mipmap.icon_location_flag
                        )
                    )
                ) // 设置 Marker 覆盖物的图标
                .perspective(true) // 设置是否开启 marker 覆盖物近大远小效果，默认开启
                .anchor(0.5f, 0f) // 设置 marker 覆盖物的锚点比例，默认（0.5f, 1.0f）水平居中，垂直下对齐
                .zIndex(3) // 设置 marker 覆盖物的 zIndex

            val view = View.inflate(activity, R.layout.layout_map_point, null)
            view.findViewById<TextView>(R.id.id_location_text).setText(list.get(i).name)
            val markerOptionsC_2 = MarkerOptions()
                .position(latLng) // 经纬度
                .icon(
                    BitmapDescriptorFactory.fromView(view)
                ) // 设置 Marker 覆盖物的图标
                .perspective(true) // 设置是否开启 marker 覆盖物近大远小效果，默认开启
                .anchor(0.5f, 2f) // 设置 marker 覆盖物的锚点比例，默认（0.5f, 1.0f）水平居中，垂直下对齐

                .zIndex(-100) // 设置 marker 覆盖物的 zIndex
            var asd = mBaiduMap!!.addOverlay(markerOptionsC_2) as Marker
            var zxc = mBaiduMap!!.addOverlay(markerOptionsC) as Marker

            val builder = MapStatus.Builder()
            val zoom = 15.0f
            builder.zoom(zoom)
            val mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(builder.build())

            // 设置地图状态
            // 设置地图状态
            mBaiduMap?.setMapStatus(mapStatusUpdate)
            mBaiduMap?.setOnMarkerClickListener(object : BaiduMap.OnMarkerClickListener {
                override fun onMarkerClick(marker: Marker?): Boolean {
                    if (marker == asd || marker == zxc) {
                        mBaiduMap?.clear()
                        click_point(latLng, list.get(i))
                    }
                    return true;
                }

            })
        }


    }

    private fun click_point(
        latLngC: LatLng,
        item: bean_main_item
    ) {


        var mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(object : OnGetRoutePlanResultListener {
            override fun onGetIndoorRouteResult(p0: IndoorRouteResult?) {

            }

            override fun onGetTransitRouteResult(p0: TransitRouteResult?) {

            }

            override fun onGetDrivingRouteResult(result: DrivingRouteResult?) {
                if (result != null && result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) { // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
// result.getSuggestAddrInfo()
                    Toast.makeText(
                        activity,
                        "起终点或途经点地址有岐义,通过 result.getSuggestAddrInfo()接口获取建议查询信息",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    Toast.makeText(
                        activity,
                        "抱歉，未找到结果",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    if (result.routeLines.size > 1) {
                        // 获取选中的路线
                        var mRouteLine = result.getRouteLines().get(0)
                        val overlay =
                            DrivingRouteOverlay(
                                mBaiduMap
                            )
                        overlay.setData(mRouteLine)
                        overlay.addToMap()
                        overlay.zoomToSpan()
                    }
                }
            }

            override fun onGetWalkingRouteResult(p0: WalkingRouteResult?) {

            }

            override fun onGetMassTransitRouteResult(p0: MassTransitRouteResult?) {

            }

            override fun onGetBikingRouteResult(p0: BikingRouteResult?) {

            }

        })

        var lnew = LatLng(new_lat.toDouble(), new_lng.toDouble())
        var stNode = PlanNode.withLocation(lnew)
        var enNode = PlanNode.withLocation(latLngC)
        mSearch.drivingSearch(
            (DrivingRoutePlanOption())
                .from(stNode)
                .to(enNode)
        );
        if (!status_show_dialog) {
            show_bottom_location(item)
            status_show_dialog = !status_show_dialog
        }

        //

    }

    private fun show_bottom_location(item1: bean_main_item) {

        var view = View.inflate(activity, R.layout.layout_location_bottom_view, null)
        val id_click_big = view.findViewById<View>(R.id.id_click_big)
        val id_all_peoples = view.findViewById<TextView>(R.id.id_all_peoples)
        val id_all_area = view.findViewById<TextView>(R.id.id_all_area)
        val id_years = view.findViewById<TextView>(R.id.id_years)
        val id_time_long = view.findViewById<TextView>(R.id.id_time_long)
        val id_place_name = view.findViewById<TextView>(R.id.id_place_name)
        val id_star_view = view.findViewById<AndRatingBar>(R.id.id_star_view)
        val id_show_score = view.findViewById<TextView>(R.id.id_show_score)
        val id_qixie = view.findViewById<TextView>(R.id.id_qixie)
        val id_click_gotoother = view.findViewById<View>(R.id.id_click_gotoother)
        id_click_gotoother.setOnClickListener { view: View? ->
            val dialog = selectlocatonitem_dialog(context!!)
            dialog.setbeango(item1)
            XPopup.Builder(context)
                .hasShadowBg(false)
                .atView(id_click_gotoother)
                .asCustom(dialog)
                .show()
        }
        id_click_big.setOnClickListener { view: View? ->
            val intent = Intent(
                context,
                fragmentone_newdetail::class.java
            )
            intent.putExtra("id", item1.gymID)
            intent.putExtra("lat", item1.lat)
            intent.putExtra("lng", item1.lng)
            context!!.startActivity(intent)
        }

        try {
            id_place_name.setText(item1.name)
            id_star_view.rating = item1.comprehensiveScore
            id_show_score.text = "" + item1.comprehensiveScore
            id_years.setText(item1.openYear + "年")
            id_all_area.text = "总面积" + item1.area + "m²"
            id_all_peoples.text = "容纳" + item1.seatingCapacity + "人"
            id_qixie.text = "器械" + item1.instrumentTapNum + "种"
            id_time_long.setText(
                item1.businessHours.get(0).toString() + "  " + item1.businessHours.get(
                    1
                )
            )
        } catch (e: Exception) {
        }
        (activity as MainActivity).showbottom_location(view)
    }

    open fun hide_bottom_location() {
        (activity as MainActivity).hidebottom_location()
    }

    fun hide_editabout() {
        java_util().hintKeyBoard(activity)
        functionClass.setanimgone_totopy(id_top_bar_2_big)
    }

    fun hide_editabout_rey() {
        java_util().hintKeyBoard(activity)
        id_RecyclerView_big.visibility = View.GONE
    }

    override fun onResume() {

        // 在activity执行onResume时必须调用mMapView. onResume ()
        bmapView.onResume()
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {

        // 在activity执行onPause时必须调用mMapView. onPause ()
        bmapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        // 在activity执行onDestroy时必须调用mMapView.onDestroy()

        mBaiduMap?.setMyLocationEnabled(false);
        bmapView?.onDestroy();
        super.onDestroy()
        myOrientationListener.unregisterOrient()
    }


    fun init_getplacelist(sportsName: String) {
        mainModel.gymnasiumgymnasiumList(
            functionClass.getToken(),
            new_lat.toDouble(),
            new_lng.toDouble(),
            sportsName
        )
            .enqueue(object : retrofit2.Callback<Result<ArrayList<bean_main_item>>> {
                override fun onFailure(
                    call: Call<Result<ArrayList<bean_main_item>>>,
                    t: Throwable
                ) {
                    functionClass.error_open(t.toString())
                }

                override fun onResponse(
                    call: Call<Result<ArrayList<bean_main_item>>>,
                    response: Response<Result<ArrayList<bean_main_item>>>
                ) {
                    var bean = response?.body()
                    if ("success".equals(bean?.flag)) {
                        if (bean?.result?.size == 0) {
                            Show_toast.showText(activity, "没有查到对应场馆")
                            return
                        }
                        addmarkers(bean?.result!!)

                    } else {
                        Show_toast.showText(activity, "获取失败")
                    }

                }

            })


    }

    lateinit var list_search: ArrayList<bean_main_item>
    lateinit var adapter_search: adapter_searchlist
    private fun init_search(text: String) {
        mainModel.gymnasiumgymLikeSearch(functionClass.getToken(), text)
            .enqueue(object :
                retrofit2.Callback<com.top.kjb.bean.Result<ArrayList<bean_main_item>>> {
                override fun onFailure(
                    call: Call<com.top.kjb.bean.Result<ArrayList<bean_main_item>>>,
                    t: Throwable
                ) {
                    functionClass.error_open(t.toString())
                    Show_toast.showText(activity, "查询结果不存在")
                    id_no_data.visibility = View.VISIBLE
                    try {
                        list_search.clear()
                        adapter_search.notifyDataSetChanged()
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
                        list_search = bean?.result!!
                        if (list_search.size == 0) {
                            id_no_data.visibility = View.VISIBLE
                        } else {
                            id_no_data.visibility = View.GONE
                        }
                        adapter_search = adapter_searchlist(activity!!, list_search)
                        id_RecyclerView.adapter = adapter_search

                    } else {
                        try {
                            list_search.clear()
                            adapter_search.notifyDataSetChanged()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        id_no_data.visibility = View.VISIBLE
                    }

                }

            })
    }

    override fun Orient(orient: Int) {
//        println("滚动的角度"+orient)
        val locData = MyLocationData.Builder()
            .accuracy(new_location.radius) // 此处设置开发者获取到的方向信息，顺时针0-360
            .direction(orient.toFloat())
            .latitude(new_location.latitude)
            .longitude(new_location.longitude).build()
        mBaiduMap?.setMyLocationData(locData)
    }

    private fun getCustomStyleFilePath(
        context: Context,
        customStyleFileName: String
    ): String? {
        var outputStream: FileOutputStream? = null
        var inputStream: InputStream? = null
        var parentPath: String? = null
        try {
            inputStream = context.assets.open("baiduconfig/$customStyleFileName")
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            parentPath = context.filesDir.absolutePath
            val customStyleFile = File("$parentPath/$customStyleFileName")
            if (customStyleFile.exists()) {
                customStyleFile.delete()
            }
            customStyleFile.createNewFile()
            outputStream = FileOutputStream(customStyleFile)
            outputStream.write(buffer)
        } catch (e: IOException) {
            Log.e("CustomMapDemo", "Copy custom style file failed", e)
        } finally {
            try {
                inputStream?.close()
                outputStream?.close()
            } catch (e: IOException) {
                Log.e("CustomMapDemo", "Close stream failed", e)
                return null
            }
        }
        return "$parentPath/$customStyleFileName"
    }
}