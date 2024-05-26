import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.pedroza.infnet.ecojardimproject.R
import com.pedroza.infnet.ecojardimproject.models.Cliente
import com.pedroza.infnet.ecojardimproject.service.ClienteApiService
import com.pedroza.infnet.ecojardimproject.ui.cliente.ClienteViewModel

class ClienteAdapter(private var listaClientes: List<Cliente>,private val clienteViewModel: ClienteViewModel,private val clienteApiService: ClienteApiService) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>()  {

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

        if (position == 0) {
            holder.adicionarClienteBtn.visibility = View.VISIBLE
        } else {
            holder.adicionarClienteBtn.visibility = View.GONE
        }

        holder.editarClienteBtn.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("cliente", cliente)
            }
            holder.itemView.findNavController().navigate(R.id.action_nav_cliente_to_clienteFormFragment, bundle)
        }

        holder.excluirClienteBtn.setOnClickListener {
            excluirCliente(position)
        }
    }

    override fun getItemCount(): Int {
        return listaClientes.size
    }

    fun excluirCliente(position: Int) {
        val clienteExcluido = listaClientes[position]
        listaClientes = listaClientes.filterIndexed { index, _ -> index != position }
        notifyDataSetChanged()
        clienteViewModel.excluirCliente(clienteApiService, clienteExcluido.id)
    }
    fun updateClientesList(newList: List<Cliente>) {
        listaClientes = newList
        notifyDataSetChanged()
    }

    class ClienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeTextView: TextView = itemView.findViewById(R.id.item_nome)
        val sobrenomeTextView: TextView = itemView.findViewById(R.id.item_sobrenome)
        val documentoTextView: TextView = itemView.findViewById(R.id.item_documento)
        val adicionarClienteBtn: Button = itemView.findViewById(R.id.adicionar_cliente)
        val editarClienteBtn: ImageButton = itemView.findViewById(R.id.edit_cliente)
        val excluirClienteBtn: ImageButton = itemView.findViewById(R.id.excluir_cliente)
    }
}
