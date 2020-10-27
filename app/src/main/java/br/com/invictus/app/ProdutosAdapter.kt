import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.invictus.app.Produto
import br.com.invictus.app.R

class ProdutosAdapter(
    val produtos: List<Produto>,
    val onClick: (Produto) -> Unit
) :
    RecyclerView.Adapter<ProdutosAdapter.ProdutosViewHolder>() {
    // ViewHolder com os elementos da tela
    class ProdutosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cardView: CardView
        val cardNome: TextView
        val cardQtd: TextView
        val cardPreco: TextView
        val cardCodigo: TextView

        init {
            cardView = view.findViewById<CardView>(R.id.card_produtos)
            cardNome = view.findViewById<TextView>(R.id.cardNome)
            cardQtd = view.findViewById<TextView>(R.id.cardQtd)
            cardPreco = view.findViewById<TextView>(R.id.cardPreco)
            cardCodigo = view.findViewById<TextView>(R.id.cardCodigo)
        }
    }

    override fun getItemCount() = this.produtos.size

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ProdutosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_produto, parent, false)
        val holder = ProdutosViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ProdutosViewHolder, position: Int) {
        val produto = produtos[position]

        holder.cardNome.text = produto.nome
        holder.cardCodigo.text = produto.codigo.toString()
        holder.cardPreco.text = "R$ " + (produto.preco.toDouble() / 100).toString()
        holder.cardQtd.text = "Quantidade: " + produto.qtd.toString()

        holder.itemView.setOnClickListener { onClick(produto) }
    }
}