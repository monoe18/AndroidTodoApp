import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.ArrayList
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class RecyclerAdapter(private val context: Context, private val list: ArrayList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var yourView: TextView

        init {
            yourView = itemView.findViewById(R.id.recyclerView) 
        }

        internal fun bind(position: Int) {

            yourView.text = list[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_edit_recyclerview, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}