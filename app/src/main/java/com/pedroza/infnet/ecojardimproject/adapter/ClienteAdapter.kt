import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.models.Cliente

class ClienteAdapter(private var listaClientes: List<Cliente>) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_cliente, parent, false)
        return ClienteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = listaClientes[position]
        holder.nomeTextView.text = cliente.nome
        holder.sobrenomeTextView.text = cliente.sobrenome
        holder.documentoTextView.text = cliente.documento
        holder.adicionarClienteBtn.setOnClickListener {
            holder.itemView.findNavController().navigate(R.id.action_nav_cliente_to_clienteFormFragment)
        }
        holder.editarClienteBtn.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("cliente", cliente)
            }
            holder.itemView.findNavController().navigate(R.id.action_nav_cliente_to_clienteFormFragment, bundle)
        }


    }

    override fun getItemCount(): Int {
        return listaClientes.size
    }

    fun updateClientesList(newList: List<Cliente>) {
        listaClientes = newList
        notifyDataSetChanged()
    }

    class ClienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeTextView: TextView = itemView.findViewById(R.id.item_nome)
        val sobrenomeTextView: TextView = itemView.findViewById(R.id.item_sobrenome)
        val documentoTextView: TextView = itemView.findViewById(R.id.item_documento)
        val adicionarClienteBtn: ImageButton = itemView.findViewById(R.id.adicionar_cliente)
        val editarClienteBtn: ImageButton = itemView.findViewById(R.id.edit_cliente)
    }
}
