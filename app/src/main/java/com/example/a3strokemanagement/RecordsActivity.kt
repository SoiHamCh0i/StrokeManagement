package com.example.a3strokemanagement

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.a3strokemanagement.databinding.ActivityRecordsBinding
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityRecordsBinding

class RecordsActivity : AppCompatActivity() {

    private lateinit var firebaseDataBase: FirebaseDatabase
    private lateinit var realtimeX: DatabaseReference
    private lateinit var accelXdata: DatabaseReference
    private lateinit var accelYdata: DatabaseReference
    private lateinit var accelZdata: DatabaseReference
    private lateinit var gyroXdata: DatabaseReference
    private lateinit var gyroYdata: DatabaseReference
    private lateinit var gyroZdata: DatabaseReference
    private lateinit var tempMPUdata: DatabaseReference
    private lateinit var tempNTCdata: DatabaseReference
    private lateinit var AD8232data: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordsBinding.inflate(layoutInflater)
        setContentView(binding.root)
//thiết lập status bar và navigation bar của máy
        window.statusBarColor = ContextCompat.getColor(this@RecordsActivity, R.color.Yellow_Pastel)
//thiết lập action bar
        setSupportActionBar(binding.tbHust)
        supportActionBar?.title = "Records"
//thiết lập bottom navigation view
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bot_nav_menu)
        bottomNavigationView.selectedItemId = R.id.bot_records
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bot_records -> true
                R.id.bot_main -> {
                    val animationBundle = ActivityOptions.makeCustomAnimation(
                        this@RecordsActivity,
                        R.anim.slide_left,
                        R.anim.slide_right
                    ).toBundle()
                    startActivity(
                        Intent(applicationContext, MainActivity::class.java),
                        animationBundle
                    )
                    finish()
                    true
                }

                R.id.bot_setting -> {
                    val animationBundle = ActivityOptions.makeCustomAnimation(
                        this@RecordsActivity,
                        R.anim.slide_in_from_left,
                        R.anim.slide_in_from_right
                    ).toBundle()
                    startActivity(
                        Intent(applicationContext, SettingActivity::class.java),
                        animationBundle
                    )
                    finish()
                    true
                }

                else -> false
            }
        }
