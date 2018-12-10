package com.otikev.codecensus.repos

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.otikev.codecensus.R
import lecho.lib.hellocharts.model.*
import lecho.lib.hellocharts.util.ChartUtils
import lecho.lib.hellocharts.view.LineChartView


/**
 * Created by kevin on 10/12/18 at 21:28
 */
class RepoAdapter(val context: Context, val mDataset: ArrayList<RepoItem>) :
    RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val v: View = LayoutInflater.from(context).inflate(R.layout.repo_item, parent, false)
        return RepoViewHolder(v)
    }

    override fun onBindViewHolder(repoViewHolder: RepoViewHolder, position: Int) {
        val repoItem: RepoItem = mDataset[position]

        repoViewHolder.bind(repoItem)
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtRepoName: TextView = view.findViewById(R.id.txtRepoName)
        var chart: LineChartView = view.findViewById(R.id.chart)

        fun bind(repoItem: RepoItem) {
            chart.isViewportCalculationEnabled = false
            txtRepoName.text = repoItem.name
            generateChart(chart, repoItem.trend)
        }

        private fun generateChart(chart: LineChartView, trend: CommitTrend) {
            var data: LineChartData

            val lines = ArrayList<Line>()
            val values = ArrayList<PointValue>()

            var highest = 0f
            for (j in 0 until trend.trend.size) {
                val y = trend.trend[j].toFloat()
                values.add(PointValue(j.toFloat(), y))

                if (y > highest) {
                    highest = y
                }
            }

            val line = Line(values)
            line.color = ChartUtils.COLORS[0]
            line.shape = ValueShape.CIRCLE
            line.setHasLines(true)
            line.setHasPoints(false)
            lines.add(line)

            data = LineChartData(lines)

            data.axisXBottom = null
            data.axisYLeft = null

            data.baseValue = java.lang.Float.NEGATIVE_INFINITY
            chart.lineChartData = data

            // Reset viewport height range to (0,100)
            val v = Viewport(chart.maximumViewport)
            v.bottom = 0f
            v.top = highest+1
            v.left = 0f
            v.right = (trend.trend.size - 1).toFloat()
            chart.maximumViewport = v
            chart.currentViewport = v
        }
    }
}