/**
 * Created by Mahmud on 03/09/23.
 * mahmud120398@gmail.com
 */

package id.myone.mynewsapp.view.ui.main_screen.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.myone.mynewsapp.databinding.CategoryListItemBinding
import id.myone.mynewsapp.view.ui.main_screen.data.CategoryData

class CategoryAdapter(
    private val context: Context,
    private val categories: List<CategoryData>,
    private val onClick: (CategoryData) -> Unit,
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private lateinit var binding: CategoryListItemBinding
    class CategoryViewHolder(
        private val context: Context,
        private val binding: CategoryListItemBinding,
        private val onClick: (CategoryData) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryData) {
            
            binding.categoryName.text = context.getString(category.name)
            binding.categoryImage.setImageResource(category.image)

            binding.root.setOnClickListener {
                onClick(category)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        binding = CategoryListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CategoryViewHolder(context, binding, onClick)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }
}