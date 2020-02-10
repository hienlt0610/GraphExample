package dev.hienlt.graph

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.ImageUtils
import de.blox.graphview.BaseGraphAdapter
import de.blox.graphview.Graph
import de.blox.graphview.GraphView
import de.blox.graphview.Node
import de.blox.graphview.tree.BuchheimWalkerAlgorithm
import de.blox.graphview.tree.BuchheimWalkerConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_save.setOnClickListener {
            saveImage()
        }
    }

    private fun saveImage() {
        Log.d("hienlt0610", "save image")
        val graphView = GraphView(this)
        val graph = Graph()
        var nodeDS = Node("Danh sách")
        Utils.getDemoData(this).forEach { mien ->
            var nodeMien = Node(mien.name)
            graph.addEdge(nodeDS, nodeMien)
            mien.tinh?.forEach { tinh ->
                var nodeTinh = Node(tinh.name)
                graph.addEdge(nodeMien, nodeTinh)
                tinh.huyen?.forEach { huyen ->
                    var nodeHuyen = Node(huyen.name)
                    graph.addEdge(nodeTinh, nodeHuyen)
                }
            }
        }

        val adapter: BaseGraphAdapter<SimpleViewHolder> =
            object : BaseGraphAdapter<SimpleViewHolder>(graph) {
                override fun onCreateViewHolder(
                    parent: ViewGroup?,
                    viewType: Int
                ): SimpleViewHolder {
                    val view =
                        LayoutInflater.from(parent?.context)
                            .inflate(R.layout.item_node, parent, false)
                    return SimpleViewHolder(view)
                }

                override fun onBindViewHolder(
                    viewHolder: SimpleViewHolder?,
                    data: Any?,
                    position: Int
                ) {
                    viewHolder?.textView?.text = data.toString()
                }

            }
        graphView.adapter = adapter
        // set the algorithm here
        // set the algorithm here
        val configuration = BuchheimWalkerConfiguration.Builder()
            .setSiblingSeparation(100)
            .setLevelSeparation(300)
            .setSubtreeSeparation(300)
            .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
            .build()
        adapter.algorithm = BuchheimWalkerAlgorithm(configuration)

        //Get the dimensions of the view so we can re-layout the view at its current size
//and create a bitmap of the same size
        val width = 2000
        val height = 500

        val measuredWidth = View.MeasureSpec.makeMeasureSpec(
            width,
            View.MeasureSpec.EXACTLY
        )
        val measuredHeight = View.MeasureSpec.makeMeasureSpec(
            height,
            View.MeasureSpec.EXACTLY
        )

        //Cause the view to re-layout
        //Cause the view to re-layout
        graphView.setZoomEnabled(enabled = false)
        graphView.measure(measuredWidth, measuredHeight)
        graphView.layout(0, 0, width, height)
        ImageUtils.save(
                Utils.getViewBitmap(graphView),
                File(Environment.getExternalStorageDirectory(), "tree-image.jpg"),
                Bitmap.CompressFormat.JPEG,
                true
            )
        setContentView(graphView)
//        graphView.run {
//            ImageUtils.save(
//                Utils.getViewBitmap(graphView),
//                File(Environment.getExternalStorageDirectory(), "tree-image.jpg"),
//                Bitmap.CompressFormat.JPEG,
//                true
//            )
//        }

    }
}
