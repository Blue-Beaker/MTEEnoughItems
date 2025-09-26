package io.bluebeaker.mteenoughitems.utils;

import net.minecraft.client.resources.I18n;

public class Utils {
    public static String localize(String text,Object... params){
        return I18n.format(text, params);
    }
}
