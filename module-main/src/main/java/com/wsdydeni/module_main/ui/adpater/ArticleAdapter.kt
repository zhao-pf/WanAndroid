package com.wsdydeni.module_main.ui.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.wsdydeni.module_main.R
import com.wsdydeni.module_main.databinding.ItemArticleBinding
import com.wsdydeni.module_main.model.Article

class ArticleViewHolder(contentView : View) : RecyclerView.ViewHolder(contentView)

class ArticleAdapter : RecyclerView.Adapter<ArticleViewHolder>(){

    private var dataList = arrayListOf<Article>()

    private lateinit var binding : ItemArticleBinding

    @Synchronized
    fun setData(newList: ArrayList<Article>,isRefresh: Boolean = false,isTop: Boolean = false) {
        when {
            isRefresh -> {
                dataList.clear()
                dataList.addAll(newList)
                notifyDataSetChanged()
            }
            isTop -> {
                if(dataList.size == 0) {
                    dataList.addAll(newList)
                }else {
                    dataList.addAll(0,newList)
                }
                notifyItemRangeInserted(0,newList.size)
            }
            else -> { // isLoadMore
                dataList.addAll(newList)
                notifyItemRangeInserted(if(dataList.size == 0) 0 else dataList.size - 1,newList.size)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_article,parent,false)
        return ArticleViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        binding.article = dataList[holder.bindingAdapterPosition]
        binding.root.setOnClickListener {
            ARouter.getInstance().build("/browser/BrowserActivity")
                .withString("url", dataList[holder.bindingAdapterPosition].link).navigation()
        }
        binding.executePendingBindings()
    }

    override fun getItemCount(): Int = dataList.size
}
