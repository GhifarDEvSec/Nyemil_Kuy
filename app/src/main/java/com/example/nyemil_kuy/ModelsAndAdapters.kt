package com.example.nyemil_kuy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nyemil_kuy.databinding.CartBinding
import com.example.nyemil_kuy.databinding.ItemBinding

data class MenuItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Int,
    val imageRes: Int
)

data class CartItem(
    val menuItem: MenuItem,
    var quantity: Int
)

object UserManager {
    var userName: String = "Pelanggan"
}

object CartManager {
    val cartList = mutableListOf<CartItem>()
    var activeOrderSummary: String = "Belum ada pesanan aktif"

    fun addToCart(menuItem: MenuItem) {
        val existing = cartList.find { it.menuItem.id == menuItem.id }
        if (existing != null) {
            existing.quantity++
        } else {
            cartList.add(CartItem(menuItem, 1))
        }
    }

    fun getSubtotal(): Int = cartList.sumOf { it.menuItem.price * it.quantity }
    fun getShippingCost(): Int = if (cartList.isNotEmpty()) 10000 else 0
    fun getTotal(): Int = if (cartList.isEmpty()) 0 else getSubtotal() + getShippingCost()
    fun clearCart() = cartList.clear()
}

class HomeAdapter(
    private val menuList: List<MenuItem>,
    private val onAddToCartClicked: (MenuItem) -> Unit
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = menuList[position]
        holder.binding.tvItemName.text = item.name
        holder.binding.tvItemDesc.text = item.description
        holder.binding.tvItemPrice.text = "Rp ${item.price}"
        holder.binding.ivItemImage.setImageResource(item.imageRes)

        holder.binding.btnAddItem.setOnClickListener {
            onAddToCartClicked(item)
        }
    }

    override fun getItemCount(): Int = menuList.size
}

class CartAdapter(
    private val cartList: MutableList<CartItem>,
    private val onQuantityChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: CartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartList[position]
        holder.binding.tvCartItemName.text = item.menuItem.name
        holder.binding.tvCartItemDesc.text = item.menuItem.description
        holder.binding.tvCartItemPrice.text = "Rp ${item.menuItem.price * item.quantity}"
        holder.binding.tvCartQty.text = item.quantity.toString()
        holder.binding.ivCartItemImage.setImageResource(item.menuItem.imageRes)

        holder.binding.btnCartPlus.setOnClickListener {
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION) {
                item.quantity++
                notifyItemChanged(currentPos)
                onQuantityChanged()
            }
        }

        holder.binding.btnCartMinus.setOnClickListener {
            val currentPos = holder.adapterPosition
            if (currentPos != RecyclerView.NO_POSITION) {
                if (item.quantity > 1) {
                    item.quantity--
                    notifyItemChanged(currentPos)
                } else {
                    cartList.removeAt(currentPos)
                    notifyItemRemoved(currentPos)
                    notifyItemRangeChanged(currentPos, cartList.size)
                }
                onQuantityChanged()
            }
        }
    }

    override fun getItemCount(): Int = cartList.size
}