//thiết lập line chart
        firebaseDataBase = FirebaseDatabase.getInstance()
        realtimeX = firebaseDataBase.getReference("Time/realtime")
        accelXdata = firebaseDataBase.getReference("MPU6050/accX_pin")
        accelYdata = firebaseDataBase.getReference("MPU6050/accY_pin")
        accelZdata = firebaseDataBase.getReference("MPU6050/accZ_pin")
        gyroXdata = firebaseDataBase.getReference("MPU6050/gyroX_pin")
        gyroYdata = firebaseDataBase.getReference("MPU6050/gyroY_pin")
        gyroZdata = firebaseDataBase.getReference("MPU6050/gyroZ_pin")
        tempMPUdata = firebaseDataBase.getReference("MPU6050/tempC_pin")
        tempNTCdata = firebaseDataBase.getReference("NTC10K/tempC_pin")
        AD8232data = firebaseDataBase.getReference("AD8232/Output_pin")
        retrieveData()
    }

    private fun retrieveData() {
        val arrXAccel = ArrayList<Entry>()
        val arrYAccel = ArrayList<Entry>()
        val arrZAccel = ArrayList<Entry>()
        val arrXGyro = ArrayList<Entry>()
        val arrYGyro = ArrayList<Entry>()
        val arrZGyro = ArrayList<Entry>()
        val arrTempMPU = ArrayList<Entry>()
        val arrTempNTC = ArrayList<Entry>()
        val arrAD8232 = ArrayList<Entry>()
        var yAccelX = 0f
        var yAccelY = 0f
        var yAccelZ = 0f
        var yGyroX = 0f
        var yGyroY = 0f
        var yGyroZ = 0f
        var yTempMPU = 0f
        var yTempNTC = 0f
        var yAD8232 = 0f

        realtimeX.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                accelXdata.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (arrXAccel.size == 15)
                            arrXAccel.removeAt(0)
                        yAccelX = snapshot.getValue().toString().toFloat()
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                accelYdata.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (arrYAccel.size == 15)
                            arrYAccel.removeAt(0)
                        yAccelY = snapshot.getValue().toString().toFloat()
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                accelZdata.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (arrZAccel.size == 15)
                            arrZAccel.removeAt(0)
                        yAccelZ = snapshot.getValue().toString().toFloat()
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                gyroXdata.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (arrXGyro.size == 15)
                            arrXGyro.removeAt(0)
                        yGyroX = snapshot.getValue().toString().toFloat()
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                gyroYdata.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (arrYGyro.size == 15)
                            arrYGyro.removeAt(0)
                        yGyroY = snapshot.getValue().toString().toFloat()
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                gyroZdata.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (arrZGyro.size == 15)
                            arrZGyro.removeAt(0)
                        yGyroZ = snapshot.getValue().toString().toFloat()
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                tempMPUdata.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (arrTempMPU.size == 15)
                            arrTempMPU.removeAt(0)
                        yTempMPU = snapshot.getValue().toString().toFloat()
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                tempNTCdata.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (arrTempNTC.size == 15)
                            arrTempNTC.removeAt(0)
                        yTempNTC = snapshot.getValue().toString().toFloat()
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                AD8232data.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (arrAD8232.size == 15)
                            arrAD8232.removeAt(0)
                        yAD8232 = snapshot.getValue().toString().toFloat()
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
                val xTime = snapshot.getValue().toString().replace(":", "").toInt().toFloat()
                if (xTime != 0f) {
                    if (yAccelX != 0f)
                        arrXAccel.add(Entry(xTime, yAccelX))
                    if (yAccelY != 0f)
                        arrYAccel.add(Entry(xTime, yAccelY))
                    if (yAccelZ != 0f)
                        arrZAccel.add(Entry(xTime, yAccelZ))
                    if (yGyroX != 0f)
                        arrXGyro.add(Entry(xTime, yGyroX))
                    if (yGyroY != 0f)
                        arrYGyro.add(Entry(xTime, yGyroY))
                    if (yGyroZ != 0f)
                        arrZGyro.add(Entry(xTime, yGyroZ))
                    if (yTempMPU != 0f)
                        arrTempMPU.add(Entry(xTime, yTempMPU))
                    if (yTempNTC != 0f)
                        arrTempNTC.add(Entry(xTime, yTempNTC))
                    if (yAD8232 != 0f)
                        arrAD8232.add(Entry(xTime, yAD8232))
                }
                showAccelChart(arrXAccel, arrYAccel, arrZAccel)
                showGyroChart(arrXGyro, arrYGyro, arrZGyro)
                showTempChart(arrTempMPU, arrTempNTC)
                showAD8232chart(arrAD8232)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun showAD8232chart(arrAD8232: ArrayList<Entry>) {
        val lineAD8232data = LineDataSet(arrAD8232, "AD8232")

        CustomLineDataSet(lineAD8232data)
        lineAD8232data.color = Color.rgb(175, 122, 179)
        lineAD8232data.setCircleColor(Color.rgb(175, 122, 179))

        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(lineAD8232data)

        val legend = binding.chrtAD8232.legend
        CustomLegend(legend)

        val description = binding.chrtAD8232.description
        description.text = ""
        CustomDescriptionn(description)

        val xAxis = binding.chrtAD8232.xAxis
        val yAxisLeft = binding.chrtAD8232.axisLeft
        val yAxisRight = binding.chrtAD8232.axisRight
        yAxisLeft.valueFormatter = MyLeftYAxisValueFormatter()
        xAxis.valueFormatter = MyAxisValueFormatter()
        yAxisRight.valueFormatter = MyYAxisValueFormatter()
//custom đồ thị
        val data = LineData(dataSet)
        data.setValueFormatter(MyValueFormatter())
        binding.chrtAD8232.setDrawGridBackground(false)
        binding.chrtAD8232.setBorderColor(Color.BLACK)
        binding.chrtAD8232.setBorderWidth(8f)
        binding.chrtAD8232.data = data
        binding.chrtAD8232.invalidate()


    }

    private fun showTempChart(arrTempMPU: ArrayList<Entry>, arrTempNTC: ArrayList<Entry>) {
        val lineMPUDataSet = LineDataSet(arrTempMPU, "MPU6050")
        val lineNTCDataSet = LineDataSet(arrTempNTC, "NTC10k")

        CustomLineDataSet(lineMPUDataSet)
        lineMPUDataSet.color = Color.rgb(115, 169, 173)
        lineMPUDataSet.setCircleColor(Color.rgb(115, 169, 173))

        CustomLineDataSet(lineNTCDataSet)
        lineNTCDataSet.color = Color.rgb(154, 222, 123)
        lineNTCDataSet.setCircleColor(Color.rgb(154, 222, 123))

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineMPUDataSet)
        dataSets.add(lineNTCDataSet)

        val legend = binding.chrtTemp.legend
        CustomLegend(legend)

        val description = binding.chrtTemp.description
        description.text = ""
        CustomDescriptionn(description)

        val xAxis = binding.chrtTemp.xAxis
        val yAxisRight = binding.chrtTemp.axisRight
        xAxis.valueFormatter = MyAxisValueFormatter()
        yAxisRight.valueFormatter = MyYAxisValueFormatter()
//custom đồ thị
        val data = LineData(dataSets)
        data.setValueFormatter(MyValueFormatter())
        binding.chrtTemp.setDrawGridBackground(false)
        binding.chrtTemp.setBorderColor(Color.BLACK)
        binding.chrtTemp.setBorderWidth(8f)
        binding.chrtTemp.data = data
        binding.chrtTemp.invalidate()

    }


    private fun showGyroChart(
        arrXGyro: ArrayList<Entry>,
        arrYGyro: ArrayList<Entry>,
        arrZGyro: ArrayList<Entry>
    ) {
        val lineXDataSet = LineDataSet(arrXGyro, "Gyro X")
        val lineYDataSet = LineDataSet(arrYGyro, "Gyro Y")
        val lineZDataSet = LineDataSet(arrZGyro, "Gyro Z")

        CustomLineDataSet(lineXDataSet)
        lineXDataSet.color = Color.rgb(233, 119, 119)
        lineXDataSet.setCircleColor(Color.rgb(233, 119, 119))

        CustomLineDataSet(lineYDataSet)
        lineYDataSet.color = Color.rgb(114, 134, 211)
        lineYDataSet.setCircleColor(Color.rgb(114, 134, 211))

        CustomLineDataSet(lineZDataSet)
        lineZDataSet.color = Color.rgb(255, 196, 54)
        lineZDataSet.setCircleColor(Color.rgb(255, 196, 54))

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineXDataSet)
        dataSets.add(lineYDataSet)
        dataSets.add(lineZDataSet)
