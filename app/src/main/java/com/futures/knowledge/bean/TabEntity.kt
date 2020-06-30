package com.futures.knowledge.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * @ClassName: TabEntity
 * @Description: Tab实体类
 * @Author: dongxie
 * @CreateDate: 2019/6/19 14:32
 */
class TabEntity(private val title: String, private val selectIcon: Int, private val unSelectIcon: Int) : CustomTabEntity {

    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return selectIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectIcon
    }
}
