package com.wsdydeni.module_main.ui.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.library_base.config.PathConfig
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.databinding.ItemSearchBinding
import com.wsdydeni.module_main.model.Article

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){

    private lateinit var binding: ItemSearchBinding

    private var allData = arrayListOf<Article>()

    fun setData(newList: ArrayList<Article>,isRefresh: Boolean = false){
        if(isRefresh) allData.clear()
        allData.addAll(newList)
        notifyDataSetChanged()
    }

    fun clearData() {
        allData.clear()
        notifyDataSetChanged()
    }

    class SearchViewHolder(content: View) : RecyclerView.ViewHolder(content)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_search,parent,false)
        return SearchViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        binding.search = allData[position]
        binding.root.setOnClickListener {
            ARouter.getInstance().build(PathConfig.PATH_BROWSER).withString("url",allData[position].link).navigation()
        }
        binding.executePendingBindings()
    }

    override fun getItemCount(): Int = allData.size
}