//custom chú thích
        val legend = binding.chrtGyro.legend
        CustomLegend(legend)
//custom description
        val description = binding.chrtGyro.description
        CustomDescriptionn(description)
        description.text = ""

//custom các trục
        val xAxis = binding.chrtGyro.xAxis
        val yAxisRight = binding.chrtGyro.axisRight
        xAxis.valueFormatter = MyAxisValueFormatter()
        yAxisRight.valueFormatter = MyYAxisValueFormatter()
//custom đồ thị
        val data = LineData(dataSets)
        data.setValueFormatter(MyValueFormatter())
        binding.chrtGyro.setDrawGridBackground(false)
        binding.chrtGyro.setBorderColor(Color.BLACK)
        binding.chrtGyro.setBorderWidth(8f)
        binding.chrtGyro.data = data
        binding.chrtGyro.invalidate()
    }

    private fun showAccelChart(
        arrXAccel: ArrayList<Entry>,
        arrYAccel: ArrayList<Entry>,
        arrZAccel: ArrayList<Entry>,
    ) {
        val lineXDataSet = LineDataSet(arrXAccel, "Accel X")
        val lineYDataSet = LineDataSet(arrYAccel, "Accel Y")
        val lineZDataSet = LineDataSet(arrZAccel, "Accel Z")

        CustomLineDataSet(lineXDataSet)
        lineXDataSet.color = Color.rgb(233, 119, 119)
        lineXDataSet.setCircleColor(Color.rgb(233, 119, 119))

        CustomLineDataSet(lineYDataSet)
        lineYDataSet.color = Color.rgb(114, 134, 211)
        lineYDataSet.setCircleColor(Color.rgb(114, 134, 211))

        CustomLineDataSet(lineZDataSet)
        lineZDataSet.color = Color.rgb(255, 196, 54)
        lineZDataSet.setCircleColor(Color.rgb(255, 196, 54))

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineXDataSet)
        dataSets.add(lineYDataSet)
        dataSets.add(lineZDataSet)
//custom chú thích
        val legend = binding.chrtAccel.legend
        CustomLegend(legend)
//custom description
        val description = binding.chrtAccel.description
        CustomDescriptionn(description)
        description.text = ""

//custom các trục
        val xAxis = binding.chrtAccel.xAxis
        val yAxisRight = binding.chrtAccel.axisRight
        xAxis.valueFormatter = MyAxisValueFormatter()
        yAxisRight.valueFormatter = MyYAxisValueFormatter()
//custom đồ thị
        val data = LineData(dataSets)
        data.setValueFormatter(MyValueFormatter())
        binding.chrtAccel.setDrawGridBackground(false)
        binding.chrtAccel.setBorderColor(Color.BLACK)
        binding.chrtAccel.setBorderWidth(8f)
        binding.chrtAccel.data = data
        binding.chrtAccel.invalidate()
    }


    private fun CustomLineDataSet(lineDataSet: LineDataSet) {
        lineDataSet.lineWidth = 2f
        lineDataSet.setDrawCircleHole(false)
        lineDataSet.circleRadius = 3f
        lineDataSet.valueTextSize = 0f

    }

    private fun CustomLegend(legend: Legend?) {
        legend?.isEnabled = true
        legend?.textColor = Color.BLACK
        legend?.textSize = 15f
        legend?.form = Legend.LegendForm.LINE
        legend?.formSize = 20f
        legend?.xEntrySpace = 10f
        legend?.formToTextSpace = 5f
    }

    private fun CustomDescriptionn(description: Description?) {
        description?.yOffset = 0f
        description?.textColor = Color.BLACK
        description?.textSize = 20f
    }

    class MyYAxisValueFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return ""
        }
    }

    class MyAxisValueFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            axis!!.setLabelCount(5, true)
            if (value.toInt().toString().length == 5) return '0' + value.toInt().toString()
            else return value.toInt().toString()
        }
    }

    class MyValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return String.format("%.3f", value)
        }
    }

    class MyLeftYAxisValueFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return value.toInt().toString()
        }
    }


}
