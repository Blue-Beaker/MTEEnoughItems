package io.bluebeaker.mteenoughitems.utils;

import net.minecraft.client.Minecraft;

public class RenderUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static String cutStringToWidth(String text, int width){
        if(mc.fontRenderer.getStringWidth(text)<=width) return text;
        String text1 = mc.fontRenderer.listFormattedStringToWidth(text, width-mc.fontRenderer.getStringWidth("...")).get(0);
        return text1+"...";
    }
    public static void drawTextAlignedLeft(String text,int x,int y,int color){
        mc.fontRenderer.drawString(text, x, y, color);
    }
    public static void drawTextAlignedMiddle(String text,int x,int y,int color){
        mc.fontRenderer.drawString(text, x-(mc.fontRenderer.getStringWidth(text)/2), y, color);
    }
    public static void drawTextAlignedRight(String text,int x,int y,int color){
        mc.fontRenderer.drawString(text, x-mc.fontRenderer.getStringWidth(text), y, color);
    }
}
