import android.app.AlertDialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gost_app.Jelo
import com.example.gost_app.OnCartUpdateListener
import com.example.gost_app.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class JeloAdapter(  private val jelaList: List<Jelo>,
                    private val cartTextListener: OnCartUpdateListener,
                    private val cartText: TextView,
                    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<JeloAdapter.JeloViewHolder>() {

    class JeloViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nazivJela: TextView = itemView.findViewById(R.id.textView_naziv_jela)
        val opis: TextView = itemView.findViewById(R.id.textView_opis)
        val cijena: TextView = itemView.findViewById(R.id.textView_cijena)
        val button: Button = itemView.findViewById(R.id.jelo_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JeloViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.jelo_layout, parent, false)
        return JeloViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JeloViewHolder, position: Int) {
        val currentItem = getUniqueJelaList()[position]
        holder.nazivJela.text = currentItem.nazivJela
        holder.opis.text = currentItem.opis
        holder.cijena.text = currentItem.cijena.toString() + "0 KM"

        holder.button.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            showPopup(holder.button.context, holder.button, getUniqueJelaList()[adapterPosition])
        }
    }
    private fun getUniqueJelaList(): List<Jelo> {
        return jelaList.distinctBy { it.nazivJela }
    }
    private fun showPopup(context: Context, anchorView: View, selectedItem: Jelo) {
        val inflater = LayoutInflater.from(context)
        val popupView: View = inflater.inflate(R.layout.popup_layout, null)
        var brojacJela: Int = 0

        // Pronađite elemente iz custom layouta
        val titleTextView: TextView = popupView.findViewById(R.id.textView_title)
        val contentTextView: TextView = popupView.findViewById(R.id.textView_content)
        val brojacText: TextView = popupView.findViewById(R.id.brojacTextView)
        val plusButton: Button = popupView.findViewById(R.id.plusButton)
        val minusButton: Button = popupView.findViewById(R.id.minusButton)
        val xButton: Button = popupView.findViewById(R.id.xButton)
        val okButton: Button = popupView.findViewById(R.id.okButton)

        // Postavite sadržaj prema podacima odabranog jela
        titleTextView.text = selectedItem.nazivJela
        contentTextView.text = selectedItem.opis
        brojacText.text = "0"
        plusButton.setOnClickListener {
            brojacJela++
            if (brojacJela <= 10)
                brojacText.text = brojacJela.toString()
            else brojacJela = 10
        }

        minusButton.setOnClickListener {
            brojacJela--
            if (brojacJela >= 0)
                brojacText.text = brojacJela.toString()
            else brojacJela = 0
        }
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setView(popupView)

        val alertDialog = alertDialogBuilder.create()

        alertDialog.show()

        xButton.setOnClickListener {
            alertDialog.dismiss()
        }
        okButton.setOnClickListener {
            val updatedValue = brojacJela
            cartTextListener.updateCartValue(updatedValue)
            brojacJela = 0
            alertDialog.dismiss()
        }

        // Pronađite trenutnu poziciju unutar RecyclerViewa
        val layoutManager = recyclerView.layoutManager
        val visiblePosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val viewAtPosition = layoutManager.findViewByPosition(visiblePosition)
        val location = IntArray(2)
        viewAtPosition?.getLocationOnScreen(location)

        // Postavljanje pozicije dijaloga na temelju trenutne pozicije unutar RecyclerViewa
        val dialogWindow = alertDialog.window
        dialogWindow?.let {
            val layoutParams = it.attributes
            layoutParams.gravity = Gravity.TOP or Gravity.START
            layoutParams.x = location[0] // X-koordinata
            layoutParams.y = location[1] // Y-koordinata
            layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            it.attributes = layoutParams
        }
    }



    override fun getItemCount(): Int {
        val itemCount = jelaList.size

        val recyclerViewHeight = recyclerView.height // Dohvaćanje visine RecyclerView-a

        // Provjerite je li visina RecyclerView-a već postavljena; ako nije, vratite 0
        if (recyclerViewHeight == 0) {
            return 0
        }

        // Izračunajte visinu jednog reda ovdje
        val rowHeight = 100// Visina jednog reda

        // Izračunajte broj vidljivih redova koji se mogu prikazati u RecyclerView-u
        val visibleRows = recyclerViewHeight / rowHeight

        // Ako je veličina podataka manja od vidljivih redova, vratite broj redova kako biste popunili dostupni prostor
        return if (itemCount < visibleRows) {
            visibleRows
        } else {
            itemCount
        }
    }


}








