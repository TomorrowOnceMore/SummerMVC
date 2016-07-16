package com.summer.portal.pojo;

import com.summer.pojo.TbItem;

/**
 * Created by toy on 7/15/16.
 */
public class PortalItem extends TbItem {
    public String[] getImages() {
        String images = this.getImage();
        if (images != null && !images.equals("")) {
            String[] imgs = images.split(",");
            return imgs;
        }
        return null;
    }
}
