package com.example.nyemil_kuy

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nyemil_kuy.databinding.ActivityCartFragmentBinding
import com.example.nyemil_kuy.databinding.ActivityHomeBinding
import com.example.nyemil_kuy.databinding.ActivityOrderBinding
import com.example.nyemil_kuy.databinding.ActivityProfileBinding
import com.example.nyemil_kuy.databinding.ActivitySuccessBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvHeaderGreeting.text = "Halo ${UserManager.userName},"

        setupRecyclerView()
        setupBottomNav()
    }

    override fun onResume() {
        super.onResume()
        binding.tvHeaderGreeting.text = "Halo ${UserManager.userName},"
    }

    private fun setupRecyclerView() {
        val cafeMenu = listOf(
            MenuItem(1, "Kopi Susu Gula Aren", "Perpaduan espresso pekat dengan manis legitnya gula aren asli dan susu krimi.", 22000, R.drawable.coffee),
            MenuItem(2, "Donat Cokelat", "Donat super empuk yang lumer di mulut dengan balutan cokelat premium.", 12000, R.drawable.chocolate),
            MenuItem(3, "Sourdough Loaf", "Roti artisan berfermentasi alami. Renyah di luar, kenyal di dalam, dan menyehatkan.", 35000, R.drawable.steak_daging),
            MenuItem(4, "Cappuccino", "Kombinasi klasik espresso murni dengan kelembutan micro-foam susu hangat.", 25000, R.drawable.coffee),
            MenuItem(5, "Matcha Latte", "Racikan matcha premium asli Jepang berpadu dengan susu segar yang menenangkan.", 27000, R.drawable.matcha)
        )

        homeAdapter = HomeAdapter(cafeMenu) { menuItem ->
            CartManager.addToCart(menuItem)
            Toast.makeText(this, "${menuItem.name} ditambahkan ke pesanan", Toast.LENGTH_SHORT).show()
        }

        binding.rvMenu.layoutManager = LinearLayoutManager(this)
        binding.rvMenu.adapter = homeAdapter
    }

    private fun setupBottomNav() {
        binding.btnNavHome.setOnClickListener {
        }

        binding.btnNavOrder.setOnClickListener {
            val cartFragment = CartFragment()
            cartFragment.show(supportFragmentManager, "CartFragment")
        }

        binding.btnNavProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}

class CartFragment : BottomSheetDialogFragment() {
    private var _binding: ActivityCartFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ActivityCartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        updateCalculations()

        binding.btnProceedCheckout.setOnClickListener {
            if (CartManager.cartList.isNotEmpty()) {
                startActivity(Intent(requireContext(), OrderActivity::class.java))
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Keranjang kosong.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(CartManager.cartList) {
            updateCalculations()
        }
        binding.rvCartItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCartItems.adapter = cartAdapter
    }

    private fun updateCalculations() {
        val count = CartManager.cartList.sumOf { it.quantity }
        binding.tvOrderCount.text = "Anda memiliki $count item di keranjang"
        binding.tvCartSubtotal.text = "Rp ${CartManager.getSubtotal()}"
        binding.tvCartShipping.text = "Rp ${CartManager.getShippingCost()}"
        binding.tvCartTotal.text = "Rp ${CartManager.getTotal()}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etOrderName.setText(UserManager.userName)

        binding.btnOrderSubmit.setOnClickListener {
            val name = binding.etOrderName.text.toString().trim()
            val address = binding.etOrderAddress.text.toString().trim()

            if (address.isEmpty()) {
                Toast.makeText(this, "Silakan isi alamat lengkap pengiriman!", Toast.LENGTH_SHORT).show()
            } else {
                if (name.isNotEmpty()) {
                    UserManager.userName = name
                }
                val itemCount = CartManager.cartList.sumOf { it.quantity }
                val total = CartManager.getTotal()
                CartManager.activeOrderSummary = "$itemCount item pesanan (Total: Rp $total)\nAlamat: $address"
                CartManager.clearCart()

                val intent = Intent(this, SuccessActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.btnOrderNavHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.btnOrderNavOrder.setOnClickListener {
            val cartFragment = CartFragment()
            cartFragment.show(supportFragmentManager, "CartFragment")
        }

        binding.btnOrderNavProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateProfileUI()

        binding.btnProfileSave.setOnClickListener {
            val newName = binding.etProfileName.text.toString().trim()
            if (newName.isNotEmpty()) {
                UserManager.userName = newName
                updateProfileUI()
                Toast.makeText(this, "Nama berhasil diperbarui!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnProfileNavHome.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        binding.btnProfileNavOrder.setOnClickListener {
            val cartFragment = CartFragment()
            cartFragment.show(supportFragmentManager, "CartFragment")
        }

        binding.btnProfileNavProfile.setOnClickListener {
        }
    }

    private fun updateProfileUI() {
        binding.tvProfileGreeting.text = "Halo ${UserManager.userName},"
        binding.etProfileName.setText(UserManager.userName)
        binding.tvActiveOrderSummary.text = CartManager.activeOrderSummary
    }
}

class SuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSuccessName.text = "Halo ${UserManager.userName},"

        binding.btnSuccessOk.